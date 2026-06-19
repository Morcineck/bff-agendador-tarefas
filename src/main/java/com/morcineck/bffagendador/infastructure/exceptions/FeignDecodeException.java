package com.morcineck.bffagendador.infastructure.exceptions;

public class FeignDecodeException extends RuntimeException {
    public FeignDecodeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
