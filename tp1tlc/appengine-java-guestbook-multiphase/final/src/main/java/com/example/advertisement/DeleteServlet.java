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

	static boolean debug = false;
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Process the http POST of the form
	@Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	   
			
	if(debug)System.err.println("Servlet Delete POST");
	   // get the parameters from the request.
	   double priceMin;
	   double priceMax;
	   String boardName="";
	   String filter= "";
	   Date dateMin = null; 
       Date dateMax = null; 
	   try {
		   priceMin=Double.parseDouble(req.getParameter("priceMin"));
		   priceMax=Double.parseDouble(req.getParameter("priceMax"));
	   } catch (Exception e) {
		   priceMin = 0;
		   priceMax = 10000;
	   }
	   try {
		   SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy"); 
		   dateMin = formatter.parse(req.getParameter("dateMin")); 
		   dateMax = formatter.parse(req.getParameter("dateMax")); 
	   } catch (Exception e) {
		   dateMin = new Date(100, 01, 01);
		   dateMax = new Date(120, 01, 01);
	   }
	   
	   try {
		   boardName=req.getParameter("boardName");
		   filter=req.getParameter("filter");
		   
		   List<Advertisement> advertisements = ObjectifyService.ofy()
			          .load()
			          .type(Advertisement.class)
			          .filter("price >", priceMin).filter("price <", priceMax)
			          .list();
		  
		   if(debug)System.out.println("Database Loaded");
		      
		   for (Advertisement advertisement : advertisements) {
			   System.out.println("looking for delete at : "+advertisement.title + " $"+advertisement.price+" "+advertisement.date);
//			   System.out.println("filter: "+filter+" datemin & max: "+dateMin+" "+dateMax+" priceMin & max: "+priceMin+" "+priceMax);
			   if(advertisement.title.contains(filter) || filter.contentEquals("null")){// titre contient le mot ou filtre vide car on suprime tout
				  if(advertisement.date.before(dateMax) && advertisement.date.after(dateMin)){
					   ObjectifyService.ofy().delete().entity(advertisement);
					   if(debug)System.out.println("remove : "+advertisement.title);
				   }
				  
			   }
			  }
		   
		   resp.sendRedirect("/board.jsp");
		} catch (Exception e) {
			if(debug)System.err.println("plouf !!");
			e.printStackTrace();
			// TODO: handle exception
		}

	  }
	// Process the http GET of the form
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		   
		
		if(debug)System.err.println("Servlet Delete POST");
		   // get the parameters from the request.
		   double priceMin;
		   double priceMax;
		   String boardName="";
		   String filter= "";
		   Date dateMin = null; 
	       Date dateMax = null; 
		   try {
			   priceMin=Double.parseDouble(req.getParameter("priceMin"));
			   priceMax=Double.parseDouble(req.getParameter("priceMax"));
		   } catch (Exception e) {
			   priceMin = 0;
			   priceMax = 10000;
		   }
		   try {
			   SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy"); 
			   dateMin = formatter.parse(req.getParameter("dateMin")); 
			   dateMax = formatter.parse(req.getParameter("dateMax")); 
		   } catch (Exception e) {
			   dateMin = new Date(100, 01, 01);
			   dateMax = new Date(120, 01, 01);
		   }
		   
		   try {
			   boardName=req.getParameter("boardName");
			   filter=req.getParameter("filter");
			   
			   List<Advertisement> advertisements = ObjectifyService.ofy()
				          .load()
				          .type(Advertisement.class)
				          .filter("price >", priceMin).filter("price <", priceMax)
				          .list();
			  
			   if(debug)System.out.println("Database Loaded");
			      
			   for (Advertisement advertisement : advertisements) {
				   if(debug)System.out.println("looking for delete at : "+advertisement.title + " $"+advertisement.price+" "+advertisement.date);
//				   System.out.println("filter: "+filter+" datemin & max: "+dateMin+" "+dateMax+" priceMin & max: "+priceMin+" "+priceMax);
				   if(advertisement.title.contains(filter) || filter.contentEquals("null")){// titre contient le mot ou filtre vide car on suprime tout
					  if(advertisement.date.before(dateMax) && advertisement.date.after(dateMin)){
						   ObjectifyService.ofy().delete().entity(advertisement);
						   System.out.println("remove : "+advertisement.title);
					   }
					  
				   }
				  }
			   
			   resp.sendRedirect("/board.jsp");
			} catch (Exception e) {
				if(debug)System.err.println("plouf !!");
				e.printStackTrace();
				// TODO: handle exception
			}

		  }
}

