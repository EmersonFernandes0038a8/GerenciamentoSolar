package com.monitoramento.solar.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "leiturasdiarias") // Garante o nome da tabela em minúsculo
public class LeituraDiaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLeiturasDiarias;

    private Long plantasIdPlantas; // FK para a Planta

    private LocalDate dataLeitura;
    private Double geracaoKWh;
    private Double consumoKWh;

    // Usando aspas duplas para mapear a coluna case-sensitive do BD
    @Column(name = "\"geracaoEsperadaKWh\"") 
    private Double geracaoEsperadaKWh; 

    // Getters e Setters (omitidos para brevidade, mas necessários)
    // Construtores (omitidos para brevidade)
    
    // Método auxiliar para cálculo de eficiência
    public Double calcularEficiencia() {
        if (geracaoEsperadaKWh == null || geracaoEsperadaKWh == 0) {
            return 0.0;
        }
        return geracaoKWh / geracaoEsperadaKWh;
    }
}
