package org.serratec.ecommerce.controllers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.serratec.ecommerce.dtos.PedidoDTO;
import org.serratec.ecommerce.entities.Pedido;
import org.serratec.ecommerce.enums.StatusPedidoEnum;
import org.serratec.ecommerce.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    
    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoService.criarPedido(pedidoDTO);
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody Pedido pedido) {
        Pedido pedidoAtualizada = pedidoService.atualizarPedido(id, pedido);
        return ResponseEntity.ok(pedidoAtualizada);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatusPedido(@PathVariable Long id, @RequestBody String novoStatusString) {
        // Converta a string para maiúsculas
        String novoStatusUpperCase = novoStatusString.toUpperCase();
        
        try {
            // Tente converter a string para o enum StatusPedidoEnum
            StatusPedidoEnum novoStatus = StatusPedidoEnum.valueOf(novoStatusUpperCase);
            
            // Atualize o status do pedido
            Pedido pedidoAtualizado = pedidoService.atualizarStatusPedido(id, novoStatus);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (IllegalArgumentException e) {
            // Se a string não corresponder a um valor válido do enum, retorne uma resposta de erro personalizada
            String mensagemDeErro = "Status inválido. Os valores válidos são: " + Arrays.toString(StatusPedidoEnum.values());
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", mensagemDeErro));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
