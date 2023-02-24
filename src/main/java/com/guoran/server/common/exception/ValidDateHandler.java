package com.guoran.server.common.exception;


import com.guoran.server.common.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Wei
 */
@RestControllerAdvice
public class ValidDateHandler extends ResponseEntityExceptionHandler {
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		String msg = "";
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			msg += fieldError.getDefaultMessage();
			break;
		}
		return new ResponseEntity<>(Result.error(msg.intern()), HttpStatus.OK);
	}
}
