package api.services;

import java.util.Optional;

import api.entities.Associado;
import api.entities.Pauta;
import api.entities.ResultadoVotacao;
import api.entities.Votacao;

public interface VotacaoService {
	
	Votacao votar(Votacao votacao);
	
	Optional<Votacao> jaVotou(Associado associado, Pauta pauta);
	
	ResultadoVotacao resultadoVotacao(Long idPauta);

}
