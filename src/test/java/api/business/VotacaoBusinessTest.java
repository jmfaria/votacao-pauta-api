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
import org.mockito.BDDMockito;
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

	@BeforeEach
	private void init() {
		given(this.associadoService.buscarPorId(this.gerarAssociado().getId()))
				.willReturn(Optional.of(this.gerarAssociado()));
		given(this.pautaService.buscarPorId(this.gerarPauta().getId()))
				.willReturn(Optional.of(gerarPauta()));
		given(this.pautaService.estaAbertaParaVotacao(gerarPauta().getId())).willReturn(true);
		given(this.votacaoService.jaVotou(gerarAssociado(), gerarPauta())).willReturn(Optional.empty());
		given(this.apiPermissaoVotoService.associadoComPermissaoParaVotar(gerarAssociado().getCpf()))
				.willReturn(true);
		given(this.votacaoService.votar(this.gerarVotacao())).willReturn(this.gerarVotacao());
	}
	
	@AfterEach
	private void reset() {
		    Mockito.reset(votacaoService);
		    Mockito.reset(associadoService);
		    Mockito.reset(pautaService);
		    Mockito.reset(apiPermissaoVotoService);		    
	}

	@Test
	public void votarComAssociadoInexistente() {

		given(this.associadoService.buscarPorId(this.gerarAssociado().getId())).willReturn(Optional.empty());
		assertThrows(AssociadoInexistenteException.class, () -> {
			this.votacaoBusiness.votar(this.gerarVotacao());
		});
	}

	@Test
	public void votarComPautaInexistente() {

		given(this.associadoService.buscarPorId(this.gerarAssociado().getId()))
				.willReturn(Optional.of(this.gerarAssociado()));
		given(this.pautaService.buscarPorId(this.gerarPauta().getId())).willReturn(Optional.empty());
		assertThrows(PautaInexistenteException.class, () -> {
			this.votacaoBusiness.votar(this.gerarVotacao());
		});

	}

	@Test
	public void votarComPautaNaoAbertaOuJaEncerrada() {

		given(this.associadoService.buscarPorId(this.gerarAssociado().getId()))
				.willReturn(Optional.of(this.gerarAssociado()));
		given(this.pautaService.buscarPorId(this.gerarPauta().getId()))
				.willReturn(Optional.of(gerarPauta()));
		given(this.pautaService.estaAbertaParaVotacao(gerarPauta().getId())).willReturn(false);
		assertThrows(PautaNaoAbertaOuJaFechadaException.class, () -> {
			this.votacaoBusiness.votar(this.gerarVotacao());
		});

	}

	@Test
	public void votarComAssociadoQueJaVotouPauta() {

		given(this.associadoService.buscarPorId(this.gerarAssociado().getId()))
				.willReturn(Optional.of(this.gerarAssociado()));
		given(this.pautaService.buscarPorId(this.gerarPauta().getId()))
				.willReturn(Optional.of(gerarPauta()));
		given(this.pautaService.estaAbertaParaVotacao(gerarPauta().getId())).willReturn(true);
		given(this.votacaoService.jaVotou(gerarAssociado(), gerarPauta()))
				.willReturn(Optional.of(this.gerarVotacao()));
		assertThrows(AssociadoJaVotouPautaException.class, () -> {
			this.votacaoBusiness.votar(this.gerarVotacao());
		});

	}

	@Test
	public void votarSemPermissaoApiExterna() {
 
		given(this.associadoService.buscarPorId(this.gerarAssociado().getId()))
				.willReturn(Optional.of(this.gerarAssociado()));
		given(this.pautaService.buscarPorId(this.gerarPauta().getId()))
				.willReturn(Optional.of(gerarPauta()));
		given(this.pautaService.estaAbertaParaVotacao(gerarPauta().getId())).willReturn(true);
		given(this.votacaoService.jaVotou(gerarAssociado(), gerarPauta())).willReturn(Optional.empty());
		given(this.apiPermissaoVotoService.associadoComPermissaoParaVotar(gerarAssociado().getCpf()))
				.willReturn(false);
		assertThrows(ApiExternalNaoPermitiuVotoException.class, () -> {
			this.votacaoBusiness.votar(this.gerarVotacao());
		});
	}

	@Test
	public void votarComVotoNaoAceito() {

		given(this.associadoService.buscarPorId(this.gerarAssociado().getId()))
				.willReturn(Optional.of(this.gerarAssociado()));
		given(this.pautaService.buscarPorId(this.gerarPauta().getId()))
				.willReturn(Optional.of(gerarPauta()));
		given(this.pautaService.estaAbertaParaVotacao(gerarPauta().getId())).willReturn(true);
		given(this.votacaoService.jaVotou(gerarAssociado(), gerarPauta())).willReturn(Optional.empty());
		given(this.apiPermissaoVotoService.associadoComPermissaoParaVotar(gerarAssociado().getCpf()))
				.willReturn(true);
		assertThrows(VotoNaoAceitoException.class, () -> {
			this.votacaoBusiness.votar(this.gerarVotacaoVotoNaoAceito());
		});

	}

	@Test
	public void votarSucesso() {

		given(this.associadoService.buscarPorId(this.gerarAssociado().getId()))
				.willReturn(Optional.of(this.gerarAssociado()));
		given(this.pautaService.buscarPorId(this.gerarPauta().getId()))
				.willReturn(Optional.of(gerarPauta()));
		given(this.pautaService.estaAbertaParaVotacao(gerarPauta().getId())).willReturn(true);
		given(this.votacaoService.jaVotou(gerarAssociado(), gerarPauta())).willReturn(Optional.empty());
		given(this.apiPermissaoVotoService.associadoComPermissaoParaVotar(gerarAssociado().getCpf()))
				.willReturn(true);
		given(this.votacaoService.votar(this.gerarVotacao())).willReturn(this.gerarVotacao());
		Votacao votacao = this.votacaoBusiness.votar(this.gerarVotacao());
		assertEquals(this.gerarVotacao().getId(), votacao.getId());
	}

	@Test
	public void jaVotou() {

		BDDMockito.given(this.associadoService.buscarPorId(this.gerarAssociado().getId()))
				.willReturn(Optional.of(this.gerarAssociado()));
		BDDMockito.given(this.pautaService.buscarPorId(this.gerarPauta().getId()))
				.willReturn(Optional.of(gerarPauta()));
		BDDMockito.given(this.pautaService.estaAbertaParaVotacao(gerarPauta().getId())).willReturn(true);

		BDDMockito.given(this.votacaoService.jaVotou(gerarAssociado(), gerarPauta()))
				.willReturn(Optional.of(this.gerarVotacao()));
		Optional<Votacao> votacao = this.votacaoBusiness.jaVotou(gerarAssociado(), gerarPauta());
		assertTrue(votacao.isPresent());

		BDDMockito.given(this.votacaoService.jaVotou(gerarAssociado(), gerarPauta())).willReturn(Optional.empty());
		votacao = this.votacaoBusiness.jaVotou(new Associado(), new Pauta());
		assertFalse(votacao.isPresent());

	}

	@Test
	public void contarVotos() {

		BDDMockito.given(this.pautaService.buscarPorId(this.gerarPauta().getId())).willReturn(Optional.empty());
		assertThrows(PautaInexistenteException.class, () -> {
			this.votacaoBusiness.resultadoVotacao(this.gerarPauta().getId());
		});

		BDDMockito.given(this.pautaService.buscarPorId(this.gerarPauta().getId()))
				.willReturn(Optional.of(this.gerarPauta()));
		BDDMockito.given(this.pautaService.estaAbertaParaVotacao(gerarPauta().getId())).willReturn(true);
		assertThrows(ResultadoVotacaoNaoConcluidoException.class, () -> {
			this.votacaoBusiness.resultadoVotacao(this.gerarPauta().getId());
		});

		BDDMockito.given(this.pautaService.buscarPorId(this.gerarPauta().getId()))
				.willReturn(Optional.of(this.gerarPauta()));
		BDDMockito.given(this.pautaService.estaAbertaParaVotacao(gerarPauta().getId())).willReturn(false);

		BDDMockito.given(this.votacaoService.contarVotos("1", "SIM")).willReturn(2L);
		BDDMockito.given(this.votacaoService.contarVotos("1", "NÃO")).willReturn(3L);

		ResultadoVotacao resultadoVotacao = this.votacaoBusiness.resultadoVotacao(this.gerarPauta().getId());
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
