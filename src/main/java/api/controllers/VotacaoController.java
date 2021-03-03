package api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.dtos.ResultadoVotacaoDto;
import api.dtos.VotacaoDto;
import api.entities.Votacao;
import api.response.Response;
import api.services.VotacaoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value="/api")
public class VotacaoController {

	@Autowired
	private VotacaoService votacaoService;
	
	@PostMapping("v1/votacao")
	public ResponseEntity<Response<VotacaoDto>> votarV1(@Valid @RequestBody VotacaoDto votacaoDto) {

		Response<VotacaoDto> response = new Response<>();
		votacaoDto = new VotacaoDto(this.votacaoService.votar(new Votacao(votacaoDto)));
		log.info("Efetuando votação - Associado id: {}, Pauta id: {}, voto: {}", votacaoDto.getIdAssociado(),
				votacaoDto.getIdPauta(), votacaoDto.getVoto());
		response.setData(votacaoDto);
		return ResponseEntity.ok(response);
	}

	@GetMapping("v1/votacao/resultado/{idPauta}")
	public ResponseEntity<Response<ResultadoVotacaoDto>> resultadoV1(@PathVariable("idPauta") String idPauta) {

		log.info("Buscando resultado da Votação de Pauta id: {}", idPauta);
		Response<ResultadoVotacaoDto> response = new Response<>();

		response.setData(new ResultadoVotacaoDto(this.votacaoService.resultadoVotacao(idPauta)));
		return ResponseEntity.ok(response);
	}
}
