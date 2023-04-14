package com.example.staticmanager.service;


import com.example.staticmanager.entity.Project;
import com.example.staticmanager.entity.StaticAnalysisResult;
import com.example.staticmanager.repository.ProjectRepository;
import com.example.staticmanager.repository.StaticAnalysisResultRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class StaticManagerService {

    public String getStaticAnalysisResultInfo() {
        String result = new String("No response");
        OkHttpClient client = new OkHttpClient();
        try {
            String tokenBase = System.getenv("SONARQUBE_ID") + ":" + System.getenv("SONARQUBE_PASSWORD");
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(tokenBase.getBytes()));


//            String authorization = "Bearer " + System.getenv("SONARQUBE_TOKEN");
            log.info(basicAuth);
            Request request = new Request.Builder()
                    .addHeader("Authorization", basicAuth)
                    .addHeader("Accept", "application/json")
                    .url("http://localhost:9000/api/measures/component?component=pet-clinic&metricKeys=ncloc,bugs,complexity,code_smells")
                    .build();
            Response response = client.newCall(request).execute();
            log.info(response.toString());
//            log.info(System.getenv("SONARQUBE_TOKEN"));
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } ;
        log.info("result : {}", result);
        return result;
    }






    //-------------------------------------------------------------------------------------------------
    //----------------------------------Project 테이블 코드 ---------------------------------------------


    @Autowired
    ProjectRepository pr;

    public String getProjectInfo() {
        String result = new String("No response");
        OkHttpClient client = new OkHttpClient();

        try {
            String tokenBase = System.getenv("SONARQUBE_ID") + ":" + System.getenv("SONARQUBE_PASSWORD");
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(tokenBase.getBytes()));

            Request request = new Request.Builder()
                    .addHeader("Authorization", basicAuth)
                    .addHeader("Accept", "application/json")
                    .url("http://localhost:9000/api/projects/search?projects=" + System.getenv("PROJECT_KEY"))
                    .build();
            Response response = client.newCall(request).execute();

            result = response.body().string();

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            return result;
        }
    }


    //-------------------------------------------------------------------------------------------------
    //----------------------------------static analysis result 테이블 코드 ---------------------------------------------
    
    @Autowired
    StaticAnalysisResultRepository staticAnalysisResultRepository;
    
    public StaticAnalysisResult jsonToStaticAnalysisResult(String jsonResponse, Project project) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject component = (JSONObject) ((JSONObject) parser.parse(jsonResponse)).get("component");
        log.info(component.toString());
        JSONArray measures = (JSONArray) component.get("measures");
        log.info(measures.toString());

        Map<String, Integer> paramMap = new HashMap<>();
        for(Object obj : measures) {
            JSONObject jsonObject = (JSONObject) obj;
            String metric = jsonObject.get("metric").toString();
            Integer value = Integer.parseInt(jsonObject.get("value").toString());
            log.info("{} {}", metric, value);
            paramMap.put(metric, value);
        }

        return StaticAnalysisResult.builder()
                .bugs(paramMap.get("bugs"))
                .codeSmells(paramMap.get("code_smells"))
                .complexity(paramMap.get("complexity"))
                .loc(paramMap.get("ncloc"))
                .projectKey(project)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public Project jsonToProjectObject(String jsonResponse) throws ParseException {

        JSONParser parse = new JSONParser();
        String componentsStr = ((JSONObject)parse.parse(jsonResponse)).get("components").toString();
        JSONObject componentsObj = (JSONObject)(parse.parse(componentsStr.substring(1, componentsStr.length()-1)));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        String tempLastAnalysisDate = componentsObj.get("lastAnalysisDate").toString();
        tempLastAnalysisDate = tempLastAnalysisDate.substring(0, tempLastAnalysisDate.length()-5);

        System.out.println(tempLastAnalysisDate);


        return new Project(
                componentsObj.get("key").toString(),
                componentsObj.get("name").toString(),
                componentsObj.get("qualifier").toString(),
                componentsObj.get("visibility").toString(),
                LocalDateTime.parse(tempLastAnalysisDate, formatter),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }


    public String staticManagerService(){

        try{
            Project project = jsonToProjectObject(getProjectInfo());
            pr.save(project);
            StaticAnalysisResult staticAnalysisResult = jsonToStaticAnalysisResult(getStaticAnalysisResultInfo(), project);
            staticAnalysisResultRepository.save(staticAnalysisResult);
        }
        catch(Exception e){
            log.info("Error : {}", e.getMessage());
            throw new RuntimeException(e);
        }
        finally {
            return "200 OK";
        }
    }




}
