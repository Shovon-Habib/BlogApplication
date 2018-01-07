package com.blogapplication.models;

import java.security.MessageDigest;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.DatatypeConverter;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int authorId;
	
	@NotBlank
	@Column(unique=true)
	private String email;
	@NotBlank
	@Size(min=6, max=100)
	private String name;
	@NotBlank
	//@Size(min=6, max=32)
	//@PrePersist
	@Column(length = 3000) 
	private String password;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Article> articles;
	
	public User() {
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [getAuthorId()=" + getAuthorId() + ", getEmail()=" + getEmail() + ", getName()=" + getName()
				+ ", getPassword()=" + getPassword() + "]";
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}
		
}
