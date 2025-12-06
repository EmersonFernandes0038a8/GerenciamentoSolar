package com.monitoramento.solar.solar_api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "leiturasdiarias") // Nome exato da tabela no banco (tudo minúsculo)
public class LeituraDiaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLeiturasDiarias; // PK

    // FK - Por enquanto vamos usar apenas o ID (Long) para facilitar
    // Mais tarde podemos mapear o objeto "Planta" completo
    @Column(name = "Plantas_idPlantas")
    private Long plantasIdPlantas; 

    private LocalDate dataLeitura;
    
    private Double geracaoKWh;
    
    private Double consumoKWh;

    // Aquele ajuste para ler a coluna com maiúsculas no banco
    @Column(name = "\"geracaoEsperadaKWh\"") 
    private Double geracaoEsperadaKWh; 

    // --- GETTERS E SETTERS (Obrigatórios) ---
    // Você pode gerá-los automaticamente na sua IDE 
    // (Botão direito -> Source -> Generate Getters and Setters)
    
    public Long getIdLeiturasDiarias() { return idLeiturasDiarias; }
    public void setIdLeiturasDiarias(Long id) { this.idLeiturasDiarias = id; }

    public Long getPlantasIdPlantas() { return plantasIdPlantas; }
    public void setPlantasIdPlantas(Long id) { this.plantasIdPlantas = id; }

    public LocalDate getDataLeitura() { return dataLeitura; }
    public void setDataLeitura(LocalDate data) { this.dataLeitura = data; }

    public Double getGeracaoKWh() { return geracaoKWh; }
    public void setGeracaoKWh(Double g) { this.geracaoKWh = g; }

    public Double getConsumoKWh() { return consumoKWh; }
    public void setConsumoKWh(Double c) { this.consumoKWh = c; }

    public Double getGeracaoEsperadaKWh() { return geracaoEsperadaKWh; }
    public void setGeracaoEsperadaKWh(Double g) { this.geracaoEsperadaKWh = g; }
}