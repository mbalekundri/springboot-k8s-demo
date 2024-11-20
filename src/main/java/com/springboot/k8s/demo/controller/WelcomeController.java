package com.springboot.k8s.demo.controller;

import com.springboot.k8s.demo.enums.VoterNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class WelcomeController implements ErrorController {

    private static Integer count = 0;

    @Value("${APP_ENV}")
    private String appEnv;

    private static List<String> remoteCalls = new ArrayList<>();
    private static Map<String, Integer> votes = new HashMap<>();

    @GetMapping("/")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("count", count);
        modelAndView.addObject("appEnv", appEnv);
        log.info("appEnv: "+appEnv);
        modelAndView.addObject("remoteCalls", remoteCalls);
        // Sort votes by value using java 8 streams
        Map<String, Integer> sortedVotes = votes.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        modelAndView.addObject("votes", sortedVotes);
        modelAndView.addObject("voterNames", VoterNames.values());
        modelAndView.addObject("candidates", VoterNames.values());
        return modelAndView;
    }

    @PostMapping("/vote")
    public String vote(@RequestParam("voter") String voter, @RequestParam("voteFor") String voteFor, HttpServletRequest request) {
        log.info("Received vote from: " + voter);
        count ++;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String remoteCall = LocalDateTime.now().format(formatter)+", Remote address: "+request.getRemoteAddr()+", Remote host: "+request.getRemoteHost()+", Voter: "+voter+ " voted  : "+voteFor;
        remoteCalls.add(remoteCall);
        votes.put(voteFor, votes.getOrDefault(voteFor, 0) + 1);
        return "redirect:/";
    }

}
