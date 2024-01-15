package com.br.agbank.repository;

import com.br.agbank.model.Cliente;
import com.br.agbank.model.NotificacaoLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificacaoLogRepository extends JpaRepository<NotificacaoLog, Long> {
    List<NotificacaoLog> findByStatus(String pendente);
}
