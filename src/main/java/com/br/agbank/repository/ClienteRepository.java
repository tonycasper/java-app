package com.br.agbank.repository;

import com.br.agbank.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Métodos de consulta específicos
}