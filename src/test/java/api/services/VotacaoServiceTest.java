package api.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import api.entities.Associado;
import api.entities.Pauta;
import api.entities.ResultadoVotacao;
import api.entities.Votacao;
import api.repositories.VotacaoRepository;

@SpringBootTest
@ActiveProfiles("test")
public class VotacaoServiceTest{
	
	@MockBean
	private VotacaoRepository votacaoRepository;
	@MockBean
	private AssociadoService associadoService;
	@MockBean
	private PautaService pautaService;
	
	@Autowired
	private VotacaoService votacaoService;
	
	@BeforeEach
	public void init() throws Exception {
		BDDMockito.given(this.votacaoRepository.save(Mockito.any(Votacao.class))).willReturn(new Votacao());
		BDDMockito.given(this.associadoService.buscarPorId(Mockito.anyLong())).willReturn(
				Optional.of(new Associado()));
		BDDMockito.given(this.pautaService.buscarPorId(Mockito.anyLong())).willReturn(
				Optional.of(new Pauta()));
		BDDMockito.given(this.votacaoRepository
				.findByAssociadoAndPauta(Mockito.any(Associado.class), Mockito.any(Pauta.class)))
		.willReturn(Optional.of(new Votacao()));
	}

	@Test
	public void votar() {
		Votacao votacao = this.votacaoService.votar(this.gerarVotacao());
		assertNotNull(votacao);
	}

	@Test
	public void jaVotou() {
		Optional<Votacao> votacao = this.votacaoService.jaVotou(new Associado(), new Pauta());
		assertTrue(votacao.isPresent());
	}
	
	@Test
	public void resultadoVotacao() {
		ResultadoVotacao resultadoVotacao = this.votacaoService.resultadoVotacao(1L);
		assertNotNull(resultadoVotacao);
	}
	
	private Votacao gerarVotacao() {
		
		Votacao votacao = new Votacao();
		votacao.setAssociado(new Associado(1L));
		votacao.setPauta(new Pauta(1L));
		votacao.setVoto("sim");
		
		return votacao;
	}

}
