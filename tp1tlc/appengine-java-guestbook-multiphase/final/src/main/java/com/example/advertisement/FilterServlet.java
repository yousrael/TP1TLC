package com.example.advertisement;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;



public class FilterServlet  extends HttpServlet  {
	  // Process the http POST of the form
	  @Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	   
		  System.err.println("servlet Filter");
		  // Find out who the user is.
		  String boardName=req.getParameter("boardName");
		  String filter=req.getParameter("filter");
		  String priceMin=req.getParameter("priceMin");
		  String priceMax=req.getParameter("priceMax");
		  
		 
		  resp.sendRedirect("/board.jsp?boardName=" + boardName+"&filter="+filter+"&priceMin="+priceMin+"&priceMax="+priceMax);
		  //resp.sendRedirect("/board.jsp?boardName=" + boardName+"&filter="+filter);
	  }
	  
}

