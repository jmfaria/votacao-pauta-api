package api.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;

import api.entities.Pauta;
import api.repositories.PautaRepository;
import api.services.PautaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PautaServiceImpl implements PautaService {

	private static final String NOME_OBJETO = "Pauta";

	@Autowired
	private PautaRepository pautaRepository;	
	
	@Override
	public Pauta finalizarPauta(Pauta pauta) {
		log.info("Encerrando Pauta: {} id: {}", pauta.getNome(), pauta.getId());
		return this.pautaRepository.save(pauta);
	}

	@Override
	public boolean estaAbertaParaVotacao(Long id) {
		return this.pautaRepository.findByIdAndValidaAteAfter(id, LocalDateTime.now()).isPresent();
	}
	
	@Override
	public List<Pauta> listarPautasNaoEncerradas(){
		return this.pautaRepository.findByNotEncerrada();
	}

	@Override
	public Page<Pauta> listarPautasAtivas(Pageable pageable) {
		return this.pautaRepository.findByValidaAteAfter(LocalDateTime.now(), pageable);
	}
	
	@Override
	public Page<Pauta> listarTodas(Pageable pageRequest) {
		return this.pautaRepository.findAll(pageRequest);
	}

	@Override
	public Optional<Pauta> buscarPorId(Long id) {
		return this.pautaRepository.findById(id);
	}

	@Override
	public Pauta incluir(Pauta pauta) {
		
		
		BindingResult result = new DataBinder(null).getBindingResult();
		if (!(pauta.getNome() != null && pauta.getNome().isEmpty())
				&& (pauta.getNome().length() < 5 || pauta.getNome().length() > 100)) {
			result.addError(new ObjectError(NOME_OBJETO, "Nome deve conter entre 5 e 100 caracteres."));
		}

		if (this.buscarPeloNome(pauta.getNome()).isPresent()) {
			result.addError(new ObjectError(NOME_OBJETO, "Já existe Pauta com esse nome."));
		}
		
		return this.pautaRepository.save(pauta);
	}

	@Override
	public Optional<Pauta> buscarPeloNome(String nome) {
		return this.pautaRepository.findByNome(nome);
	}

	@Override
	public Pauta abrirSessaoParaVotacao(Pauta pautaRecebida) {	
		
		BindingResult result = new DataBinder(null).getBindingResult();			
		Optional<Pauta> pauta = this.buscarPorId(pautaRecebida.getId());			
		
		if (!pauta.isPresent()) {
			result.addError(new ObjectError(NOME_OBJETO, "Pauta inexistente."));
		}else {
			
			// Atribui à pauta do contexto a validade da pauta criada atraves da requisição
			pauta.get().setValidaAte(pautaRecebida.getValidaAte());
		}

		if (pauta.isPresent() && Boolean.TRUE.equals(pauta.get().getEncerrada())) {
			result.addError(new ObjectError(NOME_OBJETO, "Pauta já encerrada."));
		}

		if(pauta.isPresent())
			return this.pautaRepository.save(pauta.get());
		
		return null;
	}

}
