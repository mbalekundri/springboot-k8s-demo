package com.springboot.k8s.demo.controller;

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
public class WelcomeController implements ErrorController {

    private static Integer count = 0;
    private static List<String> remoteCalls = new ArrayList<>();

    @GetMapping("/")
    public ModelAndView welcome(HttpServletRequest request) {
        count ++;
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("count", count);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String remoteCall = "time: "+LocalDateTime.now().format(formatter)+", Remote address: "+request.getRemoteAddr()+", Remote host: "+request.getRemoteHost()+", User: "+request.getParameter("user");
        remoteCalls.add(remoteCall);
        modelAndView.addObject("remoteCalls", remoteCalls);
        return modelAndView;
    }
}
