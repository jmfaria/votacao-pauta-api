package api.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import api.entities.Pauta;

@SpringBootTest
@ActiveProfiles("test")
public class PautaRepositoryTest{
	
	@Autowired
	private PautaRepository pautaRepository;
	
	private List<Pauta> pautas;
	
	@BeforeEach
	public void init() {
		this.pautaRepository.deleteAll();
		System.out.println("Não encerrada init: " +  this.pautaRepository.findByNotEncerrada().size());
		this.pautas = this.pautaRepository.saveAll(gerarPautas());		
	}
	
	@AfterEach
	public void tearDown() {
		System.out.println("Não encerrada tearDown: " +  this.pautaRepository.findByNotEncerrada().size());
		this.pautaRepository.deleteAll();
		System.out.println("Não encerrada tearDown: " +  this.pautaRepository.findByNotEncerrada().size());
	}
	
//	@Test
//	public void testPautaAbertaParaVotacao() {
//		
//		Optional<Pauta> pauta = this.pautaRepository.findByIdAndValidaAteAfter(
//				this.pautas.get(0).getId(), LocalDateTime.now());
//		assertTrue(pauta.isPresent());
//		
//	}
//
//	@Test
//	public void testBuscarPautaPorNome() {
//		
//		Optional<Pauta> pauta = this.pautaRepository.findByNome(this.pautas.get(0).getNome());
//		assertTrue(pauta.isPresent());
//		
//	}
//	
//	@Test
//	public void testBuscarPautasAtivas() {		
//		List<Pauta> pautas = this.pautaRepository.findByValidaAteAfter(LocalDateTime.now());
//		assertEquals(this.pautas.size(), pautas.size());
//	}
	
	@Test
	public void testBuscarPautasNaoEncerradas() {		
		List<Pauta> pautas = this.pautaRepository.findByNotEncerrada();
		assertEquals(pautas.size(), this.pautas.size());
	}
	
	private List<Pauta> gerarPautas() {
		
		System.out.println("Não encerrada: " + this.pautaRepository.findByNotEncerrada().size());
		LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(10L);
		
		List<Pauta> pautas = new ArrayList<Pauta>();
		Pauta pauta = new Pauta();
		pauta.setNome("Nome da Pauta1");
		pauta.setDescricao("Descrição de Pauta1");		
		pauta.setValidaAte(localDateTime);
		pauta.setEncerrada(false);		
		pautas.add(pauta);
		
		pauta = new Pauta();
		pauta.setNome("Nome da Pauta2");
		pauta.setDescricao("Descrição de Pauta2");		
		pauta.setValidaAte(localDateTime);
		pauta.setEncerrada(false);
		pautas.add(pauta);
		
		return pautas;
		
	}
	
}
