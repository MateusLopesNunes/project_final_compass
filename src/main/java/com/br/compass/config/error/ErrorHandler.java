package com.br.compass.config.error;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.br.compass.dto.ExceptionResponseDto;
import com.br.compass.exception.ObjectNotFoundException;

@RestControllerAdvice
public class ErrorHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<ExceptionResponseDto> exceptionObjectNotFound(ObjectNotFoundException exception) {
		return new ResponseEntity<>(new ExceptionResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()),
				HttpStatus.NOT_FOUND);
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ExceptionResponseDto> exceptionBodyBadRequest(MethodArgumentNotValidException exception) {
		List<ExceptionResponseDto> dto = new ArrayList<>();
		List<FieldError> fieldError = exception.getBindingResult().getFieldErrors();
		
		fieldError.forEach(x -> {
			String messageError = messageSource.getMessage(x, LocaleContextHolder.getLocale());
			ExceptionResponseDto erro = new ExceptionResponseDto(HttpStatus.BAD_REQUEST.value(), "field " + x.getField() + " " + messageError);
			dto.add(erro);
		});
		
		return dto;
	}
	
}
