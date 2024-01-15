package com.br.agbank.service;

import com.br.agbank.model.Conta;
import com.br.agbank.repository.ClienteRepository;
import com.br.agbank.repository.ContaRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class TransacaoService {

    private ContaRepository contaRepository;

    @Transactional
    public void realizarTransferencia(Long idContaOrigem, Long idContaDestino, BigDecimal valor) {
        Conta contaOrigem = contaRepository.findById(idContaOrigem).orElseThrow(/* exceção */);
        Conta contaDestino = contaRepository.findById(idContaDestino).orElseThrow(/* exceção */);

        validarTransferencia(contaOrigem, valor);

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        enviarNotificacao(contaOrigem, contaDestino, valor);
    }

    private void validarTransferencia(Conta conta, BigDecimal valor) {
        if (!conta.getStatus().equals("Ativa") || conta.getSaldo().compareTo(valor) < 0) {
            throw new IllegalStateException("Transferência inválida.");
        }
    }

    private void enviarNotificacao(Conta contaOrigem, Conta contaDestino, BigDecimal valor) {
    }
}
