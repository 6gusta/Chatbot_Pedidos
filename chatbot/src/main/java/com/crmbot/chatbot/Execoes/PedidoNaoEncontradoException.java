package com.crmbot.chatbot.Execoes;

public class PedidoNaoEncontradoException  extends RuntimeException{

    public  PedidoNaoEncontradoException(String mensagem){
        super(mensagem);
    }
    
}
