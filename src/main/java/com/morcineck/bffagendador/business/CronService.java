package com.morcineck.bffagendador.business;

import com.morcineck.bffagendador.business.dto.in.LoginResquestDTO;
import com.morcineck.bffagendador.business.dto.out.TarefasDTOResponse;
import com.morcineck.bffagendador.business.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CronService {

    private final TarefasService tarefasService;
    private final EmailService emailService;
    private final UsuarioService usuarioServices;

    @Value("${usuario.email}")
    private String email;

    @Value("${usuario.senha}")
    private String senha;

    @Scheduled(cron = "${cron.horario}")
    public void buscaTarefasProximaHora() {

        String tokenBruto = login(converterParaRequestDTO());
        // O serviço de usuários retorna o token com "Bearer" colado sem espaço (BearereyJ...).
        // O replace remove qualquer ocorrência de "Bearer" e o trim limpa espaços,
        //  garantindo que o token final fique no formato correto: "Bearer eyJ..."
        String token = "Bearer " + tokenBruto.replace("Bearer ", "").trim();

        LocalDateTime horaAtual = LocalDateTime.now();
        LocalDateTime horaFuturaMisCinco = LocalDateTime.now().plusHours(1);
        List<TarefasDTOResponse> listaTarefas = tarefasService.buscaTarefasAgendadasPorPeriodo(horaAtual,
                horaFuturaMisCinco, token);

        listaTarefas.forEach(tarefa -> {
            emailService.enviaEmail(tarefa);
            tarefasService.alteraStatus(StatusNotificacaoEnum.notificado, tarefa.getId(), token);
        });
    }

    private String login(LoginResquestDTO dto) {
        return usuarioServices.loginUsuario(dto);

    }

    public LoginResquestDTO converterParaRequestDTO() {
        return LoginResquestDTO.builder()
                .email(email)
                .senha(senha)
                .build();
    }


}
