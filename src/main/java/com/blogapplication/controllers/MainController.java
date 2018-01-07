package com.blogapplication.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blogapplication.dao.ArticleDao;
import com.blogapplication.models.Article;
import com.blogapplication.models.Comment;
import com.blogapplication.models.User;
import com.blogapplication.service.ArticleService;
import com.blogapplication.service.CommentService;

@RestController
public class MainController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CommentService commentService;

	
	// Brand Name
	@ModelAttribute
	public void header(Model model ){
		model.addAttribute("ApplicationName","Blog Application");
	}
	
	
	@RequestMapping("/")
	public ModelAndView redirectToHomePage(Model model,@RequestParam(defaultValue="0") int page){
		model.addAttribute("Articles", articleService.retriveAll(page, 5));
		return new ModelAndView("index");
	}
	
		
	// To get details of an article
	@RequestMapping("/read_more/{id}")
	public ModelAndView readDetails(@PathVariable int id, Model model){
		// fetch specific article by Id
		Article article = articleService.findById(id);
		ArrayList<Comment> previousComments = commentService.retriveComments(article);
		model.addAttribute("PreviousComments",previousComments);
		model.addAttribute("Comment",new Comment(article));
		model.addAttribute("Article",article);
		
		return new ModelAndView("details");
	}
	
	
	//Save comment in databse
	@RequestMapping(value="/save_comment", method=RequestMethod.POST)
	public ModelAndView saveComments(@ModelAttribute("Comment") Comment comment ){
		System.out.println(comment);
		commentService.saveComment(comment);
		return new ModelAndView("redirect:/read_more/"+comment.getArticle().getArticleId());
	}
}
