package com.rii.wp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rii.wp.form.LoginForm;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/weatherprediction", method = RequestMethod.GET)
	public String home(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("loginForm") LoginForm loginForm, BindingResult result) {
		return checkLogin(request, response, loginForm, result);
	}
	
	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public String callLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("loginForm") LoginForm loginForm, BindingResult result) {
		return checkLogin(request, response, loginForm, result);
	}

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String checkLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("loginForm") LoginForm loginForm, BindingResult result){
		
		HttpSession session = request.getSession(false);
	    if (session != null && session.getAttribute("is_logged_in") != null) {
	    	logger.info("session.getAttribute('is_logged_in') is {} " , session.getAttribute("is_logged_in"));
	    	boolean isLoggedIn = (Boolean) session.getAttribute("is_logged_in");
	    	logger.info("isLoggedIn {}",  isLoggedIn);
	    	if(isLoggedIn){
	    		return "cropmodel";
	    	}
	    }
	    return "login";
	}
	
}
