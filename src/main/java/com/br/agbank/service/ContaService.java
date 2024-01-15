package com.br.agbank.service;

import com.br.agbank.model.Cliente;
import com.br.agbank.model.Conta;
import com.br.agbank.repository.ClienteRepository;
import com.br.agbank.repository.ContaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;

    public Conta salvarConta(Conta conta) {
        Optional<Conta> documento = contaRepository.findByAgencia(conta.getAgencia());

        if (documento.isEmpty()){
            return contaRepository.save(conta);
        } else {
            throw new IllegalArgumentException("Agencia já castrada no Sistema.");
        }
    }
}
