package api.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
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
import api.services.impl.exceptions.PautaJaCadastradaException;

@SpringBootTest
@ActiveProfiles("test")
public class PautaServiceTest {

	@MockBean
	private PautaRepository pautaRepository;

	@Autowired
	private PautaService pautaService;

	@BeforeEach
	public void init() throws Exception {

//		BDDMockito.given(this.pautaRepository.findByValidaAteAfter(Mockito.any(LocalDateTime.class)))
//				.willReturn(new ArrayList<Pauta>());
		BDDMockito.given(
				this.pautaRepository.findAllByIdAndValidaAteAfterAndEncerradaFalse(Mockito.anyString(), Mockito.any(LocalDateTime.class)))
				.willReturn(Optional.of(new Pauta()));
		BDDMockito.given(this.pautaRepository.findById(Mockito.anyString())).willReturn(Optional.of(new Pauta()));
		BDDMockito.given(this.pautaRepository.save(Mockito.any(Pauta.class))).willReturn(new Pauta());
		BDDMockito.given(this.pautaRepository.findByNome(Mockito.anyString())).willReturn(Optional.of(new Pauta()));
	}
	
	@AfterEach
	public void tearDown() throws Exception{
		this.pautaRepository.deleteAll();
	}

	@Test
	public void estaAbertaParaVotacao() {
		assertTrue(this.pautaService.estaAbertaParaVotacao("1"));
	}

//	@Test
//	public void listarPautasAtivas() {
//		List<Pauta> pautas = this.pautaService.listarPautasAtivas();
//		assertNotNull(pautas);
//	}

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
