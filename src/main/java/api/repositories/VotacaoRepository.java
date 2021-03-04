package api.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import api.entities.Associado;
import api.entities.Pauta;
import api.entities.Votacao;

@Transactional(readOnly = true)
public interface VotacaoRepository extends MongoRepository<Votacao, String> {
	
	Optional<Votacao> findByAssociadoAndPauta(Associado associado, Pauta pauta);
	
	@Query(value="{ 'pauta.id': ?0, 'voto': ?1 }", count = true)
	Long countByPautaAndVoto(@Param("pauta") String pautaId, @Param("voto") String voto);	
}
