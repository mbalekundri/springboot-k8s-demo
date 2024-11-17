package com.springboot.k8s.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class WelcomeController implements ErrorController {

    private static Integer count = 0;

    @Value("${APP_ENV}")
    private String appEnv;

    private static List<String> remoteCalls = new ArrayList<>();

    @GetMapping("/")
    public ModelAndView welcome(HttpServletRequest request) {
        count ++;
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("count", count);
        modelAndView.addObject("appEnv", appEnv);
        log.info("appEnv: "+appEnv);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String remoteCall = "time: "+LocalDateTime.now().format(formatter)+", Remote address: "+request.getRemoteAddr()+", Remote host: "+request.getRemoteHost()+", User: "+request.getParameter("user");
        remoteCalls.add(remoteCall);
        modelAndView.addObject("remoteCalls", remoteCalls);
        return modelAndView;
    }
}
