package com.springboot.k8s.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "candidate")
@Getter
@Setter
public class CandidateConfigProperties {
    private List<String> names;
}
