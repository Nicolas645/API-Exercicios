package org.serratec.ecommerce.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.dtos.PedidoDTO;
import org.serratec.ecommerce.entities.Cliente;
import org.serratec.ecommerce.entities.ItemPedido;
import org.serratec.ecommerce.entities.Pedido;
import org.serratec.ecommerce.entities.Produto;
import org.serratec.ecommerce.enums.StatusPedidoEnum;
import org.serratec.ecommerce.exceptions.ResourceNotFoundException;
import org.serratec.ecommerce.repositories.ClienteRepository;
import org.serratec.ecommerce.repositories.ItemPedidoRepository;
import org.serratec.ecommerce.repositories.PedidoRepository;
import org.serratec.ecommerce.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Pedido criarPedido(PedidoDTO pedidoDTO) {
        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + pedidoDTO.getClienteId()));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedidoEnum.PENDENTE); // Definindo o status usando o enum
        pedido.setItens(new ArrayList<>());
        pedido.setDataCriacao(LocalDateTime.now());

        double total = 0.0;

        for (PedidoDTO.ItemPedidoDTO itemDTO : pedidoDTO.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + itemDTO.getProdutoId()));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemDTO.getQuantidade());
            itemPedido.setPrecoUnitario(produto.getPreco());
            itemPedido.setSubtotal(produto.getPreco() * itemDTO.getQuantidade());

            pedido.getItens().add(itemPedido);
            total += itemPedido.getSubtotal();
        }

        pedido.setTotal(total);
        pedidoRepository.save(pedido);

        for (ItemPedido itemPedido : pedido.getItens()) {
            itemPedidoRepository.save(itemPedido);
        }

        return pedido;
    }


    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido buscarPedidoPorId(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isEmpty()) {
            throw new ResourceNotFoundException("Pedido não encontrado com ID: " + id);
        }
        return pedido.get();
    }

    @Override
    public Pedido atualizarPedido(Long id, Pedido pedidoAtualizado) {
        Pedido pedido = buscarPedidoPorId(id);
        pedido.setCliente(pedidoAtualizado.getCliente());
        pedido.setItens(pedidoAtualizado.getItens());
        pedido.setStatus(pedidoAtualizado.getStatus());
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido atualizarStatusPedido(Long id, StatusPedidoEnum novoStatus) {
        Pedido pedido = buscarPedidoPorId(id);
        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }
    
    
    @Override
    public void deletarPedido(Long id) {
        Pedido pedido = buscarPedidoPorId(id);
        pedidoRepository.delete(pedido);
    }

    @Override
    public double totalizarPedido(Long id) {
        Pedido pedido = buscarPedidoPorId(id);
        double total = 0.0;

        for (ItemPedido item : pedido.getItens()) {
            double subtotalItem = item.getProduto().getPreco() * item.getQuantidade();
            total += subtotalItem;
        }

        return total;
    }

}
