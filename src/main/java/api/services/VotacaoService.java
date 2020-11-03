package api.services;

import java.util.Optional;

import api.entities.Associado;
import api.entities.Pauta;
import api.entities.ResultadoVotacao;
import api.entities.Votacao;

public interface VotacaoService {
	
	Votacao votar(Votacao votacao);
	
	Votacao persistir(Votacao votacao);
	
	Optional<Votacao> jaVotou(Associado Associado, Pauta Pauta);
	
	ResultadoVotacao resultadoVotacao(Pauta pauta);

}
