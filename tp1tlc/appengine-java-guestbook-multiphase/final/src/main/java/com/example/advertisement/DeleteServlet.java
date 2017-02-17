package com.example.advertisement;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;



public class DeleteServlet  extends HttpServlet  {

	  // Process the http POST of the form
	@Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	   
		try {
			
	   System.err.println("Servlet Delete post");
	   // Find out who the user is.
	   String boardName=req.getParameter("boardName");
	   String filter=req.getParameter("filter");
	   // String delete=req.getParameter("delete");
	   // System.out.println("boardName: "+boardName+" filter: "+filter+" delete: "+delete);
	  
		   List<Advertisement> advertisements = ObjectifyService.ofy()
			          .load()
			          .type(Advertisement.class) 
			          .list();
		   
		   System.out.println("Database Loaded");
		      
		   for (Advertisement advertisement : advertisements) {
			   
			   if(advertisement.title.contains(filter)){   
						ObjectifyService.ofy().delete().entity(advertisement);
						System.out.println("remove : "+advertisement.title);
				  }
			  else{
				  if(filter == null || filter.isEmpty() || filter.contains("null")){ 
					  ObjectifyService.ofy().delete().entity(advertisement);
					  System.out.println("remove : "+advertisement.title);
				  }
			  }
		   }
		   resp.sendRedirect("/board.jsp?boardName=" + boardName+"&filter="+filter);
		} catch (Exception e) {
			System.err.println("plouf !!");
			// TODO: handle exception
		}

	  }
	
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	   
	   System.err.println("Servlet Delete get");
	   // Find out who the user is.
	   String boardName=req.getParameter("boardName");
	   String filter=req.getParameter("filter");
	   // String delete=req.getParameter("delete");
	   // System.out.println("boardName: "+boardName+" filter: "+filter+" delete: "+delete);
	  
		   List<Advertisement> advertisements = ObjectifyService.ofy()
			          .load()
			          .type(Advertisement.class) 
			          .list();
		   
		   System.out.println("Database Loaded");
		      
		   for (Advertisement advertisement : advertisements) {
			   if(advertisement.title.contains(filter)){   
					ObjectifyService.ofy().delete().entity(advertisement);
					System.out.println("remove : "+advertisement.title);
			  }
			  else{
				  if(filter == null || filter.isEmpty() || filter.contains("null")){ 
					  ObjectifyService.ofy().delete().entity(advertisement);
					  System.out.println("remove : "+advertisement.title);
				  }
			  }
		   }
		   resp.sendRedirect("/board.jsp?boardName=" + boardName+"&filter="+filter);

	  }
}

