package com.example.advertisement;


import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class FilterServlet  extends HttpServlet  {


	  // Process the http POST of the form
	  @Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	   
  System.err.println("servlet Filter");
	      // Find out who the user is.
	    String boardName=req.getParameter("boardName");
	   String filter=req.getParameter("filter");
	  // String priceMin=req.getParameter("price-min");
	  // String priceMax=req.getParameter("price-max");
	   
		   resp.sendRedirect("/board.jsp?boardName=" + boardName+"&filter="+filter);

	//[END all]

	  }
}

