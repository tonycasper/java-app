package com.br.agbank.controller;

import com.br.agbank.model.Cliente;
import com.br.agbank.model.Conta;
import com.br.agbank.service.ClienteService;
import com.br.agbank.service.ContaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contas")
@AllArgsConstructor
public class ContaController {

    private final ContaService contaService;

    @PostMapping
    public ResponseEntity<Conta> cadastrarConta(@RequestBody Conta conta) {
        Conta clienteSalvo = contaService.salvarConta(conta);
        return ResponseEntity.status(201).body(clienteSalvo);
    }
}