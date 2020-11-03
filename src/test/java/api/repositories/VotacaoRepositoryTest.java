package api.repositories;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import api.entities.Associado;
import api.entities.Pauta;
import api.entities.Votacao;

@SpringBootTest
@ActiveProfiles("test")
public class VotacaoRepositoryTest{
	
	@Autowired
	private VotacaoRepository votacaoRepository;
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	@Autowired
	private PautaRepository pautaRepository;
	
	private Associado associado;
	private Pauta pauta;
	private Votacao votacao;
	
	
	@BeforeEach
	public void init() throws Exception {
		
		this.associado = this.associadoRepository.save(gerarAssociado());
		this.pauta = this.pautaRepository.save(gerarPauta());		
		this.votacao = this.votacaoRepository.save(gerarVotacao(this.associado, this.pauta));		

	}

	@AfterEach
	public void tearDown() throws Exception {
		this.votacaoRepository.deleteAll();
	}
	
	@Test
	public void testBuscarVotoPorAssociadoEPauta() {
		
		Optional<Votacao> votacao = this.votacaoRepository.findByAssociadoAndPauta(this.associado, this.pauta);
		assertTrue(this.votacao.getId() == votacao.get().getId());		
		
	}
	
	@Test
	public void buscarResultadoVotacao() {
		
		Long votosSim = this.votacaoRepository.countByPautaAndVoto(this.pauta, "SIM");
		Long votosNao = this.votacaoRepository.countByPautaAndVoto(this.pauta, "NÃO");
		assertTrue(votosSim == 1 && votosNao == 0);
		
	}

	private Associado gerarAssociado() {
		
		Associado associado = new Associado();
		associado.setCpf("71308724462");
		associado.setNome("Associado teste");
		
		return associado;
	}
	
	private Pauta gerarPauta() {
		
		Pauta pauta = new Pauta();
		pauta.setNome("Nome da Pauta");
		pauta.setDescricao("Descrição de Pauta");
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 10);
		pauta.setValidaAte(calendar);
		
		return pauta;
		
	}
	
	private Votacao gerarVotacao(Associado associado, Pauta pauta) {
		
		Votacao votacao = new Votacao();
		votacao.setAssociado(associado);
		votacao.setPauta(pauta);
		votacao.setVotadoEm(Calendar.getInstance());
		votacao.setVoto("SIM");
		
		return votacao;
		
	}

}
