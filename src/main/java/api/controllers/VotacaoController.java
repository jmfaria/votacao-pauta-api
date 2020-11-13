package api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import api.dtos.ResultadoVotacaoDto;
import api.dtos.VotacaoDto;
import api.entities.Pauta;
import api.entities.ResultadoVotacao;
import api.entities.Votacao;
import api.response.Response;
import api.services.PautaService;
import api.services.VotacaoService;

@RestController
@CrossOrigin("*")
public class VotacaoController {

	private static Logger log = LoggerFactory.getLogger(VotacaoController.class);

	@Autowired
	private VotacaoService votacaoService;
//	@Autowired
//	private AssociadoService associadoService;
	@Autowired
	private PautaService pautaService;
//	@Autowired
//	private ApiPermissaoVotoService apiPermissaoVotoService;

	@PostMapping("/api/v1/votacao")
	public ResponseEntity<Response<VotacaoDto>> votarV1(@Valid @RequestBody VotacaoDto votacaoDto, BindingResult result)
			throws NoSuchAlgorithmException {

		log.info("Efetuando votação - Associado id: {}, Pauta id: {}, voto: {}", votacaoDto.getIdAssociado(),
				votacaoDto.getIdPauta(), votacaoDto.getVoto());

		Response<VotacaoDto> response = new Response<VotacaoDto>();
//		Optional<Associado> associado = this.associadoService.buscarPorId(votacaoDto.getIdAssociado());
//		Optional<Pauta> pauta = this.pautaService.buscarPorId(votacaoDto.getIdPauta());
//		this.validarDadosVotar(associado, pauta, votacaoDto, result);

		this.votacaoService.votar(new Votacao(votacaoDto));

		if (result.hasErrors()) {
			log.error("Erro ao validar dados de votação: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		} else {
			response.setData(votacaoDto);
			return ResponseEntity.ok(response);
		}
	}

	@GetMapping("/api/v1/votacao/resultado/{id}")
	public ResponseEntity<Response<ResultadoVotacaoDto>> resultadoV1(@PathVariable("id") Long id) {

		log.info("Buscando resultado da Votação de Pauta id: {}", id);
		Response<ResultadoVotacaoDto> response = new Response<ResultadoVotacaoDto>();
		BindingResult result = new DataBinder(null).getBindingResult();

//		Optional<Pauta> pauta = this.pautaService.buscarPorId(id);
//		this.validarDadosResultado(pauta, result);

		if (result.hasErrors()) {
			log.error("Erro ao validar dados de resultado de votação: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		} else {

			response.setData(new ResultadoVotacaoDto(this.votacaoService.resultadoVotacao(id)));
			return ResponseEntity.ok(response);
		}
	}

//	private ResultadoVotacaoDto converterParaDto(ResultadoVotacao resultadoVotacao) {
//
//		ResultadoVotacaoDto resultadoVotacaoDto = new ResultadoVotacaoDto();
//		resultadoVotacao.setVotosSim(resultadoVotacao.getVotosSim());
//		resultadoVotacao.setVotosNao(resultadoVotacao.getVotosNao());
//		resultadoVotacao.setVotosTotal(resultadoVotacao.getVotosTotal());
//		return resultadoVotacaoDto;
//	}

//	private void validarDadosResultado(Optional<Pauta> pauta, BindingResult result) {
//
//		if (!pauta.isPresent()) {
//			result.addError(new ObjectError("Votação de Pauta", "Pauta não existe."));
//		}
//
//		if (this.pautaService.estaAbertaParaVotacao(pauta.get().getId()).isPresent()) {
//			result.addError(new ObjectError("Votação de Pauta", "A votação para essa Pauta ainda não foi encerrada."));
//		}
//	}

//	private void validarDadosVotar(Optional<Associado> associado, Optional<Pauta> pauta, VotacaoDto votacaoDto,
//			BindingResult result) {
//		
//		if (!associado.isPresent()) {
//			result.addError(new ObjectError("Votação de Pauta", "Associado não existe."));
//			
//		} else if (!pauta.isPresent()) {
//			result.addError(new ObjectError("Votação de Pauta", "Pauta não existe."));
//			
//		} else if (!this.pautaService.estaAbertaParaVotacao(pauta.get().getId()).isPresent()) {
//			result.addError(new ObjectError("Votação de Pauta", "Pauta não aberta ou já encerrada para votação."));
//			
//		} else if (associado.isPresent() && pauta.isPresent()
//				&& this.votacaoService.jaVotou(associado.get(), pauta.get()).isPresent()) {
//			result.addError(new ObjectError("Votação de Pauta", "O Associado já votou nessa Pauta."));
//			
//		} else if (
//				this.apiPermissaoVotoService.associadoComPermissaoParaVotar(associado.get().getCpf())
//						.equalsIgnoreCase("UNABLE_TO_VOTE")) {
//			result.addError(new ObjectError("Votação de Pauta", "A API externa não permitiu o Associado votar."));
//		}
//
//		if (votacaoDto.getVoto() == null || (votacaoDto.getVoto().isEmpty()
//				&& !votacaoDto.getVoto().equalsIgnoreCase("sim") && !votacaoDto.getVoto().equalsIgnoreCase("não"))) {
//			result.addError(new ObjectError("Votação de Pauta",
//					"O voto deve ser expresso com as palavras \"SIM\" ou \"Não\"."));
//		}
//	}	
}
