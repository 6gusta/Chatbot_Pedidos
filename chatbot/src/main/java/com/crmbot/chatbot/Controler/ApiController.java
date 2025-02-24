package com.crmbot.chatbot.Controler;





import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;  // Importando a interface correta para List
import java.time.LocalDateTime;

import com.crmbot.chatbot.Execoes.PedidoNaoEncontradoException;
import com.crmbot.chatbot.Execoes.TelNaoEcontrado;
import com.crmbot.chatbot.Model.Intems;
import com.crmbot.chatbot.Model.Pedidos;
import com.crmbot.chatbot.Service.ContatosService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500") // Restrinja a origem para o domínio de desenvolvimento

public class ApiController {


    @GetMapping("/total")
    public double getTotalVendas() {
        return contatosService.calcularTotalVendas();
    }
    

    @Autowired
    private ContatosService contatosService;

    @PostMapping("/PostEstoque")
    public ResponseEntity<String> AdcionaEstoque(@RequestBody Intems intens){
        try {

            System.out.println("Dados recebidos: " + intens);

            contatosService.InserirEstoque(intens.getNomeProduto(), intens.getValor(),intens.getQtdeEstoque());

            return ResponseEntity.ok("{\"message\": \"Pedidos do Estoque processados com sucesso\"}");

            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao processar os dados", HttpStatus.BAD_REQUEST);

    }
}


    
    @PostMapping("/sendData")
    public ResponseEntity<String> sendData(@RequestBody Pedidos pedidos) {
        try {

            System.out.println("Dados recebidos: " + pedidos);
            System.out.println("Item do Pedido: " + pedidos.getIntemPedido());
            System.out.println("Nome: " + pedidos.getNome());
            System.out.println("Forma de Pagamento: " + pedidos.getFormaDepagamneto());
                
            

            contatosService.PedidosClientes(pedidos.getNome(), pedidos.getIntemPedido(), pedidos.getFormaDepagamneto(), pedidos.getNumero());
    
            return ResponseEntity.ok("Dados processados com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro ao processar os dados", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPedidos")
    public ResponseEntity<List<Pedidos>> getPedidos(@RequestParam(value = "status", defaultValue = "Pendente") String status) {
    List<Pedidos> pedidos = contatosService.buscarPedidosPorStatus(status); 
    return ResponseEntity.ok(pedidos);
}
@GetMapping("/GetNumero")
public ResponseEntity<List<Pedidos>> getNumero(@RequestParam("numero") String numero) {
    List<Pedidos> pedidos = contatosService.buscaPedidoPorTelefone(numero);
    if(pedidos == null || pedidos.isEmpty()){
        throw new TelNaoEcontrado(" numero Não Econtrado");
    }
    return ResponseEntity.ok(pedidos);
}

@GetMapping("/GetEstoque")
public ResponseEntity<List<Intems>> getEstoque(@RequestParam("NomeProduto") String NomeProduto){

    List<Intems> intens = contatosService.BuscaEstoque(NomeProduto);
        return ResponseEntity.ok(intens);
    }
    



    @PostMapping("/api/pedidos/{idpedido}/finalizar")
    public ResponseEntity<?> finalizarPedidos(@PathVariable("idpedido") Long idpedido) {
  
            Pedidos pedidoFinalizado = contatosService.finalizarpedido(idpedido); 
            if (pedidoFinalizado == null) {
                throw new PedidoNaoEncontradoException("Pedido não encontrado.");
            }
            return ResponseEntity.ok(pedidoFinalizado); 
       
    }

    @PostMapping("/api/pedidos/{idpedido}/EmAndamento")
    public ResponseEntity<?> PedidosEmAndamento(@PathVariable("idpedido") Long idpedido){
   

            Pedidos pedidoEmAndamento = contatosService.EmAndamentos(idpedido);
            if (pedidoEmAndamento == null) {
                throw new PedidoNaoEncontradoException("Pedido não encontrado.");
            }
            return ResponseEntity.ok(pedidoEmAndamento);
            
     
    }

    @PostMapping("/api/pedidos/{idpedido}/SaiuPraEntrega")
    public ResponseEntity<?> SaiuPraEntrega(@PathVariable("idpedido") Long idpedido){
    
            Pedidos SaiuPraentrega = contatosService.SaiuPraEntrega(idpedido);
            if (SaiuPraentrega == null) {
                throw new PedidoNaoEncontradoException("Pedido não encontrado.");
            }

            return ResponseEntity.ok(SaiuPraentrega);
        }
    

    @DeleteMapping("/api/pedidos/{idIntems}/excluir")
    public ResponseEntity<?> excluirintem(@PathVariable("idIntems") Long idIntems){
   
            
            boolean sucesso = contatosService.ExcluirdoEstoque(idIntems);
            if (sucesso) {
                return ResponseEntity.ok("Pedido cancelado com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Intem não encontrado.");
            }
     
            // TODO: handle exception

     
    }

    @DeleteMapping("/api/pedidos/{idpedido}/cancelar")
    public ResponseEntity<?> cancelarPedidos(@PathVariable("idpedido") Long idpedido){

      
            boolean sucesso = contatosService.cancelarPedido(idpedido); 

            if (sucesso) {
                return ResponseEntity.ok("Pedido cancelado com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado.");
            }
   
    }





}

   






    



    
    

    
    

