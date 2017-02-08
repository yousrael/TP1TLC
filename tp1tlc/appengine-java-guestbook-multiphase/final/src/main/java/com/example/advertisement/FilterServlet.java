package com.example.advertisement;


import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class FilterServlet  extends HttpServlet  {


	  // Process the http POST of the form
	  @Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	   

	      // Find out who the user is.
	    String boardName=req.getParameter("boardName");
	   String filter=req.getParameter("filter");
	   String delete=req.getParameter("delete");
	   System.out.println("boardName: "+boardName+" filter: "+filter+" delete: "+delete);
	   if( delete != null && !delete.isEmpty() && delete.contains("TRUE")){//on demande le delete

		   List<Advertisement> advertisements = ObjectifyService.ofy()
			          .load()
			          .type(Advertisement.class) // We want only Advertisements
			          .list();
		   for (Advertisement advertisement : advertisements) {
			   if(filter!=null && !filter.contains("null")&& !filter.contains(""))System.out.println("do \""+advertisement.title+"\"  contain \""+filter+"\" ?");
			   if(filter==null ||filter.contains("null")||filter.contains("") || advertisement.title.contains(filter)){ // Si tout ou si c'est un advertissement filter
					ObjectifyService.ofy().delete().entities(advertisement).now();
					System.out.println("remove : "+advertisement.title);
			   }
		   }
			   
		   resp.sendRedirect("/board.jsp?boardName=" + boardName);
	   }
	   else // on demande une recherche
		   resp.sendRedirect("/board.jsp?boardName=" + boardName+"&filter="+filter);

	//[END all]

	  }
}

