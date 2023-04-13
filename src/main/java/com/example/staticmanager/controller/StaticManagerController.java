package com.example.staticmanager.controller;


import com.example.staticmanager.service.StaticManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class StaticManagerController {

    @Autowired
    StaticManagerService sms;

    @PostMapping(value = "/")
    public String sonarqubeWebhook(@RequestBody String test){
//        sms.getStaticAnalysisResultInfo();
        sms.staticManagerService();

        return "OK";
    }

}
