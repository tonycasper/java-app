package com.br.agbank.controller;

import com.br.agbank.dto.TransferenciaDTO;
import com.br.agbank.service.TransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
@AllArgsConstructor
public class TransacoesController {

    private final TransacaoService transacaoService;

    @PostMapping("/transferencia")
    public ResponseEntity<?> realizarTransferencia(@RequestBody TransferenciaDTO transferenciaDTO) {
        transacaoService.realizarTransferencia(transferenciaDTO.getIdContaOrigem(),
                transferenciaDTO.getIdContaDestino(),
                transferenciaDTO.getValor());
        return ResponseEntity.ok().build();
    }
}
