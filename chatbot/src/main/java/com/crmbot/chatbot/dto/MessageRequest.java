package com.crmbot.chatbot.dto;

public record MessageRequest(
    String message,
    String nome,
    String intempedido,
    String formaDePagamento,
    String numero
) {

    
}

