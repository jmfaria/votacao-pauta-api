package api.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import api.entities.Pauta;

@Transactional(readOnly = true)
public interface PautaRepository extends JpaRepository<Pauta, Long> {
	
	@Query(value = "SELECT p FROM Pauta p WHERE p.id = :id AND p.validaAte > :data AND p.encerrada = FALSE", nativeQuery = false)
	Optional<Pauta> findByIdAndValidaAteAfter(@Param("id")Long id, @Param("data")LocalDateTime data);
	
	Optional<Pauta> findByNome(String nome); 
	
	@Query(value = "SELECT p FROM Pauta p WHERE p.validaAte > :data AND p.encerrada = FALSE ", nativeQuery = false)
	Page<Pauta> findByValidaAteAfter(@Param("data")LocalDateTime data, Pageable pageable);	
	
	@Query(value = "SELECT p FROM Pauta p WHERE p.validaAte IS NOT NULL AND p.encerrada = FALSE ", nativeQuery = false)
	List<Pauta> findByNotEncerrada();			
}
