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
//		Votacao votacao = new Votacao(votacaoDto, associado, pauta);
		BindingResult result = new DataBinder(null).getBindingResult();
		
//		if (votacao.getAssociado() == null) {
//			result.addError(new ObjectError("Votação de Pauta", "Associado não existe."));
//			
//		} else if (votacao.getPauta() == null) {
//			result.addError(new ObjectError("Votação de Pauta", "Pauta não existe."));
//			
//		} else 
			
		if (!this.pautaService.estaAbertaParaVotacao(pauta.get().getId()).isPresent()) {
			result.addError(new ObjectError("Votação de Pauta", "Pauta não aberta ou já encerrada para votação."));
			
		} else if (associado.isPresent() && pauta.isPresent()
				&& this.jaVotou(associado.get(), pauta.get()).isPresent()) {
			result.addError(new ObjectError("Votação de Pauta", "O Associado já votou nessa Pauta."));
			
		} else if (
				this.apiPermissaoVotoService.associadoComPermissaoParaVotar(associado.get().getCpf())
						.equalsIgnoreCase("UNABLE_TO_VOTE")) {
			result.addError(new ObjectError("Votação de Pauta", "A API externa não permitiu o Associado votar."));
		}

		if (votacao.getVoto() == null || (votacao.getVoto().isEmpty()
				&& !votacao.getVoto().equalsIgnoreCase("sim") && !votacao.getVoto().equalsIgnoreCase("não"))) {
			result.addError(new ObjectError("Votação de Pauta",
					"O voto deve ser expresso com as palavras \"SIM\" ou \"Não\"."));
		}
		
		return this.votacaoRepository.save(votacao);
	}

	@Override
	public Optional<Votacao> jaVotou(Associado Associado, Pauta Pauta) {
		return this.votacaoRepository.findByAssociadoAndPauta(Associado, Pauta);
	}
	
	@Override
	public ResultadoVotacao resultadoVotacao(Long idPauta) {
		
		Optional<Pauta> pauta = this.pautaService.buscarPorId(idPauta);		
		BindingResult result = new DataBinder(null).getBindingResult();
		
		if (!pauta.isPresent()) {
			result.addError(new ObjectError("Votação de Pauta", "Pauta não existe."));
		}

		if (this.pautaService.estaAbertaParaVotacao(pauta.get().getId()).isPresent()) {
			result.addError(new ObjectError("Votação de Pauta", "A votação para essa Pauta ainda não foi encerrada."));
		}
		
		ResultadoVotacao resultadoVotacao = new ResultadoVotacao();
		resultadoVotacao.setVotosSim(this.votacaoRepository.countByPautaAndVoto(pauta.get(), "SIM"));
		resultadoVotacao.setVotosNao(this.votacaoRepository.countByPautaAndVoto(pauta.get(), "NÃO"));
		resultadoVotacao.setVotosTotal(resultadoVotacao.getVotosSim() + resultadoVotacao.getVotosNao());
		resultadoVotacao.setPauta(pauta.get());
		
		return resultadoVotacao;
	}

}
