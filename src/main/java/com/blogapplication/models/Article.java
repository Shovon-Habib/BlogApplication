package com.blogapplication.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Type;

@Entity
public class Article {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int articleId;
	private String articleCategory;
	@Type(type="text")
	@Lob
	@NotBlank
	private String articleTitle;
	@Type(type="text")
	@Lob
	@NotBlank
	private String articleBody;
	
	@Column(updatable =false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@ManyToOne
    @JoinColumn(name = "author_id")
	private User user;
	
	public Article() {
		//set the article creation time
		Date rightNow = new Date();
		this.creationDate = rightNow;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(String articleCategory) {
		this.articleCategory = articleCategory;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleBody() {
		return articleBody;
	}

	public void setArticleBody(String articleBody) {
		this.articleBody = articleBody;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Article [getArticleId()=" + getArticleId() + ", getArticleCategory()=" + getArticleCategory()
				+ ", getArticleTitle()=" + getArticleTitle() + ", getArticleBody()=" + getArticleBody()
				+ ", getCreationDate()=" + getCreationDate() + ", getUser()=" + getUser() + "]";
	}
	
	
}
