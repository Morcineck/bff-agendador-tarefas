package com.morcineck.bffagendador.business;


import com.morcineck.bffagendador.business.dto.out.TarefasDTOResponse;
import com.morcineck.bffagendador.infastructure.client.EmailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailClient emailClient;

    public void enviaEmail(TarefasDTOResponse dto) {
        emailClient.enviarEmail(dto);

    }
}

