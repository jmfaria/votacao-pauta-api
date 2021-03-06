package api.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.services.MensageriaService;
import api.services.PautaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MonitoraPautaBusiness {
	
	@Autowired
	private PautaService pautaService;
	@Autowired
	private MensageriaService mensageriaService;
	@Autowired
	private VotacaoBusiness votacaoBusiness;

	@Scheduled(cron = "${cron.monitoramento.pauta}")
	public void agendamentoParaFinaliarVotacaoDePauta(){
		
		this.pautaService.listarPautasNaoEncerradas().forEach(pauta -> {
			if (!this.pautaService.estaAbertaParaVotacao(pauta.getId())) {
				pauta.setEncerrada(true);
				this.pautaService.finalizarPauta(pauta);				
				try {					
					
					this.mensageriaService.publicarMensagemNaFila(
							new ObjectMapper().writeValueAsString(
									this.votacaoBusiness.resultadoVotacao(pauta.getId())
									)
							);
					
				} catch (JsonProcessingException e) {					
					log.error(e.getMessage());
				}
			}
			
		});
	}

}
