/**
 * 
 */
package com.rii.wp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rii.wp.dao.AuthenticationDAO;
import com.rii.wp.exception.WPDataException;
import com.rii.wp.form.LoginForm;
import com.rii.wp.form.RegistrationForm;
import com.rii.wp.model.User;
import com.rii.wp.validator.LoginValidator;
import com.rii.wp.validator.RegistrationValidator;

/**
 * @author I
 * 
 */
@Controller
public class AuthenticationController {

	private static final Logger logger = LoggerFactory
			.getLogger(AuthenticationController.class);
	//private static final int SESSION_TIME_OUT_SEC = 60;// 86400; // One Day

	@Autowired
	AuthenticationDAO authenticationDAO;
	
	RegistrationValidator regValidator;
	LoginValidator loginValidator;
	
	@Autowired
	public AuthenticationController(RegistrationValidator userValidator, LoginValidator loginValidator){
		this.regValidator = userValidator;
		this.loginValidator = loginValidator;
	}

//	/**
//	 * Here in the service method, we have passed a blank
//	 * LoginForm object in the ModelAndView object with name "command" because
//	 * the spring framework expects an object with name "command" if you are
//	 * using <form:form> tags in your JSP file. So when method is
//	 * called it returns login.jsp view.
//	 */
	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public String doLogin(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("loginForm") LoginForm loginForm, BindingResult result) {

		HttpSession session = request.getSession(false);
		String page=null;
		if (session == null || session.getAttribute("is_logged_in") == null
				|| !(Boolean) session.getAttribute("is_logged_in")) {
			
			loginValidator.validate(loginForm, result);
			
			if(result.hasErrors()){
				page = "login";
			}else{
				String emailId = loginForm.getEmailId();
				String password = loginForm.getPassword();
				logger.info("emailId :{}", emailId);
				//logger.info("Pwd: {}", password); //TODO Remove
	
				User user;
				try {
					user = authenticationDAO.getUserDetails(emailId, password);
					if(user == null){
						logger.error("User not found.");
						result.rejectValue("general", "notfound.user");
						page = "login";
					}else{
						logger.info("First name: {}", user.getFirstName());
						logger.info("Last name: {}", user.getLastName());
						session = request.getSession();
						session.setAttribute("is_logged_in", true);
						session.setAttribute("emailId", emailId);
						session.setAttribute("userId", user.getUserId());
						session.setAttribute("fName", user.getFirstName());
						session.setAttribute("lName", user.getLastName());
					//	session.setMaxInactiveInterval(SESSION_TIME_OUT_SEC);	
						page = "cropmodel";
					}
				} catch (WPDataException e) {
					logger.error("Error fetching User details: {} , {}", e.getMessage(), e.getCause());
					result.rejectValue("general", "error.login");
					page = "login";
				}
			}
		}else{
			page = "cropmodel";
			logger.info("session.getAttribute('is_logged_in') is {} ",
					session.getAttribute("is_logged_in"));
		}
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
//				DateFormat.LONG, request.getLocale());
//		
//		String formattedDate = dateFormat.format(date);
//		//session.setAttribute("serverTime", formattedDate);
//		
//		request.setAttribute("serverTime", formattedDate);
		
		// model.addAttribute("userName", userName);

		return page;
	}

	@RequestMapping(value = "/Logout", method = RequestMethod.GET)
	public String doLogout(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("loginForm") LoginForm loginForm, BindingResult result) {
		String page = "cropmodel";
		HttpSession session = request.getSession(false);
		if(session != null){
			session.invalidate();
			page = "login";
		}
		return page;
	}

	@RequestMapping(value = "/RegistrationPage", method = RequestMethod.GET)
	public String callRegistration(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("registrationForm") RegistrationForm registrationForm,
			BindingResult result) {
		String page = "cropmodel";
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("is_logged_in") == null
				|| !(Boolean) session.getAttribute("is_logged_in")) {
			page = "register";
		}
		return page;
	}

	@RequestMapping(value = "/Registration", method = RequestMethod.POST)
	public String doRegistration(
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("registrationForm") RegistrationForm registrationForm,
			BindingResult result) {
		
		String page=null;	
		regValidator.validate(registrationForm, result);
		
		if (result.hasErrors()) {
			logger.error("Validation errors in Registration");
			//if validator failed
			page = "register";
		}else{
			
			String emailId = registrationForm.getEmailId();
			logger.info("EmailId :{}", emailId);
			String firstName = registrationForm.getFirstName();
			logger.info("First Name: {}", firstName);
			String lastName = registrationForm.getLastName();
			logger.info("Last Name: {}", lastName);
			String password = registrationForm.getPassword();
			//logger.info("Pwd: {}", password); //TODO Remove
	
			User user = new User();
			user.setEmailId(emailId);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setPassword(password);
		
			try {
				int status = authenticationDAO.setUserDetails(user);
				logger.info("Status: {}", status);
				switch (status) {
					case 1: result.rejectValue("general", "emailId.duplicate");
					break;
					case 2: result.rejectValue("general", "error.registration");
					break;
					case 0:
						request.setAttribute("status", "t");
						page = "registrationStatus";
				}	
			} catch (WPDataException e) {
				logger.error("WPDataException in Registration {}, {}", e.getMessage(), e.getCause());
				result.rejectValue("general", "error.registration");
			} catch (Exception e){
				logger.error("Exception in Registration {}, {}", e.getMessage(), e.getCause());
				result.rejectValue("general", "error.registration");
			}
			if (result.hasErrors()) {
				//if validator failed
				page = "register";
			}
		}
		return page;
	}
	
	@RequestMapping(value = "/Registration", method = RequestMethod.GET)
	public String InvalidRegistration(
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute("registrationForm") RegistrationForm registrationForm,
			BindingResult result) {
		return callRegistration(request, response, registrationForm, result);
	}

	/**
	 * @param authenticationDAO
	 *            the authenticationDAO to set
	 */
	public void setAuthenticationDAO(AuthenticationDAO authenticationDAO) {
		this.authenticationDAO = authenticationDAO;
	}

}
