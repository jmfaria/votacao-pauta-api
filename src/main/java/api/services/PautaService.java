package api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import api.entities.Pauta;

public interface PautaService {
	
	boolean estaAbertaParaVotacao(String id);
		
	Page<Pauta> listarPautasAtivas(Pageable pageable);
	
	Optional<Pauta> buscarPorId(String id);
	
	Pauta incluir(Pauta pauta);
	
	Optional<Pauta> buscarPeloNome(String nome);
	
	Pauta abrirSessaoParaVotacao(Pauta pauta);
	
	Pauta finalizarPauta(Pauta pauta);

	List<Pauta> listarPautasNaoEncerradas();
	
	Page<Pauta> listarTodas(Pageable pageRequest);
}
