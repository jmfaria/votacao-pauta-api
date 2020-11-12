package api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import api.entities.Associado;
import api.repositories.AssociadoRepository;
import api.services.AssociadoService;

@Service
public class AssociadoServiceImpl implements AssociadoService {
	
	private static Logger log = LoggerFactory.getLogger(AssociadoServiceImpl.class);
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	@Override
	public List<Associado> listar(){
		
		return this.associadoRepository.findAll();
		
	}
	
	@Override
	public Optional<Associado> buscarPorId(Long id) {
		
		return this.associadoRepository.findById(id);
		
	}

	@Override
	public Associado incluir(Associado associado) {
		
		return this.associadoRepository.save(associado);
		
	}

	@Cacheable("cacheAssociadoPorCPF")
	@Override
	public Optional<Associado> buscarPorCpf(String cpf) {
		
		log.info("Validando a funcionalidade de Cache");
		return this.associadoRepository.findByCpf(cpf);
		
	}
}
