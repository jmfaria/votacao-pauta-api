package api.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import api.entities.Pauta;
import api.exception.PautaJaCadastradaException;
import api.repositories.PautaRepository;

@SpringBootTest(classes = {PautaService.class})
public class PautaServiceTest {

	@MockBean
	private PautaRepository pautaRepository;

	@Autowired
	private PautaService pautaService;

	@BeforeEach
	public void init() throws Exception {

		given(this.pautaRepository.findAllByValidaAteAfterAndEncerradaFalse(
				Mockito.any(LocalDateTime.class), Mockito.any(PageRequest.class)))
				.willReturn(new PageImpl<Pauta>(new ArrayList<Pauta>()));
		given(
				this.pautaRepository.findAllByIdAndValidaAteAfterAndEncerradaFalse(Mockito.anyString(), Mockito.any(LocalDateTime.class)))
				.willReturn(Optional.of(new Pauta()));
		given(this.pautaRepository.findById(Mockito.anyString())).willReturn(Optional.of(new Pauta()));
		given(this.pautaRepository.save(Mockito.any(Pauta.class))).willReturn(new Pauta());
		given(this.pautaRepository.findByNome(Mockito.anyString())).willReturn(Optional.of(new Pauta()));
	}
	
	@Test
	public void estaAbertaParaVotacao() {
		assertTrue(this.pautaService.estaAbertaParaVotacao("1"));
	}

	@Test
	public void listarPautasAtivas() {
		
		Page<Pauta> pautas = this.pautaService.listarPautasAtivas(PageRequest.of(0, 10));
		assertNotNull(pautas);
	}

	@Test
	public void buscarPorId() {
		Optional<Pauta> pauta = this.pautaService.buscarPorId("1");
		assertTrue(pauta.isPresent());
	}

	@Test
	public void persistir() {
		
		assertThrows(PautaJaCadastradaException.class, () -> {
			this.pautaService.incluir(this.gerarPauta());
		  });
	}

	@Test
	public void buscarPeloNome() {
		Optional<Pauta> pauta = this.pautaService.buscarPeloNome("");
		assertTrue(pauta.isPresent());
	}

	@Test
	public void abrirSessaoParaVotacao() {
		Pauta pauta = this.pautaService.abrirSessaoParaVotacao(new Pauta("1", 1L));
		assertNotNull(pauta);
	}

	private Pauta gerarPauta() {

		LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(10L);
		Pauta pauta = new Pauta();
		pauta.setNome("Nome da Pauta1");
		pauta.setDescricao("Descrição de Pauta1");
		pauta.setValidaAte(localDateTime);
		pauta.setEncerrada(false);
		return pauta;
	}

}
