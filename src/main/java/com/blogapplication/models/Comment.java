package com.blogapplication.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Type;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@NotBlank
	@Type(type="text")
	@Lob
	private String userName;
	@NotBlank
	@Type(type="text")
	@Lob
	private String commentBody;
	@Column(updatable =false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date commentTime;
	@ManyToOne
    @JoinColumn(name = "article_id")
	private Article article;
	
	public Comment(){
		Date rightNow = new Date();
		this.commentTime = rightNow;
	}
	
	public Comment(Article article) {
		this.article = article;
		//comment time
		Date rightNow = new Date();
		this.commentTime = rightNow;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCommentBody() {
		return commentBody;
	}

	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@Override
	public String toString() {
		return "Comment [getId()=" + getId() + ", getUserName()=" + getUserName() + ", getCommentBody()="
				+ getCommentBody() + ", getCommentTime()=" + getCommentTime() + ", getArticle()=" + getArticle() + "]";
	}
	
}
