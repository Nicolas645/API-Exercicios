package org.serratec.ecommerce.exceptions;

public class StatusPedidoInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public StatusPedidoInvalidoException(String status) {
        super("Status do pedido inválido: " + status);
    }
}
