package com.br.agbank.repository;

import com.br.agbank.model.Cliente;
import com.br.agbank.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Cliente, Long> {
    Optional<Conta> findByAgencia(String agencia);
}
