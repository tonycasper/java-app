package com.br.agbank.controller;

import com.br.agbank.dto.TransferenciaDTO;
import com.br.agbank.service.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransacoesController.class)
public class TransacoesControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransacaoService transacaoService;

    @Test
    public void quandoRealizarTransferencia_entaoRetornarStatusOk() throws Exception {
        TransferenciaDTO transferenciaDTO = new TransferenciaDTO(1L, 2L, new BigDecimal("100.00"));

        doNothing().when(transacaoService).realizarTransferencia(1L, 2L, new BigDecimal("100.00"));

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/transferencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferenciaDTO)))
                .andExpect(status().isOk());
    }
}
