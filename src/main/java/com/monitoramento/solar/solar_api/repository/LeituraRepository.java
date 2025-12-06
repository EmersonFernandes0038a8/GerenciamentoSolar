package com.monitoramento.solar.solar_api.repository;

import com.monitoramento.solar.solar_api.model.LeituraDiaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeituraRepository extends JpaRepository<LeituraDiaria, Long> {

    // A MÁGICA DO SPRING DATA:
    // Apenas declarando este nome de método, ele gera o SQL:
    // "SELECT * FROM leiturasdiarias WHERE Plantas_idPlantas = ? AND dataLeitura >= ?"
    
    List<LeituraDiaria> findByPlantasIdPlantasAndDataLeituraGreaterThanEqual(Long idPlanta, LocalDate dataInicial);

}