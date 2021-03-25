package api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.business.VotacaoBusiness;
import api.dtos.VotacaoDto;
import api.entities.Votacao;
import api.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value="/api")
public class VotacaoController {

	@Autowired
	private VotacaoBusiness votacaoBusiness;
	
	@PostMapping("v1/votacao")
	public ResponseEntity<Response<VotacaoDto>> votar(@Valid @RequestBody VotacaoDto votacaoDto) {

		Response<VotacaoDto> response = new Response<>();
		votacaoDto = new VotacaoDto(this.votacaoBusiness.votar(new Votacao(votacaoDto)));
		log.info("Efetuando votação - Associado id: {}, Pauta id: {}, voto: {}", votacaoDto.getIdAssociado(),
				votacaoDto.getIdPauta(), votacaoDto.getVoto());
		response.setData(votacaoDto);
		return ResponseEntity.ok(response);
	}
}
