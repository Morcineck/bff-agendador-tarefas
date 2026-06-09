package com.morcineck.bffagendador.infastructure.client.config;

import com.morcineck.bffagendador.infastructure.exceptions.BusinessException;
import com.morcineck.bffagendador.infastructure.exceptions.ResourceNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeingErro implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {

        switch (response.status()) {
            case 409:
                return new ClassCastException("Erro atributo já existente ");
                case 403:
                    return new ResourceNotFoundException("Erro atributo não encontrado");
                    case 401:
                        return new UnsupportedOperationException("Erro usuário não encontrado");
                        default:
                            return new BusinessException("Erro de servidor");
        }
    }

}
