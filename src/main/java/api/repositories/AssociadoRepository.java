package api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import api.entities.Associado;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {

	Optional<Associado> findByCpf(String cpf);
}
