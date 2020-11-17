package api.controllers;

import java.util.List;
import java.util.Optional;

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

import api.dtos.AssociadoDto;
import api.entities.Associado;
import api.response.Response;
import api.services.AssociadoService;

@RestController
@CrossOrigin("*")
public class AssociadoController {

	private static final Logger log = LoggerFactory.getLogger(AssociadoController.class);

	@Autowired
	private AssociadoService associadoService;

	public AssociadoController() {
	}

	@PostMapping("/api/v1/associados")
	public ResponseEntity<Response<AssociadoDto>> incluirV1(@Valid @RequestBody AssociadoDto associadoDto) {

		log.info("Incluindo Associado {}", associadoDto.getNome());
		Response<AssociadoDto> response = new Response<AssociadoDto>();

		Associado associado = new Associado(associadoDto);
		this.associadoService.incluir(associado);

		response.setData(new AssociadoDto(associado));
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/v1/associados")
	public ResponseEntity<Response<List<Associado>>> listarV1() {

		log.info("Listando Associados");
		Response<List<Associado>> response = new Response<List<Associado>>();
		List<Associado> associados = this.associadoService.listar();

		response.setData(associados);
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/api/v1/associados/cpf/{cpf}")
	public ResponseEntity<Response<AssociadoDto>> buscarPorCpfV1(@PathVariable("cpf") String cpf) {

		log.info("Buscando Associado por CPF: {}", cpf);
		Response<AssociadoDto> response = new Response<AssociadoDto>();
		Optional<Associado> associado = this.associadoService.buscarPorCpf(cpf);

		response.setData(new AssociadoDto(associado.get()));
		return ResponseEntity.ok(response);
	}
}
