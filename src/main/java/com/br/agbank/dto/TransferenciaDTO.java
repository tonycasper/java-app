package com.br.agbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransferenciaDTO {
    private Long idContaOrigem;
    private Long idContaDestino;
    private BigDecimal valor;
}