package com.blogapplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.blogapplication.models.User;

@Transactional
@Repository
public interface UserDao extends JpaRepository<User,Integer>{
	public User findByEmail(String email);
}