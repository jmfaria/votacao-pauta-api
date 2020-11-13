package api.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.dtos.PautaDto;
import api.entities.Pauta;
import api.repositories.PautaRepository;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PautaControllerTest {

	@Autowired
	private MockMvc mvc;
	
	private Pauta pauta;
	private Long tempoValidade = 10L;
	
	@BeforeEach
	public void init() {
		this.pauta = gerarPauta();
	}
	
	@Order(1)
	@Test
	public void testIncluirV1() throws Exception {		
		mvc.perform(MockMvcRequestBuilders.post("/api/v1/pautas").content(this.gerarJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(this.pauta.getId()))
				.andExpect(jsonPath("$.data.nome").value(this.pauta.getNome()))
				.andExpect(jsonPath("$.data.descricao").value(this.pauta.getDescricao()))
				.andExpect(jsonPath("$.data.tempoSessaoEmMinutos").value(equalTo(null)))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	@Order(2)
	@Test
	public void testAbrirSessaoV1() throws Exception {

		mvc.perform(MockMvcRequestBuilders.put("/api/v1/pautas/id/" + this.pauta.getId())
				.content(this.gerarJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(this.pauta.getId()))
				.andExpect(jsonPath("$.data.nome").value(this.pauta.getNome()))
				.andExpect(jsonPath("$.data.descricao").value(this.pauta.getDescricao()))
				.andExpect(jsonPath("$.data.tempoSessaoEmMinutos").value(tempoValidade))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	private Pauta gerarPauta() {

		LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(tempoValidade);
		Pauta pauta = new Pauta();
		pauta.setId(1L);
		pauta.setNome("Nome da Pauta1");
		pauta.setDescricao("Descrição da Pauta1");
		pauta.setValidaAte(localDateTime);

		return pauta;

	}

	private String gerarJsonRequisicaoPost() throws JsonProcessingException {

		PautaDto pautaDto = new PautaDto();
		pautaDto.setId(null);
		pautaDto.setNome(this.pauta.getNome());
		pautaDto.setDescricao(this.pauta.getDescricao());
		pautaDto.setTempoSessaoEmMinutos(tempoValidade);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(pautaDto);
	}

}
