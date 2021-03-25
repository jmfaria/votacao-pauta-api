package api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import api.entities.Pauta;
import api.exception.PautaInexistenteException;
import api.exception.PautaJaCadastradaException;
import api.exception.PautaJaEncerradaException;
import api.exception.PautaNomeInvalidoException;
import api.repositories.PautaRepository;
import api.services.PautaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PautaService {

	@Autowired
	private PautaRepository pautaRepository;

	public Pauta finalizarPauta(Pauta pauta) {
		log.info("Encerrando Pauta: {} id: {}", pauta.getNome(), pauta.getId());
		return this.pautaRepository.save(pauta);
	}

	public boolean estaAbertaParaVotacao(String id) {
		return this.pautaRepository.findAllByIdAndValidaAteAfterAndEncerradaFalse(id, LocalDateTime.now()).isPresent();
	}

	public List<Pauta> listarPautasNaoEncerradas() {
		return this.pautaRepository.findAllByValidaAteNotNullAndEncerradaFalse();
	}

	public Page<Pauta> listarPautasAtivas(Pageable pageable) {
		return this.pautaRepository.findAllByValidaAteAfterAndEncerradaFalse(LocalDateTime.now(), pageable);
	}

	public Page<Pauta> listarTodas(Pageable pageRequest) {
		return this.pautaRepository.findAll(pageRequest);
	}

	public Optional<Pauta> buscarPorId(String id) {
		return this.pautaRepository.findById(id);
	}

	public Pauta incluir(Pauta pauta) {

		if (!(pauta.getNome() != null && pauta.getNome().isEmpty())
				&& (pauta.getNome().length() < 5 || pauta.getNome().length() > 100)) {
			log.error("Pauta com nome inválido: {}", pauta.getNome());
			throw new PautaNomeInvalidoException();
		}

		if (this.buscarPeloNome(pauta.getNome()).isPresent()) {
			throw new PautaJaCadastradaException();
		}

		return this.pautaRepository.save(pauta);
	}

	public Optional<Pauta> buscarPeloNome(String nome) {
		return this.pautaRepository.findByNome(nome);
	}

	public Pauta abrirSessaoParaVotacao(Pauta pautaRecebida) {

		Optional<Pauta> pauta = this.buscarPorId(pautaRecebida.getId());

		if (!pauta.isPresent()) {
			log.error("Pauta inexistente: {}", pautaRecebida.getId());
			throw new PautaInexistenteException();
		} else {
			pauta.get().setValidaAte(pautaRecebida.getValidaAte());
		}

		if (Boolean.TRUE.equals(pauta.get().getEncerrada())) {
			throw new PautaJaEncerradaException();
		}

		return this.pautaRepository.save(pauta.get());
	}

}
