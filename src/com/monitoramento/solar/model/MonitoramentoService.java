package com.monitoramento.solar.service;

import com.monitoramento.solar.model.LeituraDiaria;
import com.monitoramento.solar.repository.LeituraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class MonitoramentoService {

    @Autowired
    private LeituraRepository leituraRepository;
    
    // OBS: Você precisará de um Service e Repository para OrdensServico também!
    @Autowired 
    private OrdemServicoService ordemServicoService;

    private static final int DIAS_ANALISE = 7;
    private static final double PERCENTUAL_MINIMO = 0.85;
    private static final int DIAS_RUINS_NECESSARIOS = 3;

    /**
     * Reimplementa a lógica do seu TRIGGER: Inserir e verificar a necessidade de OS.
     * * @param novaLeitura O objeto LeituraDiaria recebido pela API.
     * @return A leitura salva.
     */
    public LeituraDiaria processarNovaLeitura(LeituraDiaria novaLeitura) {
        
        // 1. Salva a nova leitura no banco (equivalente ao INSERT)
        LeituraDiaria leituraSalva = leituraRepository.save(novaLeitura);

        // 2. Chama a verificação
        if (verificarNecessidadeManutencao(leituraSalva.getPlantasIdPlantas())) {
            
            // 3. Checa se já existe OS aberta (equivalente à checagem do TRIGGER)
            if (!ordemServicoService.existeOSAberta(leituraSalva.getPlantasIdPlantas())) {
                
                // 4. Cria a Ordem de Serviço (equivalente ao INSERT INTO OrdensServico)
                ordemServicoService.criarOrdemCorretiva(
                    leituraSalva.getPlantasIdPlantas(), 
                    "Baixa geração detectada automaticamente. " + 
                    DIAS_RUINS_NECESSARIOS + " ou mais dias abaixo de " + PERCENTUAL_MINIMO * 100 + "%."
                );
            }
        }
        
        return leituraSalva;
    }

    /**
     * Reimplementa a lógica da sua FUNCTION fn_VerificarNecessidadeManutencao.
     * * @param idPlanta ID da planta a ser verificada.
     * @return True se precisar de manutenção, False caso contrário.
     */
    public boolean verificarNecessidadeManutencao(Long idPlanta) {
        LocalDate dataInicial = LocalDate.now().minusDays(DIAS_ANALISE);
        
        // Busca as leituras dos últimos 7 dias
        List<LeituraDiaria> leiturasRecentes = leituraRepository.findByPlantasIdAndDateRange(idPlanta, dataInicial);

        long diasRuins = leiturasRecentes.stream()
            .filter(leitura -> leitura.calcularEficiencia() < PERCENTUAL_MINIMO)
            .count();
        
        return diasRuins >= DIAS_RUINS_NECESSARIOS;
    }
}