package com.company.pratica04.config.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.company.pratica04.exception.DomainException;
import com.company.pratica04.exception.ExceptionResponse;
import com.company.pratica04.exception.FormFieldError;
import com.company.pratica04.exception.FormFieldResponse;
import com.company.pratica04.exception.ExceptionResponse.ExceptionResponseBuilder;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handleAnyException(Exception e, WebRequest request) {
		ExceptionResponse response = ExceptionResponseBuilder.create()
			.withError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
			.withMessage(e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "Ocorreu um erro inesperado.")
			.withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
			.withPath(this.extractPath(request))
			.build();
		//Arrays.asList(e.getStackTrace()).forEach(line -> System.out.println(line.toString()));
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FormFieldError> erros = new ArrayList<>();
		
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

		fieldErrors.forEach(e -> {
			String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			String campo = e.getField();
			erros.add(new FormFieldError(campo, message));
		});

		FormFieldResponse response = new FormFieldResponse(status.getReasonPhrase(), erros, this.extractPath(request));
		return new ResponseEntity<>(response, headers, status);
	}

	@ExceptionHandler(value = DomainException.class)
	public ResponseEntity<Object> handleDomainException(DomainException ex, WebRequest request) {
		ExceptionResponse response = ExceptionResponseBuilder.create()
				.withStatus(ex.getStatus())
				.withError(ex.getStatus().getReasonPhrase())
				.withMessage(ex.getMessage())
				.withPath(this.extractPath(request))
				.build();
		return new ResponseEntity<>(response, ex.getStatus());
	}
	
	private String extractPath(WebRequest request) {
		return request.getDescription(false).split("=")[1];
	}

}
