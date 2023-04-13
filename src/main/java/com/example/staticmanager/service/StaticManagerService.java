package com.example.staticmanager.service;


import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
import java.util.Base64;


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
                    .url("http://localhost:9000/api/measures/component?component=pet-clinic&metricKeys=ncloc")
                    .build();
            Response response = client.newCall(request).execute();
            log.info(response.toString());
//            log.info(System.getenv("SONARQUBE_TOKEN"));
            result = response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } ;
        log.info("result : {}", result);
        return result;
    }



    public String getProjectInfo() {
        String result = new String("No response");
        OkHttpClient client = new OkHttpClient();
        try {
            String tokenBase = System.getenv("SONARQUBE_ID") + ":" + System.getenv("SONARQUBE_PASSWORD");
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(tokenBase.getBytes()));

            log.info(basicAuth);
            Request request = new Request.Builder()
                    .addHeader("Authorization", basicAuth)
                    .addHeader("Accept", "application/json")
                    .url("http://localhost:9000/api/api/projects/search?projects=" + System.getenv("PROJECT_KEY"))
                    .build();
            Response response = client.newCall(request).execute();

            log.info(response.toString());

            result = response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } ;
        log.info("result : {}", result);
        return result;
    }



}
