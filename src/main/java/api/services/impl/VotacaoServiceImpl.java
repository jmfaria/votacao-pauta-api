package api.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;

import api.entities.Associado;
import api.entities.Pauta;
import api.entities.ResultadoVotacao;
import api.entities.Votacao;
import api.repositories.VotacaoRepository;
import api.services.ApiPermissaoVotoService;
import api.services.AssociadoService;
import api.services.PautaService;
import api.services.VotacaoService;
import api.services.impl.exceptions.ApiExternalNaoPermitiuVotoException;
import api.services.impl.exceptions.AssociadoJaVotouPautaException;
import api.services.impl.exceptions.PautaInexistenteException;
import api.services.impl.exceptions.PautaJaEncerradaException;
import api.services.impl.exceptions.PautaNaoAbertaOuJaFechadaException;
import api.services.impl.exceptions.ResultadoVotacaoNaoConcluidoException;
import api.services.impl.exceptions.VotoNaoAceitoException;

@Service
public class VotacaoServiceImpl implements VotacaoService {

	@Autowired
	private VotacaoRepository votacaoRepository;

	@Autowired
	private AssociadoService associadoService;

	@Autowired
	private PautaService pautaService;

	@Autowired
	private ApiPermissaoVotoService apiPermissaoVotoService;

	@Override
	public Votacao votar(Votacao votacao) {

		Optional<Associado> associado = this.associadoService.buscarPorId(votacao.getAssociado().getId());
		Optional<Pauta> pauta = this.pautaService.buscarPorId(votacao.getPauta().getId());

		if (pauta.isPresent() && !this.pautaService.estaAbertaParaVotacao(pauta.get().getId())) {

			throw new PautaNaoAbertaOuJaFechadaException();

		} else if (associado.isPresent() && pauta.isPresent()
				&& this.jaVotou(associado.get(), pauta.get()).isPresent()) {

			throw new AssociadoJaVotouPautaException();

		} else if (associado.isPresent() && this.apiPermissaoVotoService
				.associadoComPermissaoParaVotar(associado.get().getCpf()).equalsIgnoreCase("UNABLE_TO_VOTE")) {

			throw new ApiExternalNaoPermitiuVotoException();

		}

		if (votacao.getVoto() == null
				|| (!votacao.getVoto().equalsIgnoreCase("sim") && !votacao.getVoto().equalsIgnoreCase("não"))) {

			throw new VotoNaoAceitoException();
		}

		votacao.setAssociado(associado.get());
		votacao.setPauta(pauta.get());
		return this.votacaoRepository.save(votacao);
	}

	@Override
	public Optional<Votacao> jaVotou(Associado associado, Pauta pauta) {
		return this.votacaoRepository.findByAssociadoAndPauta(associado, pauta);
	}

	@Override
	public ResultadoVotacao resultadoVotacao(String idPauta) {

		Optional<Pauta> pauta = this.pautaService.buscarPorId(idPauta);

		if (!pauta.isPresent()) {
			throw new PautaInexistenteException();
		}

		if (this.pautaService.estaAbertaParaVotacao(pauta.get().getId())) {
			throw new ResultadoVotacaoNaoConcluidoException();
		}

		ResultadoVotacao resultadoVotacao = new ResultadoVotacao();
		resultadoVotacao.setVotosSim(this.votacaoRepository.countByPautaAndVoto(pauta.get().getId(), "SIM"));
		resultadoVotacao.setVotosNao(this.votacaoRepository.countByPautaAndVoto(pauta.get().getId(), "NÃO"));
		resultadoVotacao.setVotosTotal(resultadoVotacao.getVotosSim() + resultadoVotacao.getVotosNao());
		resultadoVotacao.setPauta(pauta.get());

		return resultadoVotacao;
	}

}
