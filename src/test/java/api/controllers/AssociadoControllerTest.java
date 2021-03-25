package api.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.dtos.AssociadoDto;
import api.entities.Associado;
import api.services.AssociadoService;

@TestMethodOrder(OrderAnnotation.class)
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AssociadoController.class)
public class AssociadoControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AssociadoService associadoService;
	private List<Associado> associados;
	
	
	@BeforeEach
	public void init() {
		
		this.associados = gerarAssociados();		
		given(this.associadoService.listar()).willReturn(this.associados);
		given(this.associadoService.buscarPorCpf(this.associados.get(0).getCpf()))
		.willReturn(Optional.of(this.associados.get(0)));
		
	}

	@Order(1)
	@Test
	public void testIncluir() throws Exception {		
		
		mvc.perform(MockMvcRequestBuilders.post("/api/v1/associados/").content(this.gerarJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.nome").value(this.associados.get(0).getNome()))
				.andExpect(jsonPath("$.data.cpf").value(this.associados.get(0).getCpf()))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	@Order(2)
	@Test
	public void testBuscarPorCpf() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders.get("/api/v1/associados/cpf/" + this.associados.get(0).getCpf())
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.nome", equalTo(this.associados.get(0).getNome())))
				.andExpect(jsonPath("$.data.cpf", equalTo(this.associados.get(0).getCpf())))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	@Order(3)
	@Test
	public void testListarAssociados() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/api/v1/associados").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.length()").value(this.associados.size()))
				.andExpect(jsonPath("$.data[0].id", equalTo(this.associados.get(0).getId())))
				.andExpect(jsonPath("$.data[0].nome", equalTo(this.associados.get(0).getNome())))
				.andExpect(jsonPath("$.data[0].cpf", equalTo(this.associados.get(0).getCpf())))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	private List<Associado> gerarAssociados() {
		
		List<Associado> associados = new ArrayList<Associado>();
		
		Associado associado = new Associado();		
		associado.setCpf("71308724462");
		associado.setNome("Associado 1");		
		associados.add(associado);
		
		associado = new Associado();		
		associado.setCpf("92392401012");
		associado.setNome("Associado 2");
		associados.add(associado);
		
		return associados;		
	}

	private String gerarJsonRequisicaoPost() throws JsonProcessingException {

		AssociadoDto associadoDto = new AssociadoDto();
		associadoDto.setId(null);
		associadoDto.setNome(this.associados.get(0).getNome());
		associadoDto.setCpf(this.associados.get(0).getCpf());
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(associadoDto);
	}

}
