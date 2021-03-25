package api.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import api.entities.Associado;
import api.entities.Pauta;
import api.entities.ResultadoVotacao;
import api.entities.Votacao;
import api.exception.ApiExternalNaoPermitiuVotoException;
import api.exception.AssociadoInexistenteException;
import api.exception.AssociadoJaVotouPautaException;
import api.exception.PautaInexistenteException;
import api.exception.PautaNaoAbertaOuJaFechadaException;
import api.exception.ResultadoVotacaoNaoConcluidoException;
import api.exception.VotoNaoAceitoException;
import api.services.ApiPermissaoVotoService;
import api.services.AssociadoService;
import api.services.PautaService;
import api.services.VotacaoService;

@SpringBootTest(classes = VotacaoBusiness.class)
public class VotacaoBusinessTest {

	@Autowired
	private VotacaoBusiness votacaoBusiness;
	@MockBean
	private VotacaoService votacaoService;
	@MockBean
	private AssociadoService associadoService;
	@MockBean
	private PautaService pautaService;
	@MockBean
	private ApiPermissaoVotoService apiPermissaoVotoService;
	
	private Associado associado;
	private Votacao votacao;
	private Pauta pauta;
	private Votacao votacaoVotoNaoAceito;

	@BeforeEach
	private void init() {
		
		this.associado = this.gerarAssociado();
		this.pauta = this.gerarPauta();
		this.votacao = this.gerarVotacao();
		this.votacaoVotoNaoAceito = this.gerarVotacaoVotoNaoAceito();
		
	}

	@AfterEach
	private void reset() {
		votacaoBusiness = null;
		Mockito.reset(votacaoService);
		Mockito.reset(associadoService);
		Mockito.reset(pautaService);
		Mockito.reset(apiPermissaoVotoService);
	}

	@Test
	public void votarComAssociadoInexistente() {

		given(this.associadoService.buscarPorId(this.associado.getId())).willReturn(Optional.empty());
		assertThrows(AssociadoInexistenteException.class, () -> {
			this.votacaoBusiness.votar(this.votacao);
		});
	}

	@Test
	public void votarComPautaInexistente() {

		given(this.associadoService.buscarPorId(this.associado.getId()))
				.willReturn(Optional.of(this.associado));
		given(this.pautaService.buscarPorId(this.pauta.getId())).willReturn(Optional.empty());
		assertThrows(PautaInexistenteException.class, () -> {
			this.votacaoBusiness.votar(this.votacao);
		});

	}

	@Test
	public void votarComPautaNaoAbertaOuJaEncerrada() {

		given(this.associadoService.buscarPorId(this.associado.getId()))
				.willReturn(Optional.of(this.associado));
		given(this.pautaService.buscarPorId(this.pauta.getId())).willReturn(Optional.of(this.pauta));
		given(this.pautaService.estaAbertaParaVotacao(this.pauta.getId())).willReturn(false);
		assertThrows(PautaNaoAbertaOuJaFechadaException.class, () -> {
			this.votacaoBusiness.votar(this.votacao);
		});

	}

	@Test
	public void votarComAssociadoQueJaVotouPauta() {

		given(this.associadoService.buscarPorId(this.associado.getId()))
				.willReturn(Optional.of(this.associado));
		given(this.pautaService.buscarPorId(this.pauta.getId())).willReturn(Optional.of(this.pauta));
		given(this.pautaService.estaAbertaParaVotacao(this.pauta.getId())).willReturn(true);
		given(this.votacaoService.jaVotou(this.associado, this.pauta)).willReturn(Optional.of(this.votacao));
		assertThrows(AssociadoJaVotouPautaException.class, () -> {
			this.votacaoBusiness.votar(this.votacao);
		});

	}

	@Test
	public void votarSemPermissaoApiExterna() {

		given(this.associadoService.buscarPorId(this.associado.getId()))
				.willReturn(Optional.of(this.associado));
		given(this.pautaService.buscarPorId(this.pauta.getId())).willReturn(Optional.of(this.pauta));
		given(this.pautaService.estaAbertaParaVotacao(this.pauta.getId())).willReturn(true);
		given(this.votacaoService.jaVotou(this.associado, this.pauta)).willReturn(Optional.empty());
		given(this.apiPermissaoVotoService.associadoComPermissaoParaVotar(this.associado.getCpf())).willReturn(false);
		assertThrows(ApiExternalNaoPermitiuVotoException.class, () -> {
			this.votacaoBusiness.votar(this.votacao);
		});
	}

