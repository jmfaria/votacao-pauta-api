package api.services;

import java.util.List;
import java.util.Optional;

import api.entities.Pauta;

public interface PautaService {
	
	Optional<Pauta> estaAbertaParaVotacao(Long id);
		
	List<Pauta> listarPautasAtivas();
	
	Optional<Pauta> buscarPorId(Long id);
	
	Pauta persistir(Pauta pauta);
	
	Optional<Pauta> buscarPeloNome(String nome);
	
	Pauta abrirSessaoParaVotacao(Pauta pauta);
	
	Pauta finalizarPauta(Pauta pauta);

	List<Pauta> listarPautasNaoEncerradas();
}