package com.minibanco.exceptions;

public class LimiteDiarioExcedidoException extends Exception {
    public LimiteDiarioExcedidoException(String mensaje) {
        super(mensaje);
    }
}
