package com.example.advertisement;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.googlecode.objectify.ObjectifyService;


//Delete Servlet
public class DeleteServlet  extends HttpServlet  {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Process the http POST of the form
	@Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	   
			
	   System.err.println("Servlet Delete POST");
	   // get the parameters from the request.
	   String boardName=req.getParameter("boardName");
	   String filter=req.getParameter("filter");
	   double priceMin=Double.parseDouble(req.getParameter("priceMin"));
	   double priceMax=Double.parseDouble(req.getParameter("priceMax"));
	  
	   
	   try {
		   SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy"); 
	       Date dateMin = formatter.parse(req.getParameter("dateMax")); 
	       Date dateMax = formatter.parse(req.getParameter("dateMin")); 
		   List<Advertisement> advertisements = ObjectifyService.ofy()
			          .load()
			          .type(Advertisement.class)
			          .filter("price >", priceMin).filter("price <", priceMax)
			          .list();
		  
		   System.out.println("Database Loaded");
		      
		   for (Advertisement advertisement : advertisements) {
			   System.out.println("looking for delete at : "+advertisement.title);
			   if(advertisement.title.contains(filter)){
				  if(advertisement.date.before(dateMin) && advertisement.date.after(dateMax)){ 
			    
					   ObjectifyService.ofy().delete().entity(advertisement);
					   System.out.println("remove : "+advertisement.title);
				   }
				  
			   }
			  }
		   
		   resp.sendRedirect("/boardForDelete.jsp?boardName=" + boardName);
		} catch (Exception e) {
			System.err.println("plouf !!");
			e.printStackTrace();
			// TODO: handle exception
		}

	  }
	// Process the http GET of the form
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


		 System.err.println("Servlet Delete GET");
		 // get the parameters from the request.
		   String boardName=req.getParameter("boardName");
		   String filter=req.getParameter("filter");
		   double priceMin=Double.parseDouble(req.getParameter("priceMin"));
		   double priceMax=Double.parseDouble(req.getParameter("priceMax"));
		  
		   
		   try {
			   SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy"); 
		       Date dateMin = formatter.parse(req.getParameter("dateMax")); 
		       Date dateMax = formatter.parse(req.getParameter("dateMin")); 
		       System.out.println("dateMin===========>"+dateMin);
		       System.out.println("dateMAX===========>"+dateMax);
			   List<Advertisement> advertisements = ObjectifyService.ofy()
				          .load()
				          .type(Advertisement.class)
				          .filter("price >", priceMin).filter("price <", priceMax)
				          .list();
			   System.out.println("Database Loaded");
			      
			   for (Advertisement advertisement : advertisements) {
				   System.out.println("looking for delete at : "+advertisement.title);
				   if(advertisement.title.contains(filter)){
					   System.out.println("dateMin===========>"+dateMin);
				       System.out.println("dateMAX===========>"+dateMax);
					  if(advertisement.date.before(dateMin) && advertisement.date.after(dateMax)){ 
				    
						   ObjectifyService.ofy().delete().entity(advertisement);
						   System.out.println("remove : "+advertisement.title);
					   }
					  
				   }
				  }
			   
			   resp.sendRedirect("/test.jsp?boardName=" + boardName);
			} catch (Exception e) {
				System.err.println("plouf !!");
				e.printStackTrace();
				// TODO: handle exception
			}
}
}

