package com.br.agbank.service;

import com.br.agbank.model.NotificacaoLog;
import com.br.agbank.repository.NotificacaoLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
@AllArgsConstructor
public class AgendamentoService {
    private final NotificacaoLogRepository notificacaoRepository;
    private final TransacaoService transacaoService;

    @Scheduled(fixedDelay = 10000)
    public void reenviarNotificacoesPendentes() {
        List<NotificacaoLog> notificacoesPendentes = notificacaoRepository.findByStatus("Pendente");

        for (NotificacaoLog notificacao : notificacoesPendentes) {
            try {
                transacaoService.enviarNotificacao(notificacao.getContaOrigemId(), notificacao.getContaDestinoId(), notificacao.getValorTransferido());
                notificacao.setStatus("Enviado");
                notificacaoRepository.save(notificacao);
            } catch (RestClientException e) {
                notificacao.setStatus("Falha");
                notificacaoRepository.save(notificacao);
            }
        }
    }
}
