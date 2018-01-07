package com.blogapplication.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blogapplication.models.Article;
import com.blogapplication.models.User;
import com.blogapplication.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	// Brand Name
	@ModelAttribute
	public void header(Model model ){
		model.addAttribute("ApplicationName","Blog Application");
	}
	
	
	//redirect to registration page
	@RequestMapping("/registration")
	public ModelAndView newRegistration(Model model){
		model.addAttribute("User",new User());
		return new ModelAndView("registration");
		//return new ModelAndView("redirect:/");
	}
	
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView registerUser(@Valid @ModelAttribute("User") User user, BindingResult bindingResult, Model model,
										RedirectAttributes redirectAttributes){
		//check existing user
		boolean userExists = userService.checkExistingEmail(user);
		//if not exists
		if(bindingResult.hasErrors()){
			return new ModelAndView("registration");
		}
		else{
			String registrationMessage="";
			if(userExists){
				//save user
				userService.registerUser(user);
				registrationMessage = "Regisration Successfull.";
			}
			else{
				registrationMessage = "Email Already Exists!!!";
			}
			redirectAttributes.addFlashAttribute("RegistrationMessage",registrationMessage);
			return new ModelAndView("redirect:/registration");
		}		
	}
	
	
	//redirect to login page
	@RequestMapping("/loginpage")
	public ModelAndView loginPage(HttpServletRequest request){
		return new ModelAndView("login");
		//return new ModelAndView("redirect:/");
	}
	
	//login action
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView login(@RequestParam("email") String email,
			@RequestParam("password") String password, HttpServletRequest request,
			RedirectAttributes redirectAttribute, Model model ){
		User user =  userService.isValidUser(email, password);
		if(user!=null){
			//valid user
			HttpSession session = request.getSession(true);
			session.setAttribute("USER", user);
			session.setAttribute("LOGGEDIN", true);
			return new ModelAndView("redirect:/account");
		}
		else{
			//invalid user
			redirectAttribute.addFlashAttribute("LoginMessage","Invalid Email or Password.");
			return new ModelAndView("redirect:/loginpage");
		}
	}
	
	
	//logout
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		session.setAttribute("USER", null);
		session.setAttribute("LOGGEDIN", false);
		session.invalidate();
		return new ModelAndView("redirect:/loginpage");
	}
}





