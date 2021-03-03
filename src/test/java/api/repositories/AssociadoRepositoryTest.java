package api.repositories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import api.entities.Associado;

@SpringBootTest
@ActiveProfiles("test")
public class AssociadoRepositoryTest {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	private Associado associado;

	@BeforeEach
	public void init() {
		this.associado = this.associadoRepository.save(gerarAssociado());
	}

	@AfterEach
	public void tearDown() {
//		this.associadoRepository.deleteAll();
		mongoTemplate.getDb().drop();
	}

	@Test
	public void testBuscarAssociadoPorCpf() {		
		Optional<Associado> associado = this.associadoRepository.findByCpf(this.associado.getCpf());
		assertTrue(associado.isPresent());		
	}	

	private Associado gerarAssociado() {
		Associado associado = new Associado();
		associado.setCpf("44158921082");
		associado.setNome("Associado teste");
		return associado;
	}
}
