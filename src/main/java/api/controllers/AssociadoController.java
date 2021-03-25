package api.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.dtos.AssociadoDto;
import api.entities.Associado;
import api.response.Response;
import api.services.AssociadoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value="/api")
public class AssociadoController {

	@Autowired
	private AssociadoService associadoService;

	@PostMapping("v1/associados")
	public ResponseEntity<Response<AssociadoDto>> incluir(@Valid @RequestBody AssociadoDto associadoDto) {

		log.info("Incluindo Associado {}", associadoDto.getNome());
		Response<AssociadoDto> response = new Response<>();

		Associado associado = new Associado(associadoDto);
		this.associadoService.incluir(associado);
		
		response.setData(new AssociadoDto(associado));
		return ResponseEntity.ok(response);
	}

	@GetMapping("v1/associados")
	public ResponseEntity<Response<List<Associado>>> listar() {

		log.info("Listando Associados");
		Response<List<Associado>> response = new Response<>();
		List<Associado> associados = this.associadoService.listar();

		response.setData(associados);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "v1/associados/cpf/{cpf}")
	public ResponseEntity<Response<AssociadoDto>> buscarPorCpf(@PathVariable("cpf") String cpf) {

		log.info("Buscando Associado por CPF: {}", cpf);
		Response<AssociadoDto> response = new Response<>();
		Optional<Associado> associado = this.associadoService.buscarPorCpf(cpf);

		if(associado.isPresent())
			response.setData(new AssociadoDto(associado.get()));
		
		return ResponseEntity.ok(response);
	}
}
