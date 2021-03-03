package api.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
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
import api.repositories.AssociadoRepository;

@SpringBootTest
@ActiveProfiles("test")
public class AssociadoServiceTest {

	@MockBean
	private AssociadoRepository associadoRepository;

	@Autowired
	private AssociadoService associadoService;

	@BeforeEach
	public void init() throws Exception {
		
		BDDMockito.given(this.associadoRepository.findAll()).willReturn(new ArrayList<Associado>());
		BDDMockito.given(this.associadoRepository.findById(Mockito.anyString())).willReturn(Optional.of(new Associado()));
		BDDMockito.given(this.associadoRepository.findByCpf(Mockito.anyString())).willReturn(Optional.of(new Associado()));
		BDDMockito.given(this.associadoRepository.save(Mockito.any(Associado.class))).willReturn(new Associado());

	}

	@Test
	public void listar() {

		List<Associado> associados = this.associadoService.listar();
		assertNotNull(associados);
	}

	@Test
	public void buscarPorId() {

		Optional<Associado> associado = this.associadoService.buscarPorId("1");
		assertTrue(associado.isPresent());
	}

	@Test
	public void persistir() {
		Associado associado = this.associadoService.incluir(new Associado());
		assertNotNull(associado);
	}

	@Test
	public void buscarPorCpf() {
		
		Optional<Associado> associado = this.associadoRepository.findByCpf("123");
		assertTrue(associado.isPresent());
	}

}