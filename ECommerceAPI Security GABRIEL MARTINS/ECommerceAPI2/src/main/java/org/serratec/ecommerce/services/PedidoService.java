package org.serratec.ecommerce.services;

import java.util.List;

import org.serratec.ecommerce.entities.Pedido;

public interface PedidoService {
	Pedido criarPedido(Pedido pedido);

	List<Pedido> listarPedidos();

	Pedido buscarPedidoPorId(Long id);

	Pedido atualizarPedido(Long id, Pedido pedidoAtualizado);

	void deletarPedido(Long id);

	double totalizarPedido(Long id);
}
