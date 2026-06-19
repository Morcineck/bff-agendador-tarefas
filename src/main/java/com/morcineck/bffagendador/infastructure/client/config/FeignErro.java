package com.morcineck.bffagendador.infastructure.client.config;

import com.morcineck.bffagendador.infastructure.exceptions.BusinessException;
import com.morcineck.bffagendador.infastructure.exceptions.FeignDecodeException;
import com.morcineck.bffagendador.infastructure.exceptions.ResourceNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FeignErro implements ErrorDecoder {

    private static final String ERROPREFIXO = "Erro: ";

    @Override
    public Exception decode(String s, Response response) {

        String mensagemErro = mensagemErro(response);

        switch (response.status()) {
            case 409:
                return new ClassCastException(ERROPREFIXO + mensagemErro);
            case 403:
                return new ResourceNotFoundException(ERROPREFIXO + mensagemErro);
            case 401:
                return new UnsupportedOperationException(ERROPREFIXO + mensagemErro);
            case 400:
                return new BusinessException(ERROPREFIXO + mensagemErro);
            default:
                return new BusinessException(ERROPREFIXO + mensagemErro);
        }
    }

    private String mensagemErro(Response response) {
        try {
            if(Objects.isNull(response.body())){
                return "";
            }
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FeignDecodeException("Erro ao ler corpo da resposta do Feign", e);

        }
    }

}
