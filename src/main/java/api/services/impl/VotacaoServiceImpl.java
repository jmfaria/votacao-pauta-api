package api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.entities.Associado;
import api.entities.Pauta;
import api.entities.ResultadoVotacao;
import api.entities.Votacao;
import api.repositories.VotacaoRepository;
import api.services.VotacaoService;

@Service
public class VotacaoServiceImpl implements VotacaoService {

	private static Logger log = LoggerFactory.getLogger(VotacaoServiceImpl.class);
	
	@Autowired
	private VotacaoRepository votacaoRepository;
	
	@Override
	public Votacao votar(Votacao votacao) {
		return this.votacaoRepository.save(votacao);
	}
	
	@Override
	public Votacao persistir(Votacao votacao) {
		log.info("Persistindo votação: {}", votacao);
		return this.votacaoRepository.save(votacao);
	}

	@Override
	public Optional<Votacao> jaVotou(Associado Associado, Pauta Pauta) {
		return this.votacaoRepository.findByAssociadoAndPauta(Associado, Pauta);
	}
	
	@Override
	public ResultadoVotacao resultadoVotacao(Pauta pauta) {
		
		ResultadoVotacao resultadoVotacao = new ResultadoVotacao();
		resultadoVotacao.setVotosSim(this.votacaoRepository.countByPautaAndVoto(pauta, "SIM"));
		resultadoVotacao.setVotosNao(this.votacaoRepository.countByPautaAndVoto(pauta, "NÃO"));
		resultadoVotacao.setVotosTotal(resultadoVotacao.getVotosSim() + resultadoVotacao.getVotosNao());
		resultadoVotacao.setPauta(pauta);
		
		return resultadoVotacao;
	}

}
