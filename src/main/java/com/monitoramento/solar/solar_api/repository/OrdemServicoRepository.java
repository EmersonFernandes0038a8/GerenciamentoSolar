package com.monitoramento.solar.solar_api.repository;

import com.monitoramento.solar.solar_api.model.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {

    // Método para verificar se já existe OS Aberta para esta planta
    // Tradução: "Existe alguma OS onde PlantaID é X E SituacaoOS é Y?"
    boolean existsByPlantasIdPlantasAndSituacaoOS(Long idPlanta, Integer situacao);

}