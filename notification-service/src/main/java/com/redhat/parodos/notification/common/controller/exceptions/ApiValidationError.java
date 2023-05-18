package com.redhat.parodos.notification.common.controller.exceptions;

import java.beans.Introspector;
import java.util.Locale;

import javax.validation.ConstraintViolation;

import lombok.*;

import org.springframework.context.MessageSource;

@Getter
@ToString
public class ApiValidationError {

	String defaultMessage;

	String objectName;

	String field;

	Object rejectedValue;

	String code;

	public static ApiValidationError from(ConstraintViolation<?> violation, MessageSource msgSrc, Locale locale) {
		ApiValidationError result = new ApiValidationError();
		result.defaultMessage = msgSrc.getMessage(violation.getMessageTemplate(),
				new Object[] { violation.getLeafBean().getClass().getSimpleName(),
						violation.getPropertyPath().toString(), violation.getInvalidValue() },
				violation.getMessage(), locale);
		result.objectName = Introspector.decapitalize(violation.getRootBean().getClass().getSimpleName());
		result.field = String.valueOf(violation.getPropertyPath());
		result.rejectedValue = violation.getInvalidValue();
		result.code = violation.getMessageTemplate();
		return result;
	}

}
