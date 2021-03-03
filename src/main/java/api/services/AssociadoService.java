package api.services;

import java.util.List;
import java.util.Optional;

import api.entities.Associado;

public interface AssociadoService {
	
	List<Associado> listar();
	
	Optional<Associado> buscarPorId(String id);
	
	Associado incluir(Associado associado);
	
	Optional<Associado> buscarPorCpf(String cpf);

}
