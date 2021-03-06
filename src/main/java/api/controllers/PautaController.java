package api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.business.PautaBusiness;
import api.dtos.PautaDto;
import api.dtos.ResultadoVotacaoDto;
import api.entities.Pauta;
import api.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value="/api")
public class PautaController {

	@Autowired
	private PautaBusiness pautaBusiness;

	@PostMapping("v1/pautas")
	public ResponseEntity<Response<PautaDto>> incluir(@Valid @RequestBody PautaDto pautaDto) {

		log.info("Incluindo Pauta {}", pautaDto.getNome());
		Response<PautaDto> response = new Response<>();

		Pauta pauta = new Pauta(pautaDto);
		this.pautaBusiness.incluir(pauta);

		response.setData(new PautaDto(pauta));
		return ResponseEntity.ok(response);
	}

	@PutMapping("v1/pautas/id/{id}")
	public ResponseEntity<Response<PautaDto>> abriSessao(@Valid @PathVariable("id") String id,
			@Valid @RequestBody Long tempoDaSessao) {

		log.info("Abrindo sessão para Pauta id: {}", id);
		Response<PautaDto> response = new Response<>();

		Pauta pauta = this.pautaBusiness.abrirSessaoParaVotacao(new Pauta(id, tempoDaSessao));
		response.setData(new PautaDto(pauta));
		return ResponseEntity.ok(response);
	}

	@GetMapping("v1/pautas/ativas")
	public ResponseEntity<Response<Page<Pauta>>> listarAtivas(Pageable pageable) {

		Response<Page<Pauta>> response = new Response<>();
		Page<Pauta> pautasAtivas = this.pautaBusiness.listarPautasAtivas(pageable);
		
		response.setData(pautasAtivas);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("v1/pautas")
	public ResponseEntity<Response<Page<Pauta>>> listarTodas(
			Pageable pageable
			) {

		Response<Page<Pauta>> response = new Response<>();
		Page<Pauta> pautasAtivas = this.pautaBusiness.listarTodas(pageable);
		
		response.setData(pautasAtivas);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("v1/pautas/votacao/{idPauta}/resultado")
	public ResponseEntity<Response<ResultadoVotacaoDto>> resultado(@PathVariable("idPauta") String idPauta) {

		log.info("Buscando resultado da Votação de Pauta id: {}", idPauta);
		Response<ResultadoVotacaoDto> response = new Response<>();

		response.setData(new ResultadoVotacaoDto(this.pautaBusiness.resultadoVotacao(idPauta)));
		return ResponseEntity.ok(response);
	}
	
}
