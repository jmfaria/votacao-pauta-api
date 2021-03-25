package api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.entities.Associado;
import api.exception.AssociadoCpfJaCadastradoException;
import api.exception.AssociadoInexistenteException;
import api.exception.CpfInvalidoException;
import api.repositories.AssociadoRepository;
import api.utils.CpfUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AssociadoService {
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	public List<Associado> listar(){
		
		return this.associadoRepository.findAll();
		
	}
	
	public Optional<Associado> buscarPorId(String id) {
		
		return this.associadoRepository.findById(id);
		
	}

	public Associado incluir(Associado associado) {
		
		if (associado.getCpf() == null || !CpfUtils.validarCPF(associado.getCpf())) {
			
			throw new CpfInvalidoException();
			
		} else if (this.associadoRepository.findByCpf(associado.getCpf()).isPresent()) {
			
			log.error("Associado já Cadastrado: {}", associado.getCpf() );
			throw new AssociadoCpfJaCadastradoException();
		}
		
		return this.associadoRepository.save(associado);		
	}

	public Optional<Associado> buscarPorCpf(String cpf) {
		
		Optional<Associado> associado = this.associadoRepository.findByCpf(cpf);
		if(!associado.isPresent()) {
			log.error("Associado inexistente: {}", cpf );
			throw new AssociadoInexistenteException();
		}
		
		return associado;		
	}
}
