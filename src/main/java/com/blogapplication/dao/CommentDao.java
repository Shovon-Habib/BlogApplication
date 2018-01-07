package com.blogapplication.dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.blogapplication.models.Article;
import com.blogapplication.models.Comment;

@Transactional
@Repository
public interface CommentDao extends JpaRepository<Comment, Integer>{
	ArrayList<Comment> findByArticleOrderByCommentTimeDesc(Article article);
}
