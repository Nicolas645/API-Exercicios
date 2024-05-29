package org.serratec.ecommerce.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> erros = new ArrayList<>();
		for (FieldError f : ex.getBindingResult().getFieldErrors()) {
			erros.add(f.getField() + ":" + f.getDefaultMessage());
		}

		ErroResposta er = new ErroResposta(status.value(), "Existem campos inválidos", LocalDateTime.now(), erros);

		return super.handleExceptionInternal(ex, er, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ErroResposta er = new ErroResposta(status.value(), "Campos inválidos foram inseridos, favor verificar",
				LocalDateTime.now(), null);
		return super.handleExceptionInternal(ex, er, headers, status, request);
	}

	@ExceptionHandler(ConfirmaSenhaException.class)
	protected ResponseEntity<Object> handleEmailException(ConfirmaSenhaException e) {
		List<String> erros = new ArrayList<>();
		erros.add(e.getMessage());
		HttpStatus http = HttpStatus.BAD_REQUEST;
		ErroResposta er = new ErroResposta(http.value(), e.getLocalizedMessage(), LocalDateTime.now(), null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<Object> handleEmailException(ResourceNotFoundException ex) {
		List<String> erros = new ArrayList<>();
		erros.add(ex.getMessage());
		HttpStatus http = HttpStatus.BAD_REQUEST;
		ErroResposta er = new ErroResposta(http.value(), ex.getLocalizedMessage(), LocalDateTime.now(), null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
	}

	@ExceptionHandler(StatusPedidoInvalidoException.class)
	public ResponseEntity<Object> handleStatusPedidoInvalidoException(StatusPedidoInvalidoException ex,
			WebRequest request) {
		String errorMessage = ex.getMessage();
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		String mensagemDeErro = "Ocorreu um erro ao processar a sua solicitação. Por favor, verifique os dados informados e tente novamente.";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemDeErro);
	}

	@ExceptionHandler(EmailViolationException.class)
    public ResponseEntity<String> handleEmailViolationException(EmailViolationException ex) {
        String mensagemDeErro = "Erro ao processar e-mail: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemDeErro);
    }

	@ExceptionHandler(CPFViolationException.class)
    public ResponseEntity<String> handleCPFViolationException(CPFViolationException ex) {
        String mensagemDeErro = "Erro ao processar CPF: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemDeErro);
    }
}