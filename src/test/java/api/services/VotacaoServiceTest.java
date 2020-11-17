package api.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
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
	@MockBean
	private ApiPermissaoVotoService apiPermissaoVotoService;
	
	@Autowired
	private VotacaoService votacaoService;
	
	@BeforeEach
	public void init() throws Exception {
		BDDMockito.given(this.votacaoRepository.save(Mockito.any(Votacao.class))).willReturn(new Votacao());
		BDDMockito.given(this.associadoService.buscarPorId(Mockito.anyLong())).willReturn(
				Optional.of(gerarAssociado()));
		BDDMockito.given(this.pautaService.buscarPorId(Mockito.anyLong())).willReturn(
				Optional.of(new Pauta(1L)));
		BDDMockito.given(this.pautaService.estaAbertaParaVotacao(Mockito.anyLong())).willReturn(true);		
		
//		BDDMockito.given(this.votacaoService.jaVotou(gerarAssociado(), gerarPauta()))
//		.willReturn(false);
		
		BDDMockito.given(this.apiPermissaoVotoService.associadoComPermissaoParaVotar(Mockito.anyString()))
		.willReturn("ENABLE_TO_VOTE");
		
		BDDMockito.given(this.votacaoRepository
				.findByAssociadoAndPauta(Mockito.any(Associado.class), Mockito.any(Pauta.class)))
		.willReturn(Optional.empty());
	}

	@Test
	public void votar() {
		Votacao votacao = this.votacaoService.votar(this.gerarVotacao());
		assertNotNull(votacao);
	}

	@Test
	public void jaVotou() {
		Optional<Votacao> votacao = this.votacaoService.jaVotou(new Associado(), new Pauta());
		assertFalse(votacao.isPresent());
	}
	
	@Test
	public void resultadoVotacao() {
		ResultadoVotacao resultadoVotacao = this.votacaoService.resultadoVotacao(1L);
		assertNotNull(resultadoVotacao);
	}
	
	private Votacao gerarVotacao() {
		
		Votacao votacao = new Votacao();
		votacao.setAssociado(gerarAssociado());
		votacao.setPauta(gerarPauta());
		votacao.setVoto("sim");
		
		return votacao;
	}
	
	private Associado gerarAssociado() {
		Associado associado = new Associado();
		associado.setId(1L);
		associado.setCpf("71308724462");
		associado.setNome("Associado teste");
		return associado;
	}
	
	private Pauta gerarPauta() {

		LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(10L);
		Pauta pauta = new Pauta();
		pauta.setId(1L);
		pauta.setNome("Nome da Pauta1");
		pauta.setDescricao("Descrição da Pauta1");
		pauta.setValidaAte(localDateTime);

		return pauta;

	}

		
}
