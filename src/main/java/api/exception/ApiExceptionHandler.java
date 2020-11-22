package api.exception;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import api.response.Response;
import api.services.impl.exceptions.ApiExternalNaoPermitiuVotoException;
import api.services.impl.exceptions.AssociadoJaVotouPautaException;
import api.services.impl.exceptions.PautaNaoAbertaOuJaFechadaException;
import api.services.impl.exceptions.VotoNaoAceitoException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String mensagemUsuario = "Argumento não válido";
		log.error("{} {}", mensagemUsuario, ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagemUsuario = "Argumento não válido";
		log.error("{} {}", mensagemUsuario, ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {

		String mensagemUsuario = "Operação não permitida.";
		log.error("{} {}", mensagemUsuario, ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> constraintViolationException(Exception ex, WebRequest request) {

		String mensagemUsuario = "Restrição não cumprida";
		log.error("{} {}", mensagemUsuario, ex.getCause() != null ? ex.getCause().toString() : ex.toString());

		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}
	
	@ExceptionHandler({ EmptyResultDataAccessException.class, Exception.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex, WebRequest request) {

		String mensagemUsuario = "Recurso não encontrado.";
		log.error("{} {}", mensagemUsuario, ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}

	@ExceptionHandler({ PautaNaoAbertaOuJaFechadaException.class })
	public ResponseEntity<Object> handlePautaNaoAbertaOuJaFechada(PautaNaoAbertaOuJaFechadaException ex,
			WebRequest request) {

		String mensagemUsuario = "Pauta não aberta ou já fechada para votação";
		log.error("{} {}", mensagemUsuario, ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}

	@ExceptionHandler({ AssociadoJaVotouPautaException.class })
	public ResponseEntity<Object> handleAssociadoJaVotouPauta(AssociadoJaVotouPautaException ex, WebRequest request) {

		String mensagemUsuario = "Associado já votou nessa Pauta";
		log.error("{} {}", mensagemUsuario, ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}

	@ExceptionHandler({ ApiExternalNaoPermitiuVotoException.class })
	public ResponseEntity<Object> handleAssociadoJaVotouPauta(ApiExternalNaoPermitiuVotoException ex,
			WebRequest request) {

		String mensagemUsuario = "API externa não permitiu voto do Associado";
		log.error("{} {}", mensagemUsuario, ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}

	@ExceptionHandler({ VotoNaoAceitoException.class })
	public ResponseEntity<Object> handleAssociadoJaVotouPauta(VotoNaoAceitoException ex, WebRequest request) {

		String mensagemUsuario = "O voto deve ser expresso com as palavras 'SIM' ou 'Não'.";
		log.error("{} {}", mensagemUsuario, ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}
	
	private Response<Object> gerarResposta(String mensagemUsuario){
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario));
		Response<Object> response = new Response<Object>();
		response.setErrors(erros);
		return response;
	}
	

	public static class Erro {
		public Erro(String mensagemUsuario) {
			this.mensagemUsuario = mensagemUsuario;
		}

		private String mensagemUsuario;

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}
	}
}
