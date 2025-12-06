package com.monitoramento.solar.solar_api.controller;

import com.monitoramento.solar.solar_api.model.LeituraDiaria;
import com.monitoramento.solar.solar_api.service.MonitoramentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Define a classe como um Controller REST
@RequestMapping("/api/leituras") // Define o prefixo do URL
public class LeituraController {

    @Autowired // Injeta o Service para usar a lógica de negócio
    private MonitoramentoService monitoramentoService;

    /**
     * Endpoint POST para registrar uma nova leitura diária.
     * Recebe um objeto JSON e o transforma na classe LeituraDiaria.
     * URL: POST http://localhost:8080/api/leituras
     */
    @PostMapping
    public ResponseEntity<LeituraDiaria> registrarLeitura(@RequestBody LeituraDiaria leitura) {
        
        // Chama o Service que executa a lógica e salva a leitura
        LeituraDiaria leituraSalva = monitoramentoService.processarNovaLeitura(leitura);
        
        // Retorna a leitura salva e o status HTTP 201 Created
        return new ResponseEntity<>(leituraSalva, HttpStatus.CREATED);
    }
    
    // Você pode adicionar outros endpoints GET, PUT, DELETE aqui.
}