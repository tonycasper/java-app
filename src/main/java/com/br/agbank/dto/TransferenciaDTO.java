package com.br.agbank.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferenciaDTO {
    private Long idContaOrigem;
    private Long idContaDestino;
    private BigDecimal valor;

    // Getters e setters omitidos para brevidade
}