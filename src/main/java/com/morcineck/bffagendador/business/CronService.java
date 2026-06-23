package com.morcineck.bffagendador.business;

import com.morcineck.bffagendador.business.dto.in.LoginRequestDTO;
import com.morcineck.bffagendador.business.dto.out.TarefasDTOResponse;
import com.morcineck.bffagendador.business.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
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
        log.info("Cron iniciado - buscando tarefas da próxima hora");

        String tokenBruto = login(converterParaRequestDTO());
        String token = "Bearer " + tokenBruto.replace("Bearer ", "");

        LocalDateTime horaAtual = LocalDateTime.now();
        LocalDateTime horaFuturaMaisCinco = LocalDateTime.now().plusHours(1);
        List<TarefasDTOResponse> listaTarefas = tarefasService.buscaTarefasAgendadasPorPeriodo(horaAtual,
                horaFuturaMaisCinco, token);

        log.info("Cron encontrou {} tarefa(s) para notificar", listaTarefas.size());

        listaTarefas.forEach(tarefa -> {
            log.info("Enviando email para a tarefa id={}", tarefa.getId());
            emailService.enviaEmail(tarefa);
            tarefasService.alteraStatus(StatusNotificacaoEnum.notificado, tarefa.getId(), token);
        });

        log.info("Cron finalizado");

    }

    private String login(LoginRequestDTO dto) {
        return usuarioServices.loginUsuario(dto);

    }

    public LoginRequestDTO converterParaRequestDTO() {
        return LoginRequestDTO.builder()
                .email(email)
                .senha(senha)
                .build();
    }


}
