package org.ecommerce.pedidos.exceptions;

public class CarrinhoNaoEncontradoException extends RuntimeException {
    public CarrinhoNaoEncontradoException(String message) {
        super(message);
    }
}
