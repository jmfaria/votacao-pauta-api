package api.controllers;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
	public ResponseEntity<Response<PautaDto>> incluirV1(@Valid @RequestBody PautaDto pautaDto, BindingResult result) {

		log.info("Incluindo Pauta {}", pautaDto.getNome());
		Response<PautaDto> response = new Response<PautaDto>();

		// this.validarDadosIncluir(pautaDto, result);
		
		Pauta pauta = new Pauta(pautaDto);
		this.pautaService.incluir(pauta);

//		Pauta pauta = this.pautaService.incluir(new Pauta(pautaDto));

		if (result.hasErrors()) {
			log.error("Erro ao validar dados de inclusão de Pauta: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		} else {
			response.setData(new PautaDto(pauta));
			return ResponseEntity.ok(response);
		}
	}

	@PutMapping("/api/v1/pautas/id/{id}")
	public ResponseEntity<Response<PautaDto>> abriSessaoV1(@Valid @PathVariable("id") Long id,
			@Valid @RequestBody PautaDto pautaDto, BindingResult result) {

		log.info("Abrindo sessão para Pauta id: {}", id);
		Response<PautaDto> response = new Response<PautaDto>();

//		Optional<Pauta> pauta = this.pautaService.buscarPorId(id);
//		this.validarDadosAbrirSessao(pauta, result);

		//pauta.get().setValidaAte(definirTempoDeSessao(pautaDto));
		Pauta pauta = this.pautaService.abrirSessaoParaVotacao(new Pauta(id, pautaDto));

		if (result.hasErrors()) {
			log.error("Erro ao validar dados para abertura de sessão de Pauta: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		} else {
			response.setData(new PautaDto(pauta));
			return ResponseEntity.ok(response);
		}
	}

	@GetMapping("/api/v1/pautas")
	public ResponseEntity<Response<List<Pauta>>> listarAtivas() {

		Response<List<Pauta>> response = new Response<List<Pauta>>();
		List<Pauta> pautasAtivas = this.pautaService.listarPautasAtivas();

		if (!pautasAtivas.isEmpty()) {
			response.setData(pautasAtivas);
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

//	private LocalDateTime definirTempoDeSessao(PautaDto pautaDto) {
//
//		LocalDateTime tempoDaSessao = LocalDateTime.now();
//
//		if (pautaDto.getTempoSessaoEmMinutos() != null && pautaDto.getTempoSessaoEmMinutos() > 0) {
//
//			// Abrir sessao com o tempo definido
//			tempoDaSessao = tempoDaSessao.plusMinutes(pautaDto.getTempoSessaoEmMinutos());
//
//		} else {
//
//			// Abrir sessao com tempo padrão (1 minuto)
//			tempoDaSessao = tempoDaSessao.plusMinutes(1L);
//		}
//
//		return tempoDaSessao;
//	}

//	private void validarDadosAbrirSessao(Optional<Pauta> pauta, BindingResult result) {
//
//		if (!pauta.isPresent()) {
//			result.addError(new ObjectError("Pauta", "Pauta inexistente."));
//		}
//
//		if (pauta.isPresent() && pauta.get().getEncerrada()) {
//			result.addError(new ObjectError("Pauta", "Pauta já encerrada."));
//		}
//
//	}

//	private void validarDadosIncluir(PautaDto pautaDto, BindingResult result) {
//
//		if (!(pautaDto.getNome() != null && pautaDto.getNome().isEmpty())
//				&& (pautaDto.getNome().length() < 5 || pautaDto.getNome().length() > 100)) {
//			result.addError(new ObjectError("Pauta", "Nome deve conter entre 5 e 100 caracteres."));
//		}
//
//		if (this.pautaService.buscarPeloNome(pautaDto.getNome()).isPresent()) {
//			result.addError(new ObjectError("Pauta", "Já existe Pauta com esse nome."));
//		}
//
//	}

//	private PautaDto converterParaDto(Pauta pauta) {
//
//		PautaDto pautaDto = new PautaDto();
//		pautaDto.setId(pauta.getId());
//		pautaDto.setNome(pauta.getNome());
//		pautaDto.setDescricao(pauta.getDescricao());
//
//		if (pauta.getValidaAte() != null) {
//			
//			//TODO Validar algoritmo
//			Long minutos = (pauta.getValidaAte().toEpochSecond(ZoneOffset.UTC) - 
//					LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) / 60;
//			pautaDto.setTempoSessaoEmMinutos(minutos);		
//		}
//
//		return pautaDto;
//	}

//	private Pauta converterParaPauta(PautaDto pautaDto) {
//
//		Pauta pauta = new Pauta();
//		pauta.setNome(pautaDto.getNome());
//		pauta.setDescricao(pautaDto.getDescricao());
//		pauta.setEncerrada(false);
//
//		return pauta;
//	}

}
