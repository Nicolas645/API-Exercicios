package org.serratec.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.serratec.ecommerce.entities.ItemPedido;
import org.serratec.ecommerce.exceptions.ResourceNotFoundException;
import org.serratec.ecommerce.repositories.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Override
    public ItemPedido criarItemPedido(ItemPedido itemPedido) {
        return itemPedidoRepository.save(itemPedido);
    }

    @Override
    public List<ItemPedido> listarItensPedido() {
        return itemPedidoRepository.findAll();
    }

    @Override
    public ItemPedido buscarItemPedidoPorId(Long id) {
        Optional<ItemPedido> itemPedido = itemPedidoRepository.findById(id);
        if (itemPedido.isEmpty()) {
            throw new ResourceNotFoundException("Item do Pedido não encontrado com ID: " + id);
        }
        return itemPedido.get();
    }

    @Override
    public ItemPedido atualizarItemPedido(Long id, ItemPedido itemPedidoAtualizado) {
        ItemPedido itemPedido = buscarItemPedidoPorId(id);
        itemPedido.setQuantidade(itemPedidoAtualizado.getQuantidade());
        itemPedido.setPrecoUnitario(itemPedidoAtualizado.getPrecoUnitario());
        
        
        return itemPedidoRepository.save(itemPedido);
    }

    @Override
    public void deletarItemPedido(Long id) {
        ItemPedido itemPedido = buscarItemPedidoPorId(id);
        itemPedidoRepository.delete(itemPedido);
    }
}
