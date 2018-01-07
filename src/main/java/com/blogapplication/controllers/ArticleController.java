package com.blogapplication.controllers;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.blogapplication.models.Article;
import com.blogapplication.models.User;
import com.blogapplication.service.ArticleService;

@RestController
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	
	
	// Brand Name
	@ModelAttribute
	public void header(Model model ){
		model.addAttribute("ApplicationName","Blog Application");
	}
	
	
	//Redirect to account page
	@RequestMapping(value="/account")
	public ModelAndView accountPage(Model model, HttpServletRequest request){
		try{
			User user=null;
			if(LoginChecker.checkIfLoggedIn(request)){
				HttpSession session = request.getSession(false);
				user = (User)session.getAttribute("USER");
				model.addAttribute("CurrentUser",user);
				ArrayList<Article> articles = articleService.findAllArticleOfAuthor(user);
				model.addAttribute("Articles",articles);
				return new ModelAndView("account");
			}
			return new ModelAndView("redirect:/");
		}
		catch(Exception ex){
			System.out.println(ex);
			return new ModelAndView("redirect:/loginpage");
		}
	}
	
	
	//redirect to "New Article" page
	@RequestMapping("/insert")
	public ModelAndView newArticle(Model model,HttpServletRequest request){
		User user=null;
		if(LoginChecker.checkIfLoggedIn(request)){
			HttpSession session = request.getSession(false);
			user = (User)session.getAttribute("USER");
			model.addAttribute("CurrentUser",user);
			model.addAttribute("Article",new Article());
			return new ModelAndView("insert");
		}
		
		return new ModelAndView("redirect:/loginpage");
	}
	
		
	//Save an article
	@RequestMapping(value= "/save", method=RequestMethod.POST)
	public ModelAndView saveArticle(@Valid @ModelAttribute("Article") Article article, 
									BindingResult bindingResult, 
									HttpServletRequest request){
		try{
			User user;
			if(LoginChecker.checkIfLoggedIn(request)){
				HttpSession session = request.getSession(false);
				user = (User)session.getAttribute("USER");
				article.setUser(user);
				if(bindingResult.hasErrors()){
					return new ModelAndView("redirect:/insert");
				}
			}
			//System.out.println(article);
			articleService.addNewArticle(article);
			return new ModelAndView("redirect:/account");
		}
		catch(Exception ex){
			System.out.println(ex);
			return new ModelAndView("redirect:/");
		}
	}

	
	//go to edit_article page
	@RequestMapping("/edit_article/{id}")
	public ModelAndView editArticle(@PathVariable int id, Model model, 
											HttpServletRequest request){
		if(LoginChecker.checkIfLoggedIn(request)){
			HttpSession session = request.getSession(false);
			User user = (User)request.getSession(false).getAttribute("USER");
			if(session!=null){
				model.addAttribute("Article",articleService.findById(id));
				model.addAttribute("CurrentUser",user);
				return new ModelAndView("edit_article");
			}
		}
		return new ModelAndView("redirect:/loginpage");
	}
	
	
	// Edit Opearation
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public ModelAndView edit(Article article, HttpServletRequest request){
		if(LoginChecker.checkIfLoggedIn(request)){
			articleService.editArticle(article);
			//show a message
			return new ModelAndView("redirect:/account");
		}
		return new ModelAndView("redirect:/loginpage");
	}
	
	
	//Delete article
	@RequestMapping("/delete_article/{id}")
	public ModelAndView deleteArticle(@PathVariable int id, Model model,
											HttpServletRequest request){
		if(LoginChecker.checkIfLoggedIn(request)){
			articleService.deleteArticle(id);
			//show a message
			return new ModelAndView("redirect:/account");
		}
		return new ModelAndView("redirect:/loginpage");
	}

}
