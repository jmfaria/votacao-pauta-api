package api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.business.VotacaoBusiness;
import api.dtos.VotacaoDto;
import api.entities.Associado;
import api.entities.Pauta;
import api.entities.Votacao;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = VotacaoController.class)
@TestMethodOrder(OrderAnnotation.class)
public class VotacaoControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private VotacaoBusiness votacaoBusiness;

	
	@BeforeEach
	public void init() {
		BDDMockito.given(this.votacaoBusiness.votar(Mockito.any(Votacao.class))).willReturn(this.gerarVotacao());
	}

	@Test
	public void votar() throws Exception {

		mvc.perform(MockMvcRequestBuilders.post("/api/v1/votacao/").content(this.gerarJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.idPauta").value(1L))
				.andExpect(jsonPath("$.data.idAssociado").value(1L))
				.andExpect(jsonPath("$.data.voto").value(this.gerarVotacaoDto().getVoto()))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	private String gerarJsonRequisicaoPost() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this.gerarVotacaoDto());
	}

	private VotacaoDto gerarVotacaoDto() {
		VotacaoDto votacaoDto = new VotacaoDto();

		votacaoDto.setId("1");
		votacaoDto.setIdAssociado("1");
		votacaoDto.setIdPauta("1");
		votacaoDto.setVoto("SIM");

		return votacaoDto;
	}

	private Votacao gerarVotacao() {
		
		Votacao votacao = new Votacao();

		votacao.setId("1");
		votacao.setAssociado(new Associado("1"));
		votacao.setPauta(new Pauta("1"));
		votacao.setVoto("SIM");
		votacao.setVotadoEm(LocalDateTime.now());

		return votacao;
	}
}
