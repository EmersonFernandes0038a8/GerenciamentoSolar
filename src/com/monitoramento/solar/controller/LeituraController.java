package com.monitoramento.solar.controller;

import com.monitoramento.solar.model.LeituraDiaria;
import com.monitoramento.solar.service.MonitoramentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leituras")
public class LeituraController {

    @Autowired
    private MonitoramentoService monitoramentoService;

    @PostMapping
    public ResponseEntity<LeituraDiaria> registrarLeitura(@RequestBody LeituraDiaria leitura) {
        
        // Chama o Service que executa a lógica e salva a leitura
        LeituraDiaria leituraSalva = monitoramentoService.processarNovaLeitura(leitura);
        
        return new ResponseEntity<>(leituraSalva, HttpStatus.CREATED);
    }
    
    // Exemplo de endpoint GET (Consulta a VIEW)
    // OBS: Para consultar a VIEW, você precisaria de uma entidade mapeada para a VIEW.
    /*
    @GetMapping("/monitoramento")
    public List<PainelMonitoramento> getPainel() {
        // ... Lógica para consultar o PainelMonitoramentoView
    }
    */
}