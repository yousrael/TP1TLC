package com.example.advertisement;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;


//Filter Servlet
public class FilterServlet  extends HttpServlet  {
	static boolean debug = false;
	  // Process the http POST of the form
	@Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	   
		if(debug)System.err.println("servlet Filter");
		  // get the parameters from the request.
		  String boardName=req.getParameter("boardName");
		  String filter=req.getParameter("filter");
		  String priceMin=req.getParameter("priceMin");
		  String priceMax=req.getParameter("priceMax");
		  String dateMin=req.getParameter("dateMin");
		  String dateMax=req.getParameter("dateMax");
		  
		 
		  resp.sendRedirect("/board.jsp?boardName=" + boardName+"&filter="+filter+"&priceMin="+priceMin+"&priceMax="+priceMax+"&dateMin="+dateMin+"&dateMax="+dateMax);
		 
	  }
	 // Process the http GET of the form
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	   
		if(debug)System.err.println("servlet Filter");
		  // get the parameters from the request.
		  String boardName=req.getParameter("boardName");
		  String filter=req.getParameter("filter");
		  String priceMin=req.getParameter("priceMin");
		  String priceMax=req.getParameter("priceMax");
		  String dateMin=req.getParameter("dateMin");
		  String dateMax=req.getParameter("dateMax");
		  
		 
		  resp.sendRedirect("/board.jsp?boardName=" + boardName+"&filter="+filter+"&priceMin="+priceMin+"&priceMax="+priceMax+"&dateMin="+dateMin+"&dateMax="+dateMax);
	}
	  
}

