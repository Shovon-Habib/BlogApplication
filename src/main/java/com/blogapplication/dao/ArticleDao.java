package com.blogapplication.dao;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.blogapplication.models.Article;
import com.blogapplication.models.User;


@Transactional
@Repository
public interface ArticleDao extends JpaRepository<Article,Integer>{
	ArrayList<Article> findByUserOrderByCreationDateDesc(User user);
	Page<Article> findAllByOrderByCreationDateDesc(Pageable pageRequest);
	
}



