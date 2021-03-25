package api.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.entities.Associado;
import api.entities.Pauta;
import api.entities.ResultadoVotacao;
import api.entities.Votacao;
import api.exception.ApiExternalNaoPermitiuVotoException;
import api.exception.AssociadoInexistenteException;
import api.exception.AssociadoJaVotouPautaException;
import api.exception.PautaInexistenteException;
import api.exception.PautaNaoAbertaOuJaFechadaException;
import api.exception.ResultadoVotacaoNaoConcluidoException;
import api.exception.VotoNaoAceitoException;
import api.services.ApiPermissaoVotoService;
import api.services.AssociadoService;
import api.services.PautaService;
import api.services.VotacaoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VotacaoBusiness {

	@Autowired
	private VotacaoService votacaoService;

	@Autowired
	private AssociadoService associadoService;

	@Autowired
	private PautaService pautaService;

	@Autowired
	private ApiPermissaoVotoService apiPermissaoVotoService;

	public Votacao votar(Votacao votacao) {

		Optional<Associado> associado = this.associadoService.buscarPorId(votacao.getAssociado().getId());
		Optional<Pauta> pauta = this.pautaService.buscarPorId(votacao.getPauta().getId());

		if (!associado.isPresent()) {	
			log.error("Associado inexistente: {}", votacao.getAssociado().getId());
			throw new AssociadoInexistenteException();
		} else if (!pauta.isPresent()) {			
			log.error("Pauta inexistente: {}", votacao.getPauta().getId());
			throw new PautaInexistenteException();
		} else if (!this.pautaService.estaAbertaParaVotacao(pauta.get().getId())) {
			log.error("Pauta não aberta ou já fechada para Votação: {}", votacao.getAssociado().getId());
			throw new PautaNaoAbertaOuJaFechadaException();
		} else if (this.votacaoService.jaVotou(associado.get(), pauta.get()).isPresent()) {
			log.error("Associado {} já votou na Pauta {}", votacao.getAssociado().getId(), votacao.getPauta().getId());
			throw new AssociadoJaVotouPautaException();
		} else if (!this.apiPermissaoVotoService.associadoComPermissaoParaVotar(associado.get().getCpf())) {
			log.error("API externa não permitiu voto");
			throw new ApiExternalNaoPermitiuVotoException();
		}

		if (votacao.getVoto() == null
				|| (!votacao.getVoto().equalsIgnoreCase("sim") && !votacao.getVoto().equalsIgnoreCase("não"))) {
			log.error("Voto não aceito: {}", votacao.getVoto());
			throw new VotoNaoAceitoException();
		}

		votacao.setAssociado(associado.get());
		votacao.setPauta(pauta.get());
		return this.votacaoService.votar(votacao);
	}

	public Optional<Votacao> jaVotou(Associado associado, Pauta pauta) {
		return this.votacaoService.jaVotou(associado, pauta);
	}

	public ResultadoVotacao resultadoVotacao(String idPauta) {

		Optional<Pauta> pauta = this.pautaService.buscarPorId(idPauta);

		if (!pauta.isPresent()) {
			log.error("Pauta inexistente: {}", idPauta);
			throw new PautaInexistenteException();
		}

		if (this.pautaService.estaAbertaParaVotacao(pauta.get().getId())) {
			log.error("Pauta não aberta ou já fechada para Votação: {}", idPauta);
			throw new ResultadoVotacaoNaoConcluidoException();
		}

		return criarResultadoVotacao(pauta.get());
	}

	private ResultadoVotacao criarResultadoVotacao(Pauta pauta) {

		ResultadoVotacao resultadoVotacao = new ResultadoVotacao();
		resultadoVotacao.setVotosSim(this.votacaoService.contarVotos(pauta.getId(), "SIM"));
		resultadoVotacao.setVotosNao(this.votacaoService.contarVotos(pauta.getId(), "NÃO"));
		resultadoVotacao.setVotosTotal(resultadoVotacao.getVotosSim() + resultadoVotacao.getVotosNao());
		resultadoVotacao.setPauta(pauta);

		return resultadoVotacao;
	}

}
