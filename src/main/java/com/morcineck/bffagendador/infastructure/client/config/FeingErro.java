package com.morcineck.bffagendador.infastructure.client.config;

import com.morcineck.bffagendador.infastructure.exceptions.BusinessException;
import com.morcineck.bffagendador.infastructure.exceptions.ResourceNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FeingErro implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {

        String mensagemErro = mensagemErro(response);

        switch (response.status()) {
            case 409:
                return new ClassCastException("Erro: " + mensagemErro);
            case 403:
                return new ResourceNotFoundException("Erro: " + mensagemErro);
            case 401:
                return new UnsupportedOperationException("Erro: " + mensagemErro);
            case 400:
                return new BusinessException("Erro: " + mensagemErro);
            default:
                return new BusinessException("Erro: " + mensagemErro);
        }
    }

    private String mensagemErro(Response response) {
        try {
            if(Objects.isNull(response.body())){
                return "";
            }
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

}
