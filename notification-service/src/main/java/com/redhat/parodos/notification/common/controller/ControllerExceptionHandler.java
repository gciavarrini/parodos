package com.redhat.parodos.notification.common.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import com.redhat.parodos.notification.controller.advice.ApiValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public @ResponseBody Map<String, Object> handleConstraintViolation(ConstraintViolationException e,
			ServletWebRequest request) {
		// emulate Spring DefaultErrorAttributes
		final Map<String, Object> result = new LinkedHashMap<>();
		result.put("timestamp", new Date());
		result.put("path", request.getRequest().getRequestURI());
		result.put("status", HttpStatus.BAD_REQUEST.value());
		result.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
		result.put("message", e.getMessage());
		result.put("errors", e.getConstraintViolations().stream()
				.map(cv -> ApiValidationError.from(cv, messageSource, request.getLocale())));
		return result;
	}

}
