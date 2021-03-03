package api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import api.entities.Associado;
import api.repositories.AssociadoRepository;
import api.services.AssociadoService;
import api.services.impl.exceptions.AssociadoCpfJaCadastradoException;
import api.services.impl.exceptions.CpfInvalidoException;
import api.utils.CpfUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AssociadoServiceImpl implements AssociadoService {
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	@Override
	public List<Associado> listar(){
		
		return this.associadoRepository.findAll();
		
	}
	
	@Override
	public Optional<Associado> buscarPorId(String id) {
		
		return this.associadoRepository.findById(id);
		
	}

	@Override
	public Associado incluir(Associado associado) {
		
		if (associado.getCpf() != null && !CpfUtils.validarCPF(associado.getCpf())) {
			
			throw new CpfInvalidoException();
			
		} else if (this.buscarPorCpf(associado.getCpf()).isPresent()) {
			
			throw new AssociadoCpfJaCadastradoException();
		}
		
		return this.associadoRepository.save(associado);		
	}

	@Cacheable("cacheAssociadoPorCPF")
	@Override
	public Optional<Associado> buscarPorCpf(String cpf) {
		
		log.info("Validando a funcionalidade de Cache");
		return this.associadoRepository.findByCpf(cpf);
		
	}
}
