package com.springboot.k8s.demo.controller;

import com.springboot.k8s.demo.config.CandidateConfigProperties;
import com.springboot.k8s.demo.config.VoterConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class WelcomeController implements ErrorController {

    private static Integer count = 0;
    private static boolean clearData = false;

    @Autowired
    private VoterConfigProperties voterConfigProperties;

    @Autowired
    private CandidateConfigProperties candidateConfigProperties;

    @Value("${APP_ENV}")
    private String appEnv;

    @Value("${NO_VOTING_ALLOWED}")
    private Integer NO_VOTING_ALLOWED;

    private static List<String> remoteCalls = new ArrayList<>();

    private static Map<String, Integer> votersMap = new HashMap<>();
    private static Map<String, Integer> candidateVotesMap = new HashMap<>();

    private List<String> voterNamesList;
    private List<String> candidatesList;
    private String errorMessage;

    @PostConstruct
    public void init() {
        voterNamesList = getSortedVoterNamesList();
        candidatesList = getSortedCandidatesList();
    }

    private List<String> getSortedVoterNamesList() {
        List<String> namesList = new ArrayList<>(voterConfigProperties.getNames());
        Collections.sort(namesList);
        return namesList;
    }

    private List<String> getSortedCandidatesList() {
        List<String> candidates = new ArrayList<>(candidateConfigProperties.getNames());
        Collections.sort(candidates);
        return candidates;
    }

    @GetMapping("/")
    public ModelAndView welcome(HttpSession session) {
        if (clearData) {
            clearAllData();
        }
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("count", count);
        modelAndView.addObject("appEnv", appEnv);
        log.info("appEnv: " + appEnv);
        modelAndView.addObject("remoteCalls", remoteCalls);
        modelAndView.addObject("votes", getSortedVotes());
        modelAndView.addObject("voterNames", getSortedVoterNames());
        modelAndView.addObject("candidates", candidatesList);
        modelAndView.addObject("errorMessage", errorMessage);
        modelAndView.addObject("sessionValid", isSessionValid(session));
        return modelAndView;
    }

    private void clearAllData() {
        candidateVotesMap.clear();
        votersMap.clear();
        remoteCalls.clear();
        count = 0;
        clearData = false;
        init();
    }

    private Map<String, Integer> getSortedVotes() {
        return candidateVotesMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private List<String> getSortedVoterNames() {
        Collections.sort(voterNamesList);
        return voterNamesList;
    }

    private boolean isSessionValid(HttpSession session) {
        boolean sessionValid = session.getAttribute("votersSession") != null;
        log.info("Session is " + (sessionValid ? "valid" : "not valid"));
        return sessionValid;
    }

    @PostMapping("/vote")
    public String vote(@RequestParam("voter") String voter, @RequestParam("voteFor") String voteFor, HttpServletRequest request, HttpSession session) {

        if (voter.equalsIgnoreCase(voteFor)) {
            log.info("Voter and candidate cannot be same");
            errorMessage = "Voter and candidate cannot be same";
            return "redirect:/";
        }
        errorMessage = null;
        count++;
        String remoteCall = String.format("%s, Remote address: %s, Remote host: %s, Voter: %s voted: %s",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getRemoteAddr(), request.getRemoteHost(), voter, voteFor);
        log.debug(remoteCall);
        remoteCalls.add(remoteCall);

        int votersCount = votersMap.merge(voter, 1, Integer::sum);
        log.info("Voting count for: " + voter + " is: " + votersCount);
        candidateVotesMap.merge(voteFor, 1, Integer::sum);

        if (votersCount == NO_VOTING_ALLOWED) {
            log.info("Voting limit reached for: " + voter);
            voterNamesList.remove(voter);
        }
        if (voterNamesList.isEmpty()) {
            session.setAttribute("votersSession", null);
        }
        return "redirect:/";
    }

    @PostMapping("/startVoting")
    public String startVoting(HttpServletRequest request) {
        request.getSession(true).setAttribute("votersSession", "sessionData");
        clearData = true;
        return "redirect:/";
    }

}
