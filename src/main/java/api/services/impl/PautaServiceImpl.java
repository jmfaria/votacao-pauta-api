package api.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import api.entities.Pauta;
import api.repositories.PautaRepository;
import api.services.PautaService;
import api.services.impl.exceptions.PautaInexistenteException;
import api.services.impl.exceptions.PautaJaCadastradaException;
import api.services.impl.exceptions.PautaJaEncerradaException;
import api.services.impl.exceptions.PautaNomeInvalidoException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PautaServiceImpl implements PautaService {

	@Autowired
	private PautaRepository pautaRepository;

	@Override
	public Pauta finalizarPauta(Pauta pauta) {
		log.info("Encerrando Pauta: {} id: {}", pauta.getNome(), pauta.getId());
		return this.pautaRepository.save(pauta);
	}

	@Override
	public boolean estaAbertaParaVotacao(String id) {
		return this.pautaRepository.findAllByIdAndValidaAteAfterAndEncerradaFalse(id, LocalDateTime.now()).isPresent();
	}

	@Override
	public List<Pauta> listarPautasNaoEncerradas() {
		return this.pautaRepository.findAllByValidaAteNotNullAndEncerradaFalse();
	}

	@Override
	public Page<Pauta> listarPautasAtivas(Pageable pageable) {
		return this.pautaRepository.findAllByValidaAteAfterAndEncerradaFalse(LocalDateTime.now(), pageable);
	}

	@Override
	public Page<Pauta> listarTodas(Pageable pageRequest) {
		return this.pautaRepository.findAll(pageRequest);
	}

	@Override
	public Optional<Pauta> buscarPorId(String id) {
		return this.pautaRepository.findById(id);
	}

	@Override
	public Pauta incluir(Pauta pauta) {

		if (!(pauta.getNome() != null && pauta.getNome().isEmpty())
				&& (pauta.getNome().length() < 5 || pauta.getNome().length() > 100)) {
			throw new PautaNomeInvalidoException();
		}

		if (this.buscarPeloNome(pauta.getNome()).isPresent()) {
			throw new PautaJaCadastradaException();
		}

		return this.pautaRepository.save(pauta);
	}

	@Override
	public Optional<Pauta> buscarPeloNome(String nome) {
		return this.pautaRepository.findByNome(nome);
	}

	@Override
	public Pauta abrirSessaoParaVotacao(Pauta pautaRecebida) {

		Optional<Pauta> pauta = this.buscarPorId(pautaRecebida.getId());

		if (!pauta.isPresent()) {
			throw new PautaInexistenteException();
		} else {

			// Atribui à pauta do contexto a validade da pauta criada atraves da requisição
			pauta.get().setValidaAte(pautaRecebida.getValidaAte());
		}

		if (Boolean.TRUE.equals(pauta.get().getEncerrada())) {
			throw new PautaJaEncerradaException();
		}

		return this.pautaRepository.save(pauta.get());
	}

}
