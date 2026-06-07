package com.morcineck.bffagendador.controller;

import com.morcineck.bffagendador.business.TarefasService;
import com.morcineck.bffagendador.business.dto.in.TarefasDTOResquest;
import com.morcineck.bffagendador.business.dto.out.TarefasDTOResponse;
import com.morcineck.bffagendador.business.enums.StatusNotificacaoEnum;
import com.morcineck.bffagendador.infastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Cadastra tarefas de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class TarefasController {

    private final TarefasService tarefasService;

    // Método auxiliar que garante que o token JWT sempre esteja no formato correto "Bearer eyJ..."
    // Mesmo problema identificado no UsuarioController — token sem espaço causa 403 Forbidden.
    private String formatarToken(String token) {
        if (token == null) return null; // Retorna nulo se nenhum token foi enviado
        return token.startsWith("Bearer ")
                ? token // Token já está correto, usa como está
                : "Bearer " + token.replace("Bearer", "").trim(); // Remove "Bearer" colado, adiciona com espaço correto
    }

    @PostMapping
    @Operation(summary = "Salvar tarefas de usuários", description = "Cria uma nova tarefa")
    @ApiResponse(responseCode = "200", description = "Tarefa salva com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TarefasDTOResponse> gravarTarefas(@RequestBody TarefasDTOResquest dto,
                                                            @RequestHeader(value = "Authorization", required = false) String token) {
        // Token formatado antes de repassar para evitar 403 no serviço de tarefas
        return ResponseEntity.ok(tarefasService.gravaTarefa(formatarToken(token), dto));
    }

    @GetMapping("/eventos")
    @Operation(summary = "Busca tarefas por período", description = "busca tarefas cadastradas por período")
    @ApiResponse(responseCode = "200", description = "Tarefa encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<List<TarefasDTOResponse>> buscaListaDeTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader(name = "Authorization", required = false) String token) {
        // Token formatado antes de repassar para evitar 403 no serviço de tarefas
        return ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal, formatarToken(token)));
    }

    @GetMapping
    @Operation(summary = "Busca lista de tarefas por email de usuário", description = "busca tarefas cadastradas por período")
    @ApiResponse(responseCode = "200", description = "Tarefa encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<List<TarefasDTOResponse>> buscaTarefasPorEmail(
            @RequestHeader(name = "Authorization", required = false) String token) {
        // Token formatado antes de repassar para evitar 403 no serviço de tarefas
        return ResponseEntity.ok(tarefasService.buscarTarefaPorEmail(formatarToken(token)));
    }

    @DeleteMapping
    @Operation(summary = "Deleta tarefas por id", description = "Deleta tarefas cadastradas por id")
    @ApiResponse(responseCode = "200", description = "Tarefa deletada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id,
                                                  @RequestHeader(name = "Authorization", required = false) String token) {
        // Token formatado antes de repassar para evitar 403 no serviço de tarefas
        tarefasService.deletaTarefaPorId(id, formatarToken(token));
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Operation(summary = "Altera status de tarefas", description = "Altera status de tarefas cadastradas")
    @ApiResponse(responseCode = "200", description = "Status da tarefa alterado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TarefasDTOResponse> alteraStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum status,
                                                                      @RequestParam("id") String id,
                                                                      @RequestHeader(name = "Authorization", required = false) String token) {
        // Token formatado antes de repassar para evitar 403 no serviço de tarefas
        return ResponseEntity.ok(tarefasService.alteraStatus(status, id, formatarToken(token)));
    }

    @PutMapping
    @Operation(summary = "Altera dados de tarefas", description = "Altera dados das tarefas cadastradas")
    @ApiResponse(responseCode = "200", description = "Tarefa alterada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TarefasDTOResponse> updateTarefas(@RequestBody TarefasDTOResquest dto,
                                                            @RequestParam("id") String id,
                                                            @RequestHeader(name = "Authorization", required = false) String token) {
        // Token formatado antes de repassar para evitar 403 no serviço de tarefas
        return ResponseEntity.ok(tarefasService.updateTarefas(dto, id, formatarToken(token)));
    }
}