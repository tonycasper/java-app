package com.br.agbank.service;

import com.br.agbank.model.Conta;
import com.br.agbank.model.NotificacaoLog;
import com.br.agbank.repository.ContaRepository;
import com.br.agbank.repository.NotificacaoLogRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class TransacaoService {

    private ContaRepository contaRepository;
    private NotificacaoLogRepository notificacaoLogRepository;
    private RestTemplate restTemplate;
    private static final String NOTIFICATION_ENDPOINT = "https://run.mocky.io/v3/9769bf3a-b0b6-477a-9ff5-91f63010c9d3";
    private Logger logger;

    @Transactional
    public void realizarTransferencia(Long idContaOrigem, Long idContaDestino, BigDecimal valor) {
        Conta contaOrigem = contaRepository.findById(idContaOrigem).orElseThrow(/* exceção */);
        Conta contaDestino = contaRepository.findById(idContaDestino).orElseThrow(/* exceção */);

        validarTransferencia(contaOrigem, valor);

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        enviarNotificacao(contaOrigem.getId(), contaDestino.getId(), valor);
    }

    private void validarTransferencia(Conta conta, BigDecimal valor) {
        if (!conta.getStatus().equals("Ativa") || conta.getSaldo().compareTo(valor) < 0) {
            throw new IllegalStateException("Transferência inválida.");
        }
    }

    public void enviarNotificacao(Long contaOrigem, Long contaDestino, BigDecimal valor) {
        Map<String, String> notificationDetails = new HashMap<>();
        notificationDetails.put("contaOrigemId", contaOrigem.toString());
        notificationDetails.put("contaDestinoId", contaDestino.toString());
        notificationDetails.put("valorTransferido", valor.toString());

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(NOTIFICATION_ENDPOINT, notificationDetails, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Notificação enviada com sucesso para a conta de origem ID: " + contaOrigem + " e conta de destino ID: " + contaDestino);
            } else {
                registrarFalhaNotificacao(contaOrigem, contaDestino, valor);
                agendarReenvioNotificacao(contaOrigem, contaDestino, valor);
            }

        } catch (RestClientException e) {
            registrarFalhaNotificacao(contaOrigem, contaDestino, valor);
            agendarReenvioNotificacao(contaOrigem, contaDestino, valor);
        }
    }

    private void registrarFalhaNotificacao(Long contaOrigem, Long contaDestino, BigDecimal valor) {
        NotificacaoLog log = new NotificacaoLog();
        log.setContaOrigemId(contaOrigem);
        log.setContaDestinoId(contaDestino);
        log.setValorTransferido(valor);
        log.setDataTentativa(LocalDateTime.now());
        log.setStatus("Falha");
        log.setMensagemErro("Detalhes do erro");

        notificacaoLogRepository.save(log);
        logger.error("Falha ao enviar notificação.");
    }

    private void agendarReenvioNotificacao(Long contaOrigem, Long contaDestino, BigDecimal valor) {
        NotificacaoLog log = new NotificacaoLog();
        log.setContaOrigemId(contaOrigem);
        log.setContaDestinoId(contaDestino);
        log.setValorTransferido(valor);
        log.setDataTentativa(LocalDateTime.now());
        log.setStatus("Pendente");

        notificacaoLogRepository.save(log);
    }
}
