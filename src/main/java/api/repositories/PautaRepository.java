package api.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import api.entities.Pauta;

@Transactional(readOnly = true)
public interface PautaRepository extends MongoRepository<Pauta, String> {
	
//	@Query(value = "SELECT p FROM Pauta p WHERE p.id = :id AND p.validaAte > :data AND p.encerrada = FALSE")
//	Optional<Pauta> findByIdAndValidaAteAfter(@Param("id")String id, @Param("data")LocalDateTime data);
	
	@Query("{ 'id':?0, 'validaAte':{$gt: ?1}, 'encerrada':false}")
	Optional<Pauta> findByIdAndValidaAteAfter(@Param("id")String id, @Param("data")LocalDateTime data);
	
	Optional<Pauta> findByNome(String nome); 
	
//	@Query(value = "SELECT p FROM Pauta p WHERE (p.validaAte > :data AND p.encerrada = FALSE) or p.validaAte IS NULL ")
//	Page<Pauta> findByValidaAteAfter(@Param("data")LocalDateTime data, Pageable pageable);
	
	@Query("{'validaAte':{$gt: ?0}, 'encerrada':false }")
	Page<Pauta> findByValidaAteAfter(@Param("data")LocalDateTime data, Pageable pageable);	
	
//	@Query(value = "SELECT p FROM Pauta p WHERE p.validaAte IS NOT NULL AND p.encerrada = FALSE ")
//	List<Pauta> findByNotEncerrada();
	
	@Query("{ 'validaAte': {$ne:null}, 'encerrada':false }")
	List<Pauta> findByNotEncerrada();			
}