	@Test
	public void votarComVotoNaoAceito() {

		given(this.associadoService.buscarPorId(this.associado.getId()))
				.willReturn(Optional.of(this.associado));
		given(this.pautaService.buscarPorId(this.pauta.getId())).willReturn(Optional.of(this.pauta));
		given(this.pautaService.estaAbertaParaVotacao(this.pauta.getId())).willReturn(true);
		given(this.votacaoService.jaVotou(this.associado, this.pauta)).willReturn(Optional.empty());
		given(this.apiPermissaoVotoService.associadoComPermissaoParaVotar(this.associado.getCpf())).willReturn(true);
		assertThrows(VotoNaoAceitoException.class, () -> {
			this.votacaoBusiness.votar(this.votacaoVotoNaoAceito);
		});

	}

	@Test
	public void votarSucesso() {

		given(this.associadoService.buscarPorId(this.associado.getId()))
				.willReturn(Optional.of(this.associado));
		given(this.pautaService.buscarPorId(this.pauta.getId())).willReturn(Optional.of(this.pauta));
		given(this.pautaService.estaAbertaParaVotacao(this.pauta.getId())).willReturn(true);
		given(this.votacaoService.jaVotou(this.associado, this.pauta)).willReturn(Optional.empty());
		given(this.apiPermissaoVotoService.associadoComPermissaoParaVotar(this.associado.getCpf())).willReturn(true);
		given(this.votacaoService.votar(this.votacao)).willReturn(this.votacao);
		Votacao votacao = this.votacaoBusiness.votar(this.votacao);
		assertEquals(this.votacao.getId(), votacao.getId());
	}

	@Test
	public void jaVotou() {

		given(this.associadoService.buscarPorId(this.associado.getId()))
				.willReturn(Optional.of(this.associado));
		given(this.pautaService.buscarPorId(this.pauta.getId()))
				.willReturn(Optional.of(this.pauta));
		given(this.pautaService.estaAbertaParaVotacao(this.pauta.getId())).willReturn(true);

		given(this.votacaoService.jaVotou(this.associado, this.pauta))
				.willReturn(Optional.of(this.votacao));
		Optional<Votacao> votacao = this.votacaoBusiness.jaVotou(this.associado, this.pauta);
		assertTrue(votacao.isPresent());

		given(this.votacaoService.jaVotou(this.associado, this.pauta)).willReturn(Optional.empty());
		votacao = this.votacaoBusiness.jaVotou(this.associado, this.pauta);
		assertFalse(votacao.isPresent());

	}

	@Test
	public void contarVotos() {

		given(this.pautaService.buscarPorId(this.pauta.getId())).willReturn(Optional.empty());
		assertThrows(PautaInexistenteException.class, () -> {
			this.votacaoBusiness.resultadoVotacao(this.pauta.getId());
		});

		given(this.pautaService.buscarPorId(this.pauta.getId()))
				.willReturn(Optional.of(this.pauta));
		given(this.pautaService.estaAbertaParaVotacao(this.pauta.getId())).willReturn(true);
		assertThrows(ResultadoVotacaoNaoConcluidoException.class, () -> {
			this.votacaoBusiness.resultadoVotacao(this.pauta.getId());
		});

		given(this.pautaService.buscarPorId(this.pauta.getId()))
				.willReturn(Optional.of(this.pauta));
		given(this.pautaService.estaAbertaParaVotacao(this.pauta.getId())).willReturn(false);

		given(this.votacaoService.contarVotos("1", "SIM")).willReturn(2L);
		given(this.votacaoService.contarVotos("1", "NÃO")).willReturn(3L);

		ResultadoVotacao resultadoVotacao = this.votacaoBusiness.resultadoVotacao(this.pauta.getId());
		assertEquals(2L, resultadoVotacao.getVotosSim());
		assertEquals(3L, resultadoVotacao.getVotosNao());
		assertEquals(5L, resultadoVotacao.getVotosTotal());

	}

	private Votacao gerarVotacao() {

		Votacao votacao = new Votacao();
		votacao.setId("1");
		votacao.setAssociado(gerarAssociado());
		votacao.setPauta(gerarPauta());
		votacao.setVoto("sim");

		return votacao;
	}

	private Votacao gerarVotacaoVotoNaoAceito() {

		Votacao votacao = new Votacao();
		votacao.setId("1");
		votacao.setAssociado(gerarAssociado());
		votacao.setPauta(gerarPauta());
		votacao.setVoto("VotoNaoAceito");

		return votacao;
	}

	private Associado gerarAssociado() {
		Associado associado = new Associado();
		associado.setId("1");
		associado.setCpf("71308724462");
		associado.setNome("Associado teste");
		return associado;
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
}
