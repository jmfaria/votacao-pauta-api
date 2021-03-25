package api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.entities.Associado;
import api.entities.Pauta;
import api.entities.Votacao;
import api.exception.VotoNaoAceitoException;
import api.repositories.VotacaoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VotacaoService {

	@Autowired
	private VotacaoRepository votacaoRepository;

	public Votacao votar(Votacao votacao) {		
		return this.votacaoRepository.save(votacao);
	}

	public Optional<Votacao> jaVotou(Associado associado, Pauta pauta) {
		return this.votacaoRepository.findByAssociadoAndPauta(associado, pauta);
	}
	
	public Long contarVotos(String pautaId, String voto) {
		if (voto == null
				|| (!voto.equalsIgnoreCase("sim") && !voto.equalsIgnoreCase("não"))) {
			log.error("Voto não aceito: {}", voto);
			throw new VotoNaoAceitoException();
		}		
		return this.votacaoRepository.countByPautaAndVoto(pautaId, "SIM");				
	}	

}
