package api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import api.dtos.PautaDto;
import api.entities.Pauta;
import api.response.Response;
import api.services.PautaService;

@RestController
@CrossOrigin("*")
public class PautaController {

	private static final Logger log = LoggerFactory.getLogger(PautaController.class);

	@Autowired
	private PautaService pautaService;

	@PostMapping("/api/v1/pautas")
	public ResponseEntity<Response<PautaDto>> incluirV1(@Valid @RequestBody PautaDto pautaDto) {

		log.info("Incluindo Pauta {}", pautaDto.getNome());
		Response<PautaDto> response = new Response<>();

		Pauta pauta = new Pauta(pautaDto);
		this.pautaService.incluir(pauta);

		response.setData(new PautaDto(pauta));
		return ResponseEntity.ok(response);
	}

	@PutMapping("/api/v1/pautas/id/{id}")
	public ResponseEntity<Response<PautaDto>> abriSessaoV1(@Valid @PathVariable("id") Long id,
			@Valid @RequestBody Long tempoDaSessao) {

		log.info("Abrindo sessão para Pauta id: {}", id);
		Response<PautaDto> response = new Response<>();

		Pauta pauta = this.pautaService.abrirSessaoParaVotacao(new Pauta(id, tempoDaSessao));
		response.setData(new PautaDto(pauta));
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/v1/pautas")
	public ResponseEntity<Response<List<Pauta>>> listarAtivas() {

		Response<List<Pauta>> response = new Response<>();
		List<Pauta> pautasAtivas = this.pautaService.listarPautasAtivas();
		
		response.setData(pautasAtivas);
		return ResponseEntity.ok(response);
	}
}
