package com.br.agbank.service;

import com.br.agbank.model.NotificacaoLog;
import com.br.agbank.repository.NotificacaoLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @Mock
    private NotificacaoLogRepository notificacaoRepository;

    @Mock
    private TransacaoService transacaoService;

    @InjectMocks
    private AgendamentoService agendamentoService;

    private NotificacaoLog notificacaoPendente;

    @BeforeEach
    public void setup() {
        notificacaoPendente = new NotificacaoLog();
        notificacaoPendente.setStatus("Pendente");
        notificacaoPendente.setContaOrigemId(1L);
        notificacaoPendente.setContaDestinoId(2L);
        notificacaoPendente.setValorTransferido(new BigDecimal("100.00"));
    }

    @Test
    public void quandoExistemNotificacoesPendentes_entaoReenviar() {
        List<NotificacaoLog> notificacoes = Arrays.asList(notificacaoPendente);
        given(notificacaoRepository.findByStatus("Pendente")).willReturn(notificacoes);

        agendamentoService.reenviarNotificacoesPendentes();

        verify(transacaoService, times(1)).enviarNotificacao(anyLong(), anyLong(), any(BigDecimal.class));
        verify(notificacaoRepository, times(1)).save(any(NotificacaoLog.class));
    }

    @Test
    public void quandoReenvioFalha_entaoAtualizarStatusParaFalha() {
        List<NotificacaoLog> notificacoes = Arrays.asList(notificacaoPendente);
        given(notificacaoRepository.findByStatus("Pendente")).willReturn(notificacoes);
        doThrow(new RestClientException("Falha na notificação")).when(transacaoService).enviarNotificacao(anyLong(), anyLong(), any(BigDecimal.class));

        agendamentoService.reenviarNotificacoesPendentes();

        verify(notificacaoRepository, times(1)).save(any());
    }
}