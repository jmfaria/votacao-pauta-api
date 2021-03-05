package api.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import api.entities.Pauta;

@Transactional(readOnly = true)
public interface PautaRepository extends MongoRepository<Pauta, String> {
		
	Optional<Pauta> findAllByIdAndValidaAteAfterAndEncerradaFalse(String id, LocalDateTime data);
	Optional<Pauta> findByNome(String nome); 
	Page<Pauta> findAllByValidaAteAfterAndEncerradaFalse(LocalDateTime data, Pageable pageable);	
	List<Pauta> findAllByValidaAteNotNullAndEncerradaFalse();			
}

