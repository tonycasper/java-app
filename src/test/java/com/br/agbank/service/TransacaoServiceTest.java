package com.br.agbank.service;


import com.br.agbank.model.Conta;
import com.br.agbank.model.NotificacaoLog;
import com.br.agbank.repository.ContaRepository;
import com.br.agbank.repository.NotificacaoLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private NotificacaoLogRepository notificacaoLogRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TransacaoService transacaoService;

    private Conta contaOrigem;
    private Conta contaDestino;
    private BigDecimal valorTransferencia;

    @BeforeEach
    public void setup() {
        contaOrigem = new Conta();
        contaDestino = new Conta();
        contaOrigem.setSaldo(new BigDecimal("500.00")));
        contaDestino.setSaldo(new BigDecimal("200.00"));
    }

    @Test
    public void quandoRealizarTransferencia_entaoAtualizarSaldos() {
        given(contaRepository.findById(contaOrigem.getId())).willReturn(Optional.of(contaOrigem));
        given(contaRepository.findById(contaDestino.getId())).willReturn(Optional.of(contaDestino));

        transacaoService.realizarTransferencia(contaOrigem.getId(), contaDestino.getId(), valorTransferencia);

        assertThat(contaOrigem.getSaldo()).isEqualByComparingTo("400.00");
        assertThat(contaDestino.getSaldo()).isEqualByComparingTo("300.00");
    }

    @Test
    public void quandoFalharNotificacao_entaoRegistrarFalha() {
        given(contaRepository.findById(contaOrigem.getId())).willReturn(Optional.of(contaOrigem));
        given(contaRepository.findById(contaDestino.getId())).willReturn(Optional.of(contaDestino));
        doThrow(new RestClientException("Falha na notificação")).when(restTemplate).postForEntity(anyString(), any(), any());

        transacaoService.realizarTransferencia(contaOrigem.getId(), contaDestino.getId(), valorTransferencia);

        verify(notificacaoLogRepository, times(1)).save(any(NotificacaoLog.class));
    }
}