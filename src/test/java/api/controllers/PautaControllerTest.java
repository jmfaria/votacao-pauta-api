package api.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.business.PautaBusiness;
import api.dtos.PautaDto;
import api.entities.Pauta;
import api.entities.ResultadoVotacao;

@TestMethodOrder(OrderAnnotation.class)
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PautaController.class)
public class PautaControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private PautaBusiness pautaBusiness;

	private Pauta pauta;
	private List<Pauta> pautas;
	private ResultadoVotacao resultadoVotacao;
	private Long tempoValidade = 10L;

	@BeforeEach
	public void init() {
		this.pauta = gerarPauta();
		this.pautas = gerarPautas();
		this.resultadoVotacao = gerarResultadoVotacao();

		given(this.pautaBusiness.listarTodas(PageRequest.of(0, 1)))
				.willReturn(new PageImpl<Pauta>(this.pautas));
		given(this.pautaBusiness.abrirSessaoParaVotacao(Mockito.any(Pauta.class))).willReturn(this.pauta);
		given(this.pautaBusiness.listarPautasAtivas(PageRequest.of(0, 1)))
				.willReturn(new PageImpl<Pauta>(this.pautas));
		
		given(this.pautaBusiness.resultadoVotacao(this.pauta.getId()))
		.willReturn(this.resultadoVotacao);
	}

	@Order(1)
	@Test
	public void testIncluir() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/api/v1/pautas").content(this.gerarJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.nome").value(this.pauta.getNome()))
				.andExpect(jsonPath("$.data.descricao").value(this.pauta.getDescricao()))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	@Order(2)
	@Test
	public void testAbrirSessao() throws Exception {

		mvc.perform(MockMvcRequestBuilders.put("/api/v1/pautas/id/" + this.pauta.getId())
				.content(this.gerarJsonRequisicaoAbrirSessao()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.nome").value(this.pauta.getNome()))
				.andExpect(jsonPath("$.data.descricao").value(this.pauta.getDescricao()))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	@Order(3)
	@Test
	public void listarAtivas() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders.get("/api/v1/pautas/ativas?page=0&size=1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.content[0].nome").value(this.pauta.getNome()))
				.andExpect(jsonPath("$.data.content[0].descricao").value(this.pauta.getDescricao()))
				.andExpect(jsonPath("$.errors").isEmpty());
		
	}

	@Order(4)
	@Test
	public void resultado() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/api/v1/pautas/votacao/" + this.pauta.getId() + "/resultado")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.votosSim").value(10))
				.andExpect(jsonPath("$.data.votosNao").value(10))
				.andExpect(jsonPath("$.data.votosTotal").value(20))
				.andExpect(jsonPath("$.errors").isEmpty());

	}

	private Pauta gerarPauta() {

		LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(tempoValidade);
		Pauta pauta = new Pauta();
		pauta.setId("1");
		pauta.setNome("Nome da Pauta1");
		pauta.setDescricao("Descrição da Pauta1");
		pauta.setValidaAte(localDateTime);

		return pauta;
	}

	private List<Pauta> gerarPautas() {

		LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(tempoValidade);
		List<Pauta> pautas = new ArrayList<Pauta>();

		Pauta pauta1 = new Pauta();
		pauta1.setId("1");
		pauta1.setNome("Nome da Pauta1");
		pauta1.setDescricao("Descrição da Pauta1");
		pauta1.setValidaAte(localDateTime);
		pautas.add(pauta1);

		Pauta pauta2 = new Pauta();
		pauta2.setId("2");
		pauta2.setNome("Nome da Pauta2");
		pauta2.setDescricao("Descrição da Pauta2");
		pauta2.setValidaAte(localDateTime);
		pautas.add(pauta2);

		return pautas;
	}
	
	private ResultadoVotacao gerarResultadoVotacao() {
		
		ResultadoVotacao resultadoVotacao = new ResultadoVotacao();
		resultadoVotacao.setPauta(this.gerarPauta());
		resultadoVotacao.setVotosSim(10L);
		resultadoVotacao.setVotosNao(10L);
		resultadoVotacao.setVotosTotal(20L);
		
		return resultadoVotacao;				
	}

	private String gerarJsonRequisicaoAbrirSessao() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(tempoValidade);
	}

	private String gerarJsonRequisicaoPost() throws JsonProcessingException {

		PautaDto pautaDto = new PautaDto();
		pautaDto.setId("1");
		pautaDto.setNome(this.pauta.getNome());
		pautaDto.setDescricao(this.pauta.getDescricao());
		pautaDto.setTempoSessaoEmMinutos(tempoValidade);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(pautaDto);
	}

}
