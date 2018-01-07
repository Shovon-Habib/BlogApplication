package com.blogapplication.service;

import java.security.MessageDigest;

import javax.persistence.PrePersist;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.blogapplication.dao.UserDao;
import com.blogapplication.models.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public boolean checkExistingEmail(User user){
		try{
			//find if user email already exists
			User existingUser = userDao.findByEmail(user.getEmail());
			if(existingUser==null)
				return true; //not exists
			else
				return false; //exists
		}
		catch(Exception ex){
			System.out.println(ex);
			return false;
		}
	}
	
	public boolean registerUser(User user){
		try{
			//Generate Hash of Password and Set to model
			user.setPassword(generateHash(user.getPassword()));
			userDao.save(user);
			return true;
		}
		catch(Exception ex){
			System.out.println(ex);
			return false;
		}
	}
	
	
	public User isValidUser(String email, String password){
		try{
			User validUser = userDao.findByEmail(email);
			if(validUser!=null){ //if corresponding user found
				//generate hash password
				password = generateHash(password);
				//match password
				if(password.equals(validUser.getPassword())){
					return validUser;
				}
				else{
					return null;
				}
			}
			else{
				return null;
			}
		}
		catch(Exception ex){
			System.out.println(ex);
			return null;
		}
	}
	
	
	// method to generate hash password
	private String generateHash(String inputPassword){
		try{
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.update(inputPassword.getBytes("UTF-8"),0,inputPassword.length());
			return DatatypeConverter.printHexBinary(messageDigest.digest());
		}
		catch(Exception ex){
			System.out.println(ex);
			return null;
		}
	}
}
