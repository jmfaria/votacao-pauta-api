package api.repositories;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import api.entities.Pauta;

@Transactional(readOnly = true)
public interface PautaRepository extends JpaRepository<Pauta, Long> {
	
	@Query(value = "SELECT p FROM Pauta p WHERE p.id = :id AND p.validaAte > :data AND p.encerrada = FALSE", nativeQuery = false)
	Optional<Pauta> findByIdAndValidaAteAfter(@Param("id")Long id, @Param("data") Calendar data);
	
	Optional<Pauta> findByNome(String nome); 
	
	@Query(value = "SELECT p FROM Pauta p WHERE p.validaAte > :data AND p.encerrada = FALSE ", nativeQuery = false)
	List<Pauta> findByValidaAteAfter(@Param("data")Calendar data);	
	
	@Query(value = "SELECT p FROM Pauta p WHERE p.validaAte IS NOT NULL AND p.encerrada = FALSE ", nativeQuery = false)
	List<Pauta> findByNotEncerrada();
			
}
