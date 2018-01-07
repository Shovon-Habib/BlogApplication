package com.blogapplication.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blogapplication.dao.ArticleDao;
import com.blogapplication.models.Article;
import com.blogapplication.models.User;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;
	
	public void addNewArticle(Article article){
		try{
			System.out.println(article);
			articleDao.save(article);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public ArrayList<Article> findAllArticleOfAuthor(User user){
		try{
			ArrayList<Article> articles = articleDao.findByUserOrderByCreationDateDesc(user);
			return articles;
		}
		catch(Exception ex){
			System.out.println(ex);
			return null;
		}
	}
	
	public boolean editArticle(Article article){
		try{
			Article fetchedArticle = articleDao.getOne(article.getArticleId());
			fetchedArticle.setArticleCategory(article.getArticleCategory());
			fetchedArticle.setArticleTitle(article.getArticleTitle());
			fetchedArticle.setArticleBody(article.getArticleBody());
			if(articleDao.save(fetchedArticle)!=null)
				return true;
			else
				return false;
		}
		catch(Exception ex){
			System.out.println(ex);
			return false;
		}
	}
	
	public void deleteArticle(int id){
		try{
			articleDao.deleteById(id);
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	}
	
	
	//FOR HOME PAGE
	public Page<Article> retriveAll(int page, int numberOfRows){
		try{
			return articleDao.findAllByOrderByCreationDateDesc(new PageRequest(page, numberOfRows));
		}
		catch(Exception ex){
			System.out.println(ex);
			return null;
		}
	}
	
	/*@SuppressWarnings("unchecked")
	public ArrayList<Article> retrieveAll(PageRequest pageRequest){
		try{
			return (ArrayList<Article>)articleDao.findAll(new PageRequest(page, 4));
		}
		catch(Exception ex){
			System.out.println(ex);
			return null;
		}
	}*/
	
	public Article findById(int id){
		try{
			Article article = articleDao.findById(id).get();
			System.out.println(article);
			return article;
		}
		catch(Exception ex){
			System.out.println(ex);
			return null;
		}
	}
}
