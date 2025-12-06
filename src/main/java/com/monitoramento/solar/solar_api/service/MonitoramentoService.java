package com.monitoramento.solar.solar_api.service;

import com.monitoramento.solar.solar_api.model.LeituraDiaria;
import com.monitoramento.solar.solar_api.model.OrdemServico;
import com.monitoramento.solar.solar_api.repository.LeituraRepository;
import com.monitoramento.solar.solar_api.repository.OrdemServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MonitoramentoService {

    @Autowired
    private LeituraRepository leituraRepository;
    
    @Autowired 
    private OrdemServicoRepository ordemServicoRepository;

    // Constantes (Regras do Negócio)
    private static final int DIAS_ANALISE = 7;
    private static final double PERCENTUAL_MINIMO = 0.85; // 85% de eficiência
    private static final int DIAS_RUINS_NECESSARIOS = 3; 
    private static final int STATUS_OS_ABERTA = 1; // 1 = Aberta (De acordo com seu DER)
    private static final int TIPO_OS_CORRETIVA = 2; // 2 = Corretiva

    /**
     * Ponto de entrada da API. Processa a nova leitura, salva e dispara a verificação.
     * A anotação @Transactional garante que a lógica é atômica.
     */
    @Transactional
    public LeituraDiaria processarNovaLeitura(LeituraDiaria novaLeitura) {
        
        // 1. Salva a nova leitura no banco de dados
        LeituraDiaria leituraSalva = leituraRepository.save(novaLeitura);
        
        Long idPlanta = leituraSalva.getPlantasIdPlantas();

        // 2. Chama a lógica de verificação
        if (verificarNecessidadeManutencao(idPlanta)) {
            
            // 3. Checa se já existe uma OS aberta (evita duplicidade do TRIGGER)
            if (!ordemServicoRepository.existsByPlantasIdPlantasAndSituacaoOS(idPlanta, STATUS_OS_ABERTA)) {
                
                // 4. Cria e salva a nova Ordem de Serviço Corretiva
                criarOrdemCorretiva(idPlanta);
            }
        }
        
        return leituraSalva;
    }

    /**
     * Reimplementa a lógica da sua FUNCTION fn_VerificarNecessidadeManutencao.
     * Busca os dados e conta quantos dias estão abaixo do limite.
     */
    private boolean verificarNecessidadeManutencao(Long idPlanta) {
        LocalDate dataInicial = LocalDate.now().minusDays(DIAS_ANALISE);
        
        // Busca as leituras dos últimos 7 dias usando o método customizado do Repository
        List<LeituraDiaria> leiturasRecentes = leituraRepository
            .findByPlantasIdPlantasAndDataLeituraGreaterThanEqual(idPlanta, dataInicial);

        // Calcula a eficiência para cada dia e conta quantos estão abaixo de 85%
        long diasRuins = leiturasRecentes.stream()
            .filter(this::estaAbaixoDaEficiencia)
            .count();
        
        return diasRuins >= DIAS_RUINS_NECESSARIOS;
    }
    
    /**
     * Lógica auxiliar para calcular a eficiência de uma leitura.
     */
    private boolean estaAbaixoDaEficiencia(LeituraDiaria leitura) {
        Double geracaoEsperada = leitura.getGeracaoEsperadaKWh();
        Double geracaoReal = leitura.getGeracaoKWh();
        
        if (geracaoEsperada == null || geracaoEsperada <= 0) {
            return false; // Não há dados válidos para comparar
        }
        
        double eficiencia = geracaoReal / geracaoEsperada;
        return eficiencia < PERCENTUAL_MINIMO;
    }

    /**
     * Cria e persiste a Ordem de Serviço no banco de dados.
     */
    private void criarOrdemCorretiva(Long idPlanta) {
        String descricao = String.format(
            "Manutenção Corretiva automática. Sistema detectou %d dias com eficiência abaixo de %.0f%% nos últimos 7 dias.",
            DIAS_RUINS_NECESSARIOS, PERCENTUAL_MINIMO * 100
        );

        OrdemServico novaOs = new OrdemServico(
            idPlanta,
            LocalDate.now(),
            TIPO_OS_CORRETIVA,
            STATUS_OS_ABERTA,
            descricao
        );
        
        ordemServicoRepository.save(novaOs);
    }
}