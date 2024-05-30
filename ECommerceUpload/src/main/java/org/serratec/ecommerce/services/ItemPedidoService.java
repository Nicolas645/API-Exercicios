package org.serratec.ecommerce.services;

import java.util.List;

import org.serratec.ecommerce.entities.ItemPedido;

public interface ItemPedidoService {
	ItemPedido criarItemPedido(ItemPedido itemPedido);

	List<ItemPedido> listarItensPedido();

	ItemPedido buscarItemPedidoPorId(Long id);

	ItemPedido atualizarItemPedido(Long id, ItemPedido itemPedidoAtualizado);

	void deletarItemPedido(Long id);
}
