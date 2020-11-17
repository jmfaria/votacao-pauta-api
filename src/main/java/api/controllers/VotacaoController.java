package api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import api.dtos.ResultadoVotacaoDto;
import api.dtos.VotacaoDto;
import api.entities.Votacao;
import api.response.Response;
import api.services.VotacaoService;

@RestController
@CrossOrigin("*")
public class VotacaoController {

	private static Logger log = LoggerFactory.getLogger(VotacaoController.class);

	@Autowired
	private VotacaoService votacaoService;

	@PostMapping("/api/v1/votacao")
	public ResponseEntity<Response<VotacaoDto>> votarV1(@Valid @RequestBody VotacaoDto votacaoDto)
			throws NoSuchAlgorithmException {

		Response<VotacaoDto> response = new Response<VotacaoDto>();
		votacaoDto = new VotacaoDto(this.votacaoService.votar(new Votacao(votacaoDto)));
		log.info("Efetuando votação - Associado id: {}, Pauta id: {}, voto: {}", votacaoDto.getIdAssociado(),
				votacaoDto.getIdPauta(), votacaoDto.getVoto());
		response.setData(votacaoDto);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/v1/votacao/resultado/{id}")
	public ResponseEntity<Response<ResultadoVotacaoDto>> resultadoV1(@PathVariable("id") Long id) {

		log.info("Buscando resultado da Votação de Pauta id: {}", id);
		Response<ResultadoVotacaoDto> response = new Response<ResultadoVotacaoDto>();

		response.setData(new ResultadoVotacaoDto(this.votacaoService.resultadoVotacao(id)));
		return ResponseEntity.ok(response);
	}
}
