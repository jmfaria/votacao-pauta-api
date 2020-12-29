package api.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.services.MensageriaService;
import api.services.PautaService;
import api.services.VotacaoService;

@Service
public class MonitoraPautaServiceImpl {
	
	private static final Logger log = LoggerFactory.getLogger(MonitoraPautaServiceImpl.class);

	@Autowired
	private PautaService pautaService;
	@Autowired
	private MensageriaService mensageriaService;
	@Autowired
	private VotacaoService votacaoService;

	@Scheduled(fixedDelay = 1000 * 60)
	public void agendamentoParaFinaliarVotacaoDePauta(){
		
		this.pautaService.listarPautasNaoEncerradas().forEach(pauta -> {
			
			if (!this.pautaService.estaAbertaParaVotacao(pauta.getId())) {
				
				pauta.setEncerrada(true);
				this.pautaService.finalizarPauta(pauta);
				
				try {					
					
					this.mensageriaService.publicarMensagemNaFila(
							new ObjectMapper().writeValueAsString(
									this.votacaoService.resultadoVotacao(pauta.getId())
									)
							);
					
				} catch (JsonProcessingException e) {					
					log.error(e.getMessage());
				}
			}
			
		});
	}

}
