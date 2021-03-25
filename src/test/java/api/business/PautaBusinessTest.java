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

	@BeforeEach
	private void init() {

//		BDDMockito.given(this.pautaService.buscarPorId(this.gerarPauta().getId()))
//				.willReturn(Optional.of(gerarPauta()));
//		BDDMockito.given(this.pautaService.estaAbertaParaVotacao(gerarPauta().getId())).willReturn(true);
//		BDDMockito.given(this.votacaoService.jaVotou(gerarAssociado(), gerarPauta())).willReturn(Optional.empty());
//		BDDMockito.given(this.apiPermissaoVotoService.associadoComPermissaoParaVotar(gerarAssociado().getCpf()))
//				.willReturn(true);
//		BDDMockito.given(this.votacaoService.votar(this.gerarVotacao())).willReturn(this.gerarVotacao());
	}

	@Test
	public void resultadoVotacao() {

		given(this.votacaoBusiness.resultadoVotacao(this.gerarPauta().getId()))
				.willReturn(this.gerarResultadoVotacao());
		assertNotNull(this.pautaBusiness.resultadoVotacao(this.gerarPauta().getId()));

		given(this.votacaoBusiness.resultadoVotacao(this.gerarPauta().getId())).willReturn(null);
		assertNull(this.pautaBusiness.resultadoVotacao(this.gerarPauta().getId()));
	}

	public void finalizarPauta() {

		given(this.pautaService.finalizarPauta(this.gerarPauta())).willReturn(this.gerarPauta());
		assertNotNull(this.pautaService.finalizarPauta(this.gerarPauta()));

		given(this.pautaService.finalizarPauta(this.gerarPauta())).willReturn(null);
		assertNull(this.pautaBusiness.finalizarPauta(this.gerarPauta()));
	}

	public void estaAbertaParaVotacao() {

		given(this.pautaService.estaAbertaParaVotacao(this.gerarPauta().getId())).willReturn(true);
		assertTrue(this.pautaService.estaAbertaParaVotacao(this.gerarPauta().getId()));

		given(this.pautaService.estaAbertaParaVotacao(this.gerarPauta().getId())).willReturn(false);
		assertFalse(this.pautaService.estaAbertaParaVotacao(this.gerarPauta().getId()));
	}

	public void listarPautasNaoEncerradas() {

		given(this.pautaService.listarPautasNaoEncerradas()).willReturn(gerarPautas());
		assertEquals(2, this.pautaService.listarPautasNaoEncerradas().size());

	}

	public void listarPautasAtivas() {

		given(this.pautaService.listarPautasAtivas(PageRequest.of(0, 1)))
				.willReturn(new PageImpl<>(gerarPautas()));
		assertNotNull(this.pautaService.listarPautasAtivas(PageRequest.of(0, 1)));

	}

	public void listarTodas() {
		given(this.pautaService.listarTodas(PageRequest.of(0, 1))).willReturn(new PageImpl<>(gerarPautas()));
		assertNotNull(this.pautaService.listarTodas(PageRequest.of(0, 1)));
	}

	public void buscarPorId() {
		given(this.pautaService.buscarPorId(this.gerarPauta().getId()))
				.willReturn(Optional.of(this.gerarPauta()));
		assertNotNull(this.pautaService.buscarPorId(this.gerarPauta().getId()));
	}

	public void incluir() {
		given(this.pautaService.incluir(this.gerarPauta())).willReturn(this.gerarPauta());
		assertNotNull(this.pautaService.incluir(this.gerarPauta()));
	}

	public void buscarPeloNome() {
		given(this.pautaService.buscarPeloNome(this.gerarPauta().getNome()))
		.willReturn(Optional.of(this.gerarPauta()));
		assertNotNull(this.pautaService.buscarPeloNome(this.gerarPauta().getNome()));
	}

	public void abrirSessaoParaVotacao() {

		given(this.pautaService.abrirSessaoParaVotacao(this.gerarPauta()))
		.willReturn(this.gerarPauta());
		assertNotNull(this.pautaService.abrirSessaoParaVotacao(this.gerarPauta()));
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
