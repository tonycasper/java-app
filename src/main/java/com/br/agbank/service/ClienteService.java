package com.br.agbank.service;

import com.br.agbank.model.Cliente;
import com.br.agbank.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente salvarCliente(Cliente cliente){
        Optional<Cliente> documento = clienteRepository.findByDocumento(cliente.getCpfCnpj());

        if (documento.isEmpty()){
            return clienteRepository.save(cliente);
        } else {
            throw new IllegalArgumentException("CPF ou CNPJ já cadastrado no Sistema.");
        }
    }
}
