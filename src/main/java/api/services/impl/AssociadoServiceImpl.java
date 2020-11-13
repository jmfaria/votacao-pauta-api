package api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;

import api.entities.Associado;
import api.repositories.AssociadoRepository;
import api.services.AssociadoService;
import api.utils.CpfUtils;

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
		
		log.error("01");
		BindingResult result = new DataBinder(null).getBindingResult();
		
		log.error("02");
		if (associado.getCpf() != null && !CpfUtils.ValidarCPF(associado.getCpf())) {
			result.addError(new ObjectError("Associado", "CPF inválido."));
			log.error("03");
		} else if (this.buscarPorCpf(associado.getCpf()).isPresent()) {
			result.addError(new ObjectError("Associado", "Associado com esse CPF já foi incluído."));
			
		}
		log.error("04");
		return this.associadoRepository.save(associado);
		
	}

	@Cacheable("cacheAssociadoPorCPF")
	@Override
	public Optional<Associado> buscarPorCpf(String cpf) {
		
		log.info("Validando a funcionalidade de Cache");
		return this.associadoRepository.findByCpf(cpf);
		
	}
}
