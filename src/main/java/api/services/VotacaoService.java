package api.services;

import java.util.Optional;

import org.springframework.validation.BindingResult;

import api.dtos.VotacaoDto;
import api.entities.Associado;
import api.entities.Pauta;
import api.entities.ResultadoVotacao;
import api.entities.Votacao;

public interface VotacaoService {
	
	Votacao votar(VotacaoDto votacaoDto, BindingResult result);
	
	Optional<Votacao> jaVotou(Associado Associado, Pauta Pauta);
	
	ResultadoVotacao resultadoVotacao(Pauta pauta);

}
