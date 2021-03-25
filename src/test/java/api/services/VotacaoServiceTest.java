package api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import api.entities.Associado;
import api.entities.Pauta;
import api.entities.Votacao;
import api.exception.VotoNaoAceitoException;
import api.repositories.VotacaoRepository;

@SpringBootTest(classes= {VotacaoService.class})
public class VotacaoServiceTest{
	
	@MockBean
	private VotacaoRepository votacaoRepository;
	@Autowired
	private VotacaoService votacaoService;
	
	@BeforeEach
	public void init() throws Exception {
		BDDMockito.given(this.votacaoRepository.save(Mockito.any(Votacao.class))).willReturn(this.gerarVotacao());
		BDDMockito.given(this.votacaoRepository.findByAssociadoAndPauta(
				Mockito.any(Associado.class), Mockito.any(Pauta.class))).willReturn(
				Optional.of(this.gerarVotacao()));
		BDDMockito.given(this.votacaoRepository.countByPautaAndVoto(Mockito.anyString(), Mockito.anyString()))
		.willReturn(2L);
	}

	@Test
	public void votar() {
		
		Votacao votacao = this.votacaoService.votar(this.gerarVotacao());
		assertNotNull(votacao);
		assertEquals(votacao.getVoto(), this.gerarVotacao().getVoto());
	}
	
	@Test
	public void jaVotou() {
		Optional<Votacao> votacao = this.votacaoService.jaVotou(new Associado(), new Pauta());
		assertTrue(votacao.isPresent());
	}

	@Test
	public void contarVotos() {
				
		assertEquals(2L, this.votacaoService.contarVotos("1", "SIM"));
		
		assertThrows(VotoNaoAceitoException.class, () -> {
			this.votacaoService.contarVotos("1", "NAO");			
		});
	}
	
	private Votacao gerarVotacao() {
		
		Votacao votacao = new Votacao();
		votacao.setId("1");
		votacao.setAssociado(gerarAssociado());
		votacao.setPauta(gerarPauta());
		votacao.setVoto("sim");
		
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
