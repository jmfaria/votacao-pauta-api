package api.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import api.entities.Pauta;
import api.services.MensageriaService;
import api.services.PautaService;
import api.services.VotacaoService;

@Service
public class MonitoraPautaServiceImpl {

	@Autowired
	private PautaService pautaService;
	@Autowired
	private MensageriaService mensageriaService;
	@Autowired
	private VotacaoService votacaoService;

	@Scheduled(fixedDelay = 1000 * 60)
	public void agendamentoParaFinaliarVotacaoDePauta() {
		
		this.pautaService.listarPautasNaoEncerradas().forEach(pauta -> {
			
			Optional<Pauta> p = this.pautaService.estaAbertaParaVotacao(pauta.getId());
			if (!p.isPresent()) {
				
				pauta.setEncerrada(true);
				this.pautaService.finalizarPauta(pauta);
				this.mensageriaService.publicarMensagemNaFila(this.votacaoService.resultadoVotacao(pauta).toString());
			}
			
		});
	}

}
