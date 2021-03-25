package api.business;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import api.entities.Pauta;
import api.entities.ResultadoVotacao;
import api.services.PautaService;

@SpringBootTest(classes = {PautaBusiness.class})
public class PautaBusinessTest {

	@MockBean
	private VotacaoBusiness votacaoBusiness;
	@MockBean
	private PautaService pautaService;
	@Autowired
	private PautaBusiness pautaBusiness;
	
	private Pauta pauta;
	private List<Pauta> pautas;
	private ResultadoVotacao resultadoVotacao;

	@BeforeEach
	private void init() {
		this.pauta = this.gerarPauta();
		this.pautas = this.gerarPautas();
		this.resultadoVotacao = this.gerarResultadoVotacao();		
	}

	@Test
	public void resultadoVotacao() {

		given(this.votacaoBusiness.resultadoVotacao(this.pauta.getId()))
				.willReturn(this.resultadoVotacao);
		assertNotNull(this.pautaBusiness.resultadoVotacao(this.pauta.getId()));

		given(this.votacaoBusiness.resultadoVotacao(this.pauta.getId())).willReturn(null);
		assertNull(this.pautaBusiness.resultadoVotacao(this.pauta.getId()));
	}

	public void finalizarPauta() {

		given(this.pautaService.finalizarPauta(this.pauta)).willReturn(this.pauta);
		assertNotNull(this.pautaService.finalizarPauta(this.pauta));

		given(this.pautaService.finalizarPauta(this.pauta)).willReturn(null);
		assertNull(this.pautaBusiness.finalizarPauta(this.pauta));
	}

	public void estaAbertaParaVotacao() {

		given(this.pautaService.estaAbertaParaVotacao(this.pauta.getId())).willReturn(true);
		assertTrue(this.pautaService.estaAbertaParaVotacao(this.pauta.getId()));

		given(this.pautaService.estaAbertaParaVotacao(this.pauta.getId())).willReturn(false);
		assertFalse(this.pautaService.estaAbertaParaVotacao(this.pauta.getId()));
	}

	public void listarPautasNaoEncerradas() {

		given(this.pautaService.listarPautasNaoEncerradas()).willReturn(this.pautas);
		assertEquals(2, this.pautaService.listarPautasNaoEncerradas().size());

	}

	public void listarPautasAtivas() {

		given(this.pautaService.listarPautasAtivas(PageRequest.of(0, 1)))
				.willReturn(new PageImpl<>(this.pautas));
		assertNotNull(this.pautaService.listarPautasAtivas(PageRequest.of(0, 1)));

	}

	public void listarTodas() {
		given(this.pautaService.listarTodas(PageRequest.of(0, 1))).willReturn(new PageImpl<>(this.pautas));
		assertNotNull(this.pautaService.listarTodas(PageRequest.of(0, 1)));
	}

	public void buscarPorId() {
		given(this.pautaService.buscarPorId(this.pauta.getId()))
				.willReturn(Optional.of(this.pauta));
		assertNotNull(this.pautaService.buscarPorId(this.pauta.getId()));
	}

	public void incluir() {
		given(this.pautaService.incluir(this.pauta)).willReturn(this.pauta);
		assertNotNull(this.pautaService.incluir(this.pauta));
	}

	public void buscarPeloNome() {
		given(this.pautaService.buscarPeloNome(this.pauta.getNome()))
		.willReturn(Optional.of(this.pauta));
		assertNotNull(this.pautaService.buscarPeloNome(this.pauta.getNome()));
	}

	public void abrirSessaoParaVotacao() {

		given(this.pautaService.abrirSessaoParaVotacao(this.pauta))
		.willReturn(this.pauta);
		assertNotNull(this.pautaService.abrirSessaoParaVotacao(this.pauta));
	}

	private Pauta gerarPauta() {

		LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(10L);
		Pauta pauta = new Pauta();
		pauta.setId("1");
		pauta.setNome("Nome da Pauta1");
		pauta.setDescricao("Descrição da Pauta1");
		pauta.setValidaAte(localDateTime);

		return pauta;
	}

	private List<Pauta> gerarPautas() {

		LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(10L);
		List<Pauta> pautas = new ArrayList<Pauta>();

		Pauta pauta1 = new Pauta();
		pauta1.setId("1");
		pauta1.setNome("Nome da Pauta1");
		pauta1.setDescricao("Descrição da Pauta1");
		pauta1.setValidaAte(localDateTime);
		pautas.add(pauta1);

		Pauta pauta2 = new Pauta();
		pauta2.setId("2");
		pauta2.setNome("Nome da Pauta2");
		pauta2.setDescricao("Descrição da Pauta2");
		pauta2.setValidaAte(localDateTime);

		return pautas;
	}

	private ResultadoVotacao gerarResultadoVotacao() {

		ResultadoVotacao resultadoVotacao = new ResultadoVotacao();
		resultadoVotacao.setPauta(this.gerarPauta());
		resultadoVotacao.setVotosSim(10L);
		resultadoVotacao.setVotosNao(10L);
		resultadoVotacao.setVotosTotal(20L);

		return resultadoVotacao;
	}

}
