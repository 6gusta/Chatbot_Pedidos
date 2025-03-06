package com.crmbot.chatbot.Service;

import org.springframework.stereotype.Service;
import com.crmbot.chatbot.util.Faqanswers;
import com.crmbot.chatbot.domain.faqanswer;

import java.util.Arrays;
import java.util.List;

@Service
public class FaqService {

    private final Faqanswers faqanswers = new Faqanswers();

    public String getAnswer(String question) {
        String[] words = question.toLowerCase().split("\\s+");
       List wordlist = Arrays.asList(words).stream().map(String::toLowerCase).toList();
       System.out.println(wordlist);

        for (faqanswer entry : faqanswers.getAnswers()) {
            for (String keyword : entry.getKeywords()) {
                if (wordlist.contains(keyword.toLowerCase())) {
                    return entry.getAnswer();
                }
            }
        }

        return faqanswers.getDefaultAnswer();
    }


}
