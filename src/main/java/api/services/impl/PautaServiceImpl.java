package api.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;

import api.entities.Pauta;
import api.repositories.PautaRepository;
import api.services.PautaService;

@Service
public class PautaServiceImpl implements PautaService {

	private static final Logger log = LoggerFactory.getLogger(PautaServiceImpl.class);

	@Autowired
	private PautaRepository pautaRepository;	
	
	@Override
	public Pauta finalizarPauta(Pauta pauta) {
		log.info("Encerrando Pauta: {} id: {}", pauta.getNome(), pauta.getId());
		return this.pautaRepository.save(pauta);
	}

	@Override
	public Optional<Pauta> estaAbertaParaVotacao(Long id) {
		return this.pautaRepository.findByIdAndValidaAteAfter(id, LocalDateTime.now());
	}
	
	@Override
	public List<Pauta> listarPautasNaoEncerradas(){
		return this.pautaRepository.findByNotEncerrada();
	}

	@Override
	public List<Pauta> listarPautasAtivas() {
		return this.pautaRepository.findByValidaAteAfter(LocalDateTime.now());
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
			result.addError(new ObjectError("Pauta", "Nome deve conter entre 5 e 100 caracteres."));
		}

		if (this.buscarPeloNome(pauta.getNome()).isPresent()) {
			result.addError(new ObjectError("Pauta", "Já existe Pauta com esse nome."));
		}
		
		return this.pautaRepository.save(pauta);
	}

	@Override
	public Optional<Pauta> buscarPeloNome(String nome) {
		return this.pautaRepository.findByNome(nome);
	}

	@Override
	public Pauta abrirSessaoParaVotacao(Pauta pauta) {	
		
		BindingResult result = new DataBinder(null).getBindingResult();
		//pauta = this.buscarPorId(idPauta);
		if (pauta != null) {
			result.addError(new ObjectError("Pauta", "Pauta inexistente."));
		}

		if (pauta != null && pauta.getEncerrada()) {
			result.addError(new ObjectError("Pauta", "Pauta já encerrada."));
		}

		return this.pautaRepository.save(pauta);
	}

}
