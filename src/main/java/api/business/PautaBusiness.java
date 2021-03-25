package api.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import api.entities.Pauta;
import api.entities.ResultadoVotacao;
import api.services.PautaService;

@Service
public class PautaBusiness {

	@Autowired
	private PautaService pautaService;
	@Autowired
	private VotacaoBusiness votacaoBusiness;
	
	
	public ResultadoVotacao resultadoVotacao(String idPauta) {
		return this.votacaoBusiness.resultadoVotacao(idPauta);
	}

	public Pauta finalizarPauta(Pauta pauta) {		
		return this.pautaService.finalizarPauta(pauta);
	}

	public boolean estaAbertaParaVotacao(String id) {
		return this.pautaService.estaAbertaParaVotacao(id);
	}

	public List<Pauta> listarPautasNaoEncerradas() {
		return this.pautaService.listarPautasNaoEncerradas();
	}

	public Page<Pauta> listarPautasAtivas(Pageable pageable) {
		return this.pautaService.listarPautasAtivas(pageable);
	}

	public Page<Pauta> listarTodas(Pageable pageRequest) {
		return this.pautaService.listarTodas(pageRequest);
	}

	public Optional<Pauta> buscarPorId(String id) {
		return this.pautaService.buscarPorId(id);
	}

	public Pauta incluir(Pauta pauta) {		

		return this.pautaService.incluir(pauta);
	}

	public Optional<Pauta> buscarPeloNome(String nome) {
		return this.pautaService.buscarPeloNome(nome);
	}

	public Pauta abrirSessaoParaVotacao(Pauta pautaRecebida) {

		return this.pautaService.abrirSessaoParaVotacao(pautaRecebida);
	}

}
