package org.serratec.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.entities.ItemPedido;
import org.serratec.ecommerce.entities.Pedido;
import org.serratec.ecommerce.exceptions.ResourceNotFoundException;
import org.serratec.ecommerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public Pedido criarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
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