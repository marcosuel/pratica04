package com.company.pratica04.config.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.company.pratica04.exception.DomainException;
import com.company.pratica04.exception.ExceptionResponse;
import com.company.pratica04.exception.ExceptionResponse.ExceptionResponseBuilder;


@RestControllerAdvice
public class ExceptionHandlerController {

	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FormFieldErrorDto> validationHandler(MethodArgumentNotValidException exception) {
		
		List<FormFieldErrorDto> dto = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(e -> {
			String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			dto.add(new FormFieldErrorDto(e.getField(), message));
		});
		
		return dto;
	}
	
	@ExceptionHandler(value = DomainException.class)
	public ResponseEntity<ExceptionResponse> domainExceptionHandler(DomainException ex){
		ExceptionResponse response = ExceptionResponseBuilder.create()
				.withStatus(ex.getStatus())
				.withError(ex.getStatus().getReasonPhrase())
				.withMessage(ex.getMessage())
				.build();
		return new ResponseEntity<ExceptionResponse>(response, ex.getStatus());
	}
	
}
