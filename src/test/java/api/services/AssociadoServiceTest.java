package api.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import api.entities.Associado;
import api.exception.AssociadoCpfJaCadastradoException;
import api.exception.AssociadoInexistenteException;
import api.exception.CpfInvalidoException;
import api.repositories.AssociadoRepository;

@SpringBootTest(classes = {AssociadoService.class})
public class AssociadoServiceTest {

	@MockBean
	private AssociadoRepository associadoRepository;

	@Autowired
	private AssociadoService associadoService;

	@BeforeEach
	public void init() throws Exception {

		BDDMockito.given(this.associadoRepository.findAll()).willReturn(new ArrayList<Associado>());
		BDDMockito.given(this.associadoRepository.findById(Mockito.anyString()))
				.willReturn(Optional.of(new Associado()));
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
	public void incluir() {
		
		//CPF inválido
		assertThrows(CpfInvalidoException.class, () -> {
			this.associadoService.incluir(new Associado());	
		});
			
		//Incluído com sucesso
		Associado associado1 = this.associadoService.incluir(gerarAssociado());
		assertNotNull(associado1);
		
		//CPF já cadastrado
		BDDMockito.given(this.associadoRepository.findByCpf(Mockito.anyString()))
		.willReturn(Optional.of(new Associado()));
		assertThrows(AssociadoCpfJaCadastradoException.class, () -> {
			this.associadoService.incluir(gerarAssociado());	
		});
	}

	@Test
	public void buscarPorCpf() {

		assertThrows(AssociadoInexistenteException.class, () -> {
			this.associadoService.buscarPorCpf("123");
		});

		BDDMockito.given(this.associadoRepository.findByCpf(Mockito.anyString()))
				.willReturn(Optional.of(gerarAssociado()));
		Optional<Associado> associado = this.associadoService.buscarPorCpf("44158921082");
		assertTrue(associado.isPresent());
	}
	
	private Associado gerarAssociado() {
		Associado associado = new Associado();
		associado.setCpf("44158921082");
		associado.setNome("Associado teste");
		return associado;
	}

}