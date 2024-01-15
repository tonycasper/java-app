package com.br.agbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class NotificacaoLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long contaOrigemId;
    private Long contaDestinoId;
    private BigDecimal valorTransferido;
    private LocalDateTime dataTentativa;
    private String status; // Exemplo: "Falha", "Sucesso", "Pendente"
    private String mensagemErro; // Opcional, para detalhar o erro
}
