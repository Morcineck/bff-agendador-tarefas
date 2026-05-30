package com.morcineck.bffagendador.business;


import com.morcineck.bffagendador.business.dto.in.TarefasDTOResquest;
import com.morcineck.bffagendador.business.dto.out.TarefasDTOResponse;
import com.morcineck.bffagendador.business.enums.StatusNotificacaoEnum;
import com.morcineck.bffagendador.infastructure.client.TarefasClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasClient tarefasClient;

    public TarefasDTOResponse gravaTarefa(String token, TarefasDTOResquest dto) {
        return tarefasClient.gravarTarefas(dto, token);

    }


    public List<TarefasDTOResponse> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial,
                                                                    LocalDateTime dataFinal, String token) {

        return tarefasClient.buscaListaDeTarefasPorPeriodo(dataInicial, dataFinal, token);
    }


    public List<TarefasDTOResponse> buscarTarefaPorEmail(String token) {

        return tarefasClient.buscaTarefasPorEmail(token);
    }

    public void deletaTarefaPorId(String id, String token) {
        tarefasClient.deletaTarefaPorId(id, token);


    }

    public TarefasDTOResponse alteraStatus(StatusNotificacaoEnum status, String id, String token) {
        return tarefasClient.alteraStatusNotificacao(status, id, token);
    }

    public TarefasDTOResponse updateTarefas(TarefasDTOResquest dto, String id, String token) {
        return tarefasClient.updateTarefas(dto, id, token);

    }

}
