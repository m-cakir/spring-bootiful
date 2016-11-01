package com.bootiful.web.validator;

import com.bootiful.web.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		
		return UserDTO.class.isAssignableFrom(clazz);
		
	}

	public void validate(Object target, Errors errors) {

		UserDTO form = (UserDTO) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "unameErrCode");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "pwdErrCode");

		// check username unique?
	}
}