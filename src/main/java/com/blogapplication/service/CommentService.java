package com.blogapplication.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapplication.dao.CommentDao;
import com.blogapplication.models.Article;
import com.blogapplication.models.Comment;

@Service
public class CommentService {
	@Autowired
	private CommentDao commentDao;
	
	public void saveComment(Comment comment){
		try{
			commentDao.save(comment);
		}
		catch(Exception ex){
			System.out.println(ex);
		}
	}
	
	
	public ArrayList<Comment> retriveComments(Article article){
		try{
			return commentDao.findByArticleOrderByCommentTimeDesc(article);
		}
		catch(Exception ex){
			System.out.println(ex);
			return null;
		}
	}

}
