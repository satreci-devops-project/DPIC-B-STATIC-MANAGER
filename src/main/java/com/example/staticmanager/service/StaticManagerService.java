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


@Slf4j
@Service
public class StaticManagerService {

    public void getEmployees()
    {
//        final String uri = "http://localhost:9000/api/qualitygates/project_status?projectKey=pet-clinic";
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
//            public boolean hasError(ClientHttpResponse response) throws IOException {
//                HttpStatus statusCode = response.getStatusCode();
//                return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
//            }
//        });
//
//        HttpEntity<?> requestEntity = apiClientHttpEntity(param.toString());
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
//            public boolean hasError(ClientHttpResponse response) throws IOException {
//                HttpStatus statusCode = response.getStatusCode();
//                return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
//            }
//        });
//
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        restTemplate.getMessageConverters().add(converter);
//        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, JSONObject.class);
//
//
//
//
//
//        HttpHeaders header= new HttpHeaders();
//        header.add("Authorization", System.getenv("sonar-token"));
////        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
////        header.add("Accept", "application/json");
//
//        MultiValueMap<String, String> body= new LinkedMultiValueMap<String, String>();
//        body.add("grant_type", "client_credentials");
//
//        HttpEntity<MultiValueMap<String, String>> requesteHttp =new HttpEntity<>(body, header);
//
//
//        String response = restTemplate.getForObject(uri, String.class, requesteHttp);
//
//        System.out.println(response);
    }

    public String getSonarqubeInfo() {
        String result = new String("No response");
        OkHttpClient client = new OkHttpClient();
        try {
            String authorization = "Bearer " + System.getenv("SONARQUBE_TOKEN");
            Request request = new Request.Builder()
                    .addHeader("Authorization", authorization)
                    .addHeader("Accept", "application/json")
                    .url("https://sonarqube.com/api/measures/component?component=et-clinic&metricKey=ncloc")
                    .build();
            Response response = client.newCall(request).execute();
            log.info(response.toString());
            log.info(System.getenv("SONARQUBE_TOKEN"));
            result = response.body().string();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } ;
        log.info("result : {}", result);
        return result;
    }
}
