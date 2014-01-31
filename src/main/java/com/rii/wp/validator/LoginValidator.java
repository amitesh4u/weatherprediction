package com.rii.wp.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.rii.wp.form.LoginForm;
import com.rii.wp.form.RegistrationForm;

public class LoginValidator implements Validator{
	
	@Override
	public boolean supports(Class clazz) {
		//just validate the Customer instances
		return RegistrationForm.class.isAssignableFrom(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailId",
				"required.emailId", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"required.password", "Field name is required.");
			
		LoginForm loginForm = (LoginForm)target;
		
		if(!EmailValidator.validate(loginForm.getEmailId())){
			errors.rejectValue("emailId", "invalid.emailId");
		}
		
	}
	
}