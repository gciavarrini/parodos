package com.redhat.parodos.notification.common.controller;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import com.redhat.parodos.notification.common.controller.exceptions.ApiValidationError;
import lombok.Builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessageDTO handleConstraintViolation(ConstraintViolationException e, ServletWebRequest request) {
		// @formatter:off
        return new ErrorMessageDTO.ErrorMessageDTOBuilder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .path(request.getRequest().getRequestURI())
                .message(e.getMessage())
                .description(StringUtils.collectionToCommaDelimitedString(e.getConstraintViolations().stream()
                        .map(cv -> ApiValidationError.from(cv, messageSource, request.getLocale())).toList()))
                .build();
        // @formatter:on
	}

	@Builder
	record ErrorMessageDTO(int status, Date timestamp, String path, String message, String description) {
	}

}
