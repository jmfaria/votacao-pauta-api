package api.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import api.entities.Associado;

@Transactional(readOnly = true)
public interface AssociadoRepository extends MongoRepository<Associado, String> {

	Optional<Associado> findByCpf(String cpf);
}
