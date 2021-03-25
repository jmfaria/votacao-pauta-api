package api.exception;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolationException;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String mensagemUsuario = "Argumento não válido. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagemUsuario = "Argumento não válido. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {

		String mensagemUsuario = "Operação não permitida. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> constraintViolationException(Exception ex, WebRequest request) {

		String mensagemUsuario = "Restrição não cumprida. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);

		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}
	
	@ExceptionHandler({ EmptyResultDataAccessException.class, Exception.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex, WebRequest request) {

		String mensagemUsuario = "Recurso não encontrado. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}

	@ExceptionHandler({ PautaNaoAbertaOuJaFechadaException.class })
	public ResponseEntity<Object> handlePautaNaoAbertaOuJaFechadaException(PautaNaoAbertaOuJaFechadaException ex,
			WebRequest request) {

		String mensagemUsuario = "Pauta não aberta ou já fechada para votação. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}
	
	@ExceptionHandler({ PautaJaCadastradaException.class })
	public ResponseEntity<Object> handlePautaNomeJaCadastradaException(PautaJaCadastradaException ex,
			WebRequest request) {

		String mensagemUsuario = "Pauta com esse nome já foi cadastrada. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}
	
	@ExceptionHandler({ PautaNomeInvalidoException.class })
	public ResponseEntity<Object> handlePautaNomeInvalidoException(PautaNomeInvalidoException ex,
			WebRequest request) {

		String mensagemUsuario = "Nome deve conter entre 5 e 100 caracteres. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}
	
	@ExceptionHandler({ PautaInexistenteException.class })
	public ResponseEntity<Object> handlePautaInexistenteException(PautaInexistenteException ex,
			WebRequest request) {

		String mensagemUsuario = "Pauta inexistente. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}
	
	@ExceptionHandler({ PautaJaEncerradaException.class })
	public ResponseEntity<Object> handlePautaJaEncerradaException(PautaJaEncerradaException ex,
			WebRequest request) {

		String mensagemUsuario = "Pauta já encerrada. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}
	
	@ExceptionHandler({ ResultadoVotacaoNaoConcluidoException.class })
	public ResponseEntity<Object> handleResultadoVotacaoNaoConcluidoException(ResultadoVotacaoNaoConcluidoException ex,
			WebRequest request) {

		String mensagemUsuario = "Resultado não concluido, a Pauta está aberta para Votação. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}


	@ExceptionHandler({ AssociadoJaVotouPautaException.class })
	public ResponseEntity<Object> handleAssociadoJaVotouPautaException(AssociadoJaVotouPautaException ex, WebRequest request) {

		String mensagemUsuario = "Associado já votou nessa Pauta. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}
	
	@ExceptionHandler({ AssociadoCpfJaCadastradoException.class })
	public ResponseEntity<Object> handleAssociadoCpfJaCadastradoException(AssociadoCpfJaCadastradoException ex, WebRequest request) {

		String mensagemUsuario = "Associado com esse CPF já foi cadastrado. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}
	
	@ExceptionHandler({ CpfInvalidoException.class })
	public ResponseEntity<Object> handleAssociadoCpfInvalidoException(CpfInvalidoException ex, WebRequest request) {

		String mensagemUsuario = "Associado com CPF inválido. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}
	
	@ExceptionHandler({ AssociadoInexistenteException.class })
	public ResponseEntity<Object> handleAssociadoInexistenteParaCpfException(AssociadoInexistenteException ex, WebRequest request) {

		String mensagemUsuario = "Associado inexistente. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}

	@ExceptionHandler({ ApiExternalNaoPermitiuVotoException.class })
	public ResponseEntity<Object> handleAssociadoJaVotouPautaException(ApiExternalNaoPermitiuVotoException ex,
			WebRequest request) {

		String mensagemUsuario = "API externa não permitiu voto do Associado. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}

	@ExceptionHandler({ VotoNaoAceitoException.class })
	public ResponseEntity<Object> handleAssociadoJaVotouPautaException(VotoNaoAceitoException ex, WebRequest request) {

		String mensagemUsuario = "O voto deve ser expresso com as palavras 'SIM' ou 'Não'. " + (ex.getCause() != null ? ex.getCause().toString() : ex.toString());
		log.error(mensagemUsuario);
		return ResponseEntity.badRequest().body(gerarResposta(mensagemUsuario));
	}
	
	private Response<Object> gerarResposta(String mensagemUsuario){
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario));
		Response<Object> response = new Response<>();
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
