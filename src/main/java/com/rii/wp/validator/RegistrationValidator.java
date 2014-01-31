package com.rii.wp.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.rii.wp.form.RegistrationForm;

public class RegistrationValidator implements Validator{
	
	@Override
	public boolean supports(Class clazz) {
		//just validate the Customer instances
		return RegistrationForm.class.isAssignableFrom(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailId",
				"required.emailId", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
				"required.firstName", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", 
				"required.lastName", "Field name is required.");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"required.password", "Field name is required.");
			
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
				"required.confirmPassword", "Field name is required.");
		
		RegistrationForm regForm = (RegistrationForm)target;
		
		if(regForm.getPassword().length() < 8){
			errors.rejectValue("password", "minlength.password");
		}else if(!(regForm.getPassword().equals(regForm.getConfirmPassword()))){
			errors.rejectValue("password", "notmatch.password");
		}
		
		if(!EmailValidator.validate(regForm.getEmailId())){
			errors.rejectValue("emailId", "invalid.emailId");
		}
	}
	
}