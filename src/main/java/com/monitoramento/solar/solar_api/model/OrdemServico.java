package com.monitoramento.solar.solar_api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ordensservico") // Nome tudo minúsculo conforme o banco
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrdensServico;

    @Column(name = "Plantas_idPlantas")
    private Long plantasIdPlantas;

    private LocalDate dataAbertura;
    private LocalDate dataFechamento;

    // Lembre-se dos seus "Magic Numbers": 
    // 1=Aberta, 2=Corretiva, etc.
    // O ideal seria usar ENUMs, mas vamos manter Integer como no seu banco atual.
    private Integer tipoServico; 
    private Integer situacaoOS; 

    @Column(columnDefinition = "TEXT")
    private String descricaoProblema;

    @Column(columnDefinition = "TEXT")
    private String descricaoSolucao;

    // --- CONSTRUTOR VAZIO (Obrigatório pro JPA) ---
    public OrdemServico() {}

    // --- CONSTRUTOR ÚTIL (Para criarmos rápido no código) ---
    public OrdemServico(Long idPlanta, LocalDate dataAbertura, Integer tipo, Integer situacao, String problema) {
        this.plantasIdPlantas = idPlanta;
        this.dataAbertura = dataAbertura;
        this.tipoServico = tipo;
        this.situacaoOS = situacao;
        this.descricaoProblema = problema;
    }

    // --- GETTERS E SETTERS ---
    // (Gere automaticamente na IDE: Source -> Generate Getters and Setters)
    public Long getIdOrdensServico() { return idOrdensServico; }
    public void setIdOrdensServico(Long id) { this.idOrdensServico = id; }
    
    public Long getPlantasIdPlantas() { return plantasIdPlantas; }
    public void setPlantasIdPlantas(Long id) { this.plantasIdPlantas = id; }

    public LocalDate getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDate d) { this.dataAbertura = d; }
    
    // ... gere os outros getters/setters se precisar ...
}