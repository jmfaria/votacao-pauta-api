package api.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import api.entities.Pauta;
import api.repositories.PautaRepository;

@SpringBootTest
@ActiveProfiles("test")
public class PautaServiceTest {

	@MockBean
	private PautaRepository pautaRepository;

	@Autowired
	private PautaService pautaService;

	@BeforeEach
	public void init() throws Exception {

		BDDMockito.given(this.pautaRepository.findByValidaAteAfter(Mockito.any(LocalDateTime.class)))
				.willReturn(new ArrayList<Pauta>());
		BDDMockito.given(
				this.pautaRepository.findByIdAndValidaAteAfter(Mockito.anyLong(), Mockito.any(LocalDateTime.class)))
				.willReturn(Optional.of(new Pauta()));
		BDDMockito.given(this.pautaRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Pauta()));
		BDDMockito.given(this.pautaRepository.save(Mockito.any(Pauta.class))).willReturn(new Pauta());
		BDDMockito.given(this.pautaRepository.findByNome(Mockito.anyString())).willReturn(Optional.of(new Pauta()));
	}
	
	@AfterEach
	public void tearDown() throws Exception{
		this.pautaRepository.deleteAll();
	}

	@Test
	public void estaAbertaParaVotacao() {
		assertTrue(this.pautaService.estaAbertaParaVotacao(1L));
	}

	@Test
	public void listarPautasAtivas() {
		List<Pauta> pautas = this.pautaService.listarPautasAtivas();
		assertNotNull(pautas);
	}

	@Test
	public void buscarPorId() {
		Optional<Pauta> pauta = this.pautaService.buscarPorId(1L);
		assertTrue(pauta.isPresent());
	}

	@Test
	public void persistir() {
		Pauta pauta = this.pautaService.incluir(this.gerarPauta());
		assertNotNull(pauta);
	}

	@Test
	public void buscarPeloNome() {
		Optional<Pauta> pauta = this.pautaService.buscarPeloNome("");
		assertTrue(pauta.isPresent());
	}

	@Test
	public void abrirSessaoParaVotacao() {
		Pauta pauta = this.pautaService.abrirSessaoParaVotacao(new Pauta(1L, 1L));
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
