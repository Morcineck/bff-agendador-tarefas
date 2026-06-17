package com.morcineck.bffagendador.infastructure.exceptions;

public class IllegalArgumentExcepiton extends RuntimeException {
    public IllegalArgumentExcepiton(String message) {
        super(message);
    }

    public IllegalArgumentExcepiton(String message, Throwable throwable) {
        super(message, throwable);
    }
}
