package com.crmbot.chatbot.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class faqanswer {

    private List<String> keywords;

    private String answer;
    
}
