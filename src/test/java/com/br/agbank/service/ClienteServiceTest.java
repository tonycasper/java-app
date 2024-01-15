package com.br.agbank.service;

import com.br.agbank.model.Cliente;
import com.br.agbank.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @Test
    public void quandoSalvarClienteNovo_entaoRetornarClienteSalvo() {
        given(clienteRepository.findByDocumento(cliente.getCpfCnpj())).willReturn(Optional.empty());
        given(clienteRepository.save(cliente)).willReturn(cliente);

        Cliente clienteSalvo = clienteService.salvarCliente(cliente);

        assertNotNull(clienteSalvo);
    }

    @Test
    public void quandoSalvarClienteComDocumentoExistente_entaoLancarExcecao() {
        Cliente cliente = new Cliente();
        given(clienteRepository.findByDocumento(cliente.getCpfCnpj())).willReturn(Optional.of(cliente));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.salvarCliente(cliente);
        });

        String expectedMessage = "CPF ou CNPJ já cadastrado no Sistema.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
