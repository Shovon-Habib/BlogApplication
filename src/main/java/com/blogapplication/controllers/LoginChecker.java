package com.blogapplication.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginChecker {
	
	static public boolean checkIfLoggedIn(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		//check if logged in or not
		if(session!=null){
			boolean isLoggedIn = (Boolean)session.getAttribute("LOGGEDIN");
			if(isLoggedIn){
				return true;//logged in
			}
			else{
				return false;
			}
		}
		return false;
	}

}
