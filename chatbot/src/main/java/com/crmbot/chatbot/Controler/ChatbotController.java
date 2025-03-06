package com.crmbot.chatbot.Controler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crmbot.chatbot.Service.FaqService;
import com.crmbot.chatbot.dto.MessageRequest;
import com.crmbot.chatbot.dto.MessageRespnse;
import com.crmbot.chatbot.util.Faqanswers;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/bot")
public class ChatbotController {


  final   private FaqService faqService;

    public ChatbotController( FaqService faqService){

        this.faqService = faqService;

    }

    @PostMapping("/chat")
    public ResponseEntity<MessageRespnse> answerQuestion( @RequestBody MessageRequest request ){

      String answer = this.faqService.getAnswer(request.message());

      MessageRespnse response = new  MessageRespnse(answer);


       return  ResponseEntity.ok(response);


    }
    
}
