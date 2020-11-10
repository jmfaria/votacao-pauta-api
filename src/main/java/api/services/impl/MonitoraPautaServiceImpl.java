package api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.entities.Pauta;
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
			
			Optional<Pauta> p = this.pautaService.estaAbertaParaVotacao(pauta.getId());
			if (!p.isPresent()) {
				
				pauta.setEncerrada(true);
				this.pautaService.finalizarPauta(pauta);
				try {
					this.mensageriaService.publicarMensagemNaFila(
							//this.votacaoService.resultadoVotacao(pauta).toString()
							new ObjectMapper().writeValueAsString(
									this.votacaoService.resultadoVotacao(pauta)
									)
							);
				} catch (JsonProcessingException e) {					
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}
			
		});
	}

}
