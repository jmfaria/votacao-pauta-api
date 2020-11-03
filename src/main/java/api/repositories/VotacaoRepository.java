package api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api.entities.Associado;
import api.entities.Pauta;
import api.entities.Votacao;

public interface VotacaoRepository extends JpaRepository<Votacao, Long> {
	
	Optional<Votacao> findByAssociadoAndPauta(Associado associado, Pauta pauta);
	
	@Query(value = "SELECT COUNT(v) FROM Votacao v WHERE v.pauta = :pauta AND v.voto = :voto", nativeQuery = false)
	Long countByPautaAndVoto(@Param("pauta") Pauta pauta, @Param("voto") String voto);	

}
