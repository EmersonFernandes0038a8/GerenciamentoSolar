package com.monitoramento.solar.repository;

import com.monitoramento.solar.model.LeituraDiaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface LeituraRepository extends JpaRepository<LeituraDiaria, Long> {

    // Método para buscar as leituras de uma planta nos últimos N dias
    @Query(value = "SELECT ld FROM LeituraDiaria ld WHERE ld.plantasIdPlantas = :idPlanta AND ld.dataLeitura >= :dataInicial")
    List<LeituraDiaria> findByPlantasIdAndDateRange(Long idPlanta, LocalDate dataInicial);

    // Método para verificar se já existe uma Ordem de Serviço (OS) aberta (assumindo que OrdensServico é outra entidade)
    // OBS: Você precisará criar uma entidade para OrdensServico e um método real para checagem.
    // Este é um placeholder.
    boolean existsOpenOrdemServicoForPlanta(Long idPlanta);
}