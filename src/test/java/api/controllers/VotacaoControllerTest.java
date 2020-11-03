package api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
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

import api.dtos.VotacaoDto;
import api.entities.Associado;
import api.entities.Pauta;
import api.entities.Votacao;
import api.services.ApiPermissaoVotoService;
import api.services.AssociadoService;
import api.services.PautaService;
import api.services.VotacaoService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VotacaoControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private VotacaoService votacaoService;

	@MockBean
	private AssociadoService associadoService;

	@MockBean
	private PautaService pautaService;

	@MockBean
	private ApiPermissaoVotoService apiPermissaoVotoService;

	@BeforeEach
	public void init() {
		BDDMockito.given(this.associadoService.buscarPorId(1L)).willReturn(Optional.of(this.gerarAssociado()));
		BDDMockito.given(this.pautaService.buscarPorId(1L)).willReturn(Optional.of(this.gerarPauta()));
		BDDMockito.given(this.pautaService.estaAbertaParaVotacao(1L)).willReturn(Optional.of(this.gerarPauta()));
		BDDMockito.given(this.apiPermissaoVotoService.associadoComPermissaoParaVotar(this.gerarAssociado().getCpf()))
				.willReturn("ENABLE_TO_VOTE");
		BDDMockito.given(this.votacaoService.persistir(Mockito.any(Votacao.class))).willReturn(new Votacao());
	}

	@Test
	public void testVotar() throws Exception {

		mvc.perform(MockMvcRequestBuilders.post("/api/v1/votacao/").content(this.gerarJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(1L)).andExpect(jsonPath("$.errors").isEmpty());
	}

	private String gerarJsonRequisicaoPost() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this.gerarVotacaoDto());
	}

	private VotacaoDto gerarVotacaoDto() {
		VotacaoDto votacaoDto = new VotacaoDto();

		votacaoDto.setId(1L);
		votacaoDto.setIdAssociado(1L);
		votacaoDto.setIdPauta(1L);
		votacaoDto.setVoto("SIM");

		return votacaoDto;
	}

	private Pauta gerarPauta() {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 1);

		Pauta pauta = new Pauta();
		pauta.setId(1L);
		pauta.setNome("Nome da Pauta1");
		pauta.setDescricao("Descrição da Pauta1");
		pauta.setValidaAte(calendar);
		pauta.setEncerrada(false);

		return pauta;
	}

	private Associado gerarAssociado() {

		Associado associado = new Associado();
		associado.setId(1L);
		associado.setCpf("71308724462");
		associado.setNome("Associado 1");

		return associado;
	}
}
