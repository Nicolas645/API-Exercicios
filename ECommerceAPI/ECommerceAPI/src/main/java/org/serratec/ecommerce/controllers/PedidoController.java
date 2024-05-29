package org.serratec.ecommerce.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.serratec.ecommerce.entities.Categoria;
import org.serratec.ecommerce.entities.Cliente;
import org.serratec.ecommerce.entities.Pedido;
import org.serratec.ecommerce.entities.Produto;
import org.serratec.ecommerce.services.DadosService;
import org.serratec.ecommerce.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DadosService dadosService;
    
    @PostMapping()
    public ResponseEntity<Map<String, Object>> gerarPedido() {
        Cliente cliente = dadosService.buscarClientePorId(1L); // Supondo que o cliente desejado tenha ID 1
        Produto produto = dadosService.buscarProdutoPorId(1L); // Supondo que o produto desejado tenha ID 1
        Categoria categoria = produto.getCategoria();

        Map<String, Object> pedido = new HashMap<>();
        pedido.put("id", 0);

        Map<String, Object> clienteJson = new HashMap<>();
        clienteJson.put("id", cliente.getId());
        clienteJson.put("nome", cliente.getNome());
        clienteJson.put("telefone", cliente.getTelefone());
        clienteJson.put("email", cliente.getEmail());
        clienteJson.put("senha", cliente.getSenha());
        clienteJson.put("fotourl", cliente.getFotourl());
        clienteJson.put("cpf", cliente.getCpf());
        clienteJson.put("cep", cliente.getCep());
        pedido.put("cliente", clienteJson);

        pedido.put("dataCriacao", "2024-05-27T23:50:31.206Z"); // Exemplo de data de criação
        pedido.put("status", "string"); // Exemplo de status

        List<Map<String, Object>> itens = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();
        item.put("id", 0);
        item.put("pedido", "string"); // Exemplo de ID do pedido

        Map<String, Object> produtoJson = new HashMap<>();
        produtoJson.put("id", produto.getId());
        produtoJson.put("nome", produto.getNome());
        produtoJson.put("descricao", produto.getDescricao());
        produtoJson.put("quantidadeEstoque", produto.getQuantidadeEstoque());
        produtoJson.put("preco", produto.getPreco());

        Map<String, Object> categoriaJson = new HashMap<>();
        categoriaJson.put("id", categoria.getId());
        categoriaJson.put("nome", categoria.getNome());
        categoriaJson.put("descricao", categoria.getDescricao());
        produtoJson.put("categoria", categoriaJson);

        item.put("produto", produtoJson);
        item.put("quantidade", 1); // Exemplo de quantidade
        item.put("precoUnitario", produto.getPreco());
        item.put("subtotal", produto.getPreco()); // Exemplo de subtotal

        itens.add(item);
        pedido.put("itens", itens);
        pedido.put("total", produto.getPreco()); // Exemplo de total

        return ResponseEntity.ok(pedido);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }
}