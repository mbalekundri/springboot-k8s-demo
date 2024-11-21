package com.springboot.k8s.demo.enums;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum VoterNames {
    Satish,
    Rohit,
    Ravi,
    Bala,
    Spoorthi,
    Bhawani,
    Narendra,
    Zulbie,
    Mehdi,
    Tazaeen;

    // Return all voter names in List of string format
    public static List<String> getVoterNamesList() {
        return Arrays.asList(VoterNames.values()).stream().map(Enum::name).collect(Collectors.toList());
    }

}