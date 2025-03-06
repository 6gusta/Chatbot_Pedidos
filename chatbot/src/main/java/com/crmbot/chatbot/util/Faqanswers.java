package com.crmbot.chatbot.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.core.io.ClassPathResource;

import com.crmbot.chatbot.domain.faqanswer;

import lombok.Getter;
@Getter
public class Faqanswers {

    private ArrayList<faqanswer>  answers ;
  private   String defaultAnswer;

  public Faqanswers(){

    try {
        // Carrega o arquivo JSON do classpath
        ClassPathResource resource = new ClassPathResource("answers.json");
        InputStream inputStream = resource.getInputStream();
        
        // Lê o conteúdo do arquivo
        String jsonString;
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            jsonString = scanner.useDelimiter("\\A").next();
        }

        // Processa o JSON
        JSONObject faqData = new JSONObject(jsonString);
        JSONArray faqArray = faqData.getJSONArray("faq");
        this.answers = new ArrayList<>();

        for (int i = 0; i < faqArray.length(); i++) {
            JSONObject faqEntry = faqArray.getJSONObject(i);
            JSONArray keywordsArray = faqEntry.getJSONArray("keywords");
            List<String> keywords = new ArrayList<>();
            for (int j = 0; j < keywordsArray.length(); j++) {
                keywords.add(keywordsArray.getString(j));
            }
            String answer = faqEntry.getString("answer");
            this.answers.add(new faqanswer(keywords, answer));
        }

        this.defaultAnswer = faqData.getString("default");

    } catch (IOException e) {
        System.err.println("Erro ao acessar o arquivo 'answers.json'.");
        e.printStackTrace();
    } catch (Exception e) {
        System.err.println("Erro ao processar o arquivo 'answers.json'.");
        e.printStackTrace();
    }
}


    
}
