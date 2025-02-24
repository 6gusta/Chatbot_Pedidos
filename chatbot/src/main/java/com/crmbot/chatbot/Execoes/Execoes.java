package com.crmbot.chatbot.Execoes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class Execoes {

    private static final Logger logger = LoggerFactory.getLogger(Execoes.class);


    // Tratamento de exceção para PedidoNaoEncontradoException, utilizando ResponseEntity
    @ExceptionHandler(PedidoNaoEncontradoException.class)
    public ResponseEntity<String> handlePedidoNaoEncontrado(PedidoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

  
    @ExceptionHandler(TelNaoEcontrado.class)
    public ResponseEntity<String> handleTelNaoEcontrado(TelNaoEcontrado ex) {
        logger.error("Erro: Número não encontrado", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Tratamento de exceção para ErroProcessamentoException
    @ExceptionHandler(ErroProcessamentoException.class)
    public ResponseEntity<String> handleErroProcessamento(ErroProcessamentoException ex) {
        logger.error( " erro pedido Não Econtrado", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Tratamento de exceção genérica para outras exceções com resposta HTTP
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleErroGeral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor.");
    }

    // Tratamento de exceção genérica para exibir uma página de er
}
