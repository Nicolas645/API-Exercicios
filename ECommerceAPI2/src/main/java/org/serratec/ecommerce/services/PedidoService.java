package org.serratec.ecommerce.services;

import java.util.List;

import org.serratec.ecommerce.dtos.PedidoDTO;
import org.serratec.ecommerce.entities.Pedido;
import org.serratec.ecommerce.enums.StatusPedidoEnum;

public interface PedidoService {
	Pedido criarPedido(PedidoDTO pedidoDTO);

	List<Pedido> listarPedidos();

	Pedido buscarPedidoPorId(Long id);

	Pedido atualizarPedido(Long id, Pedido pedidoAtualizado);
	
	Pedido atualizarStatusPedido(Long id, StatusPedidoEnum novoStatus);

	void deletarPedido(Long id);

	double totalizarPedido(Long id);
}
