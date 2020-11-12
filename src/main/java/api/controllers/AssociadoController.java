package api.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import api.utils.CpfUtils;

@RestController
@CrossOrigin("*")
public class AssociadoController {

	private static final Logger log = LoggerFactory.getLogger(AssociadoController.class);

	@Autowired
	private AssociadoService associadoService;

	public AssociadoController() {
	}

	@PostMapping("/api/v1/associados")
	public ResponseEntity<Response<AssociadoDto>> incluirV1(@Valid @RequestBody AssociadoDto associadoDto,
			BindingResult result) {

		log.info("Incluindo Associado {}", associadoDto.getNome());
		Response<AssociadoDto> response = new Response<AssociadoDto>();

		this.validarDadosIncluir(associadoDto, result);

		if (result.hasErrors()) {
			log.error("Erro ao validar dados de inclusão de Associado: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Associado associado = converterParaAssociado(associadoDto);
		this.associadoService.incluir(associado);

		response.setData(converterParaAssociadoDto(associado));
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/v1/associados")
	public ResponseEntity<Response<List<Associado>>> listarV1() {

		log.info("Listando Associados");
		Response<List<Associado>> response = new Response<List<Associado>>();
		List<Associado> associados = this.associadoService.listar();

		if (!associados.isEmpty()) {
			response.setData(associados);
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping(value = "/api/v1/associados/cpf/{cpf}")
	public ResponseEntity<Response<AssociadoDto>> buscarPorCpfV1(@PathVariable("cpf") String cpf) {

		log.info("Buscando Associado por CPF: {}", cpf);
		Response<AssociadoDto> response = new Response<AssociadoDto>();
		Optional<Associado> associado = this.associadoService.buscarPorCpf(cpf);

		if (!associado.isPresent()) {
			String mensagem = "Associado não encontrado para o CPF: ";
			log.info(mensagem + "{}", cpf);
			response.getErrors().add(mensagem + cpf);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterParaAssociadoDto(associado.get()));
		return ResponseEntity.ok(response);
	}

	private void validarDadosIncluir(AssociadoDto associadoDto, BindingResult result) {

		if (associadoDto.getCpf() != null && !CpfUtils.ValidarCPF(associadoDto.getCpf())) {
			result.addError(new ObjectError("Associado", "CPF inválido."));
		} else if (this.associadoService.buscarPorCpf(associadoDto.getCpf()).isPresent()) {
			result.addError(new ObjectError("Associado", "Associado com esse CPF já foi incluído."));
		}
	}

	private AssociadoDto converterParaAssociadoDto(Associado associado) {

		AssociadoDto associadoDto = new AssociadoDto();
		associadoDto.setId(associado.getId());
		associadoDto.setNome(associado.getNome());
		associadoDto.setCpf(associado.getCpf());

		return associadoDto;
	}

	private Associado converterParaAssociado(AssociadoDto associadoDto) {

		Associado associado = new Associado();
		associado.setNome(associadoDto.getNome());
		associado.setCpf(associadoDto.getCpf());

		return associado;
	}
}
