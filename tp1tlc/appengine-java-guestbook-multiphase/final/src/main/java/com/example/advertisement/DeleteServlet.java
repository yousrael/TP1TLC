package com.example.advertisement;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;



public class DeleteServlet  extends HttpServlet  {

	  // Process the http POST of the form
	@Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	   
			
	   System.err.println("Servlet Delete post");
	   // Find out who the user is.
	   String boardName=req.getParameter("boardName");
	   String filter = null;
	   String priceMin = null;
	   String priceMax = null;
	   String dateMin = null;
	   String dateMax = null;
	   try {
		   filter=req.getParameter("filter");
		   priceMin=req.getParameter("priceMin");
		   priceMax=req.getParameter("priceMax");
		   dateMin=req.getParameter("dateMin");
		   dateMax=req.getParameter("dateMax");
	   } catch (Exception e) {
		   if(filter == null) filter = "";
		   if(priceMin == null) priceMin = "0";
		   if(priceMax == null) priceMax = "65535";
		   if(dateMin == null) dateMin = "01-01-2000";
		   if(dateMax == null) dateMax = "01-01-2020";
	   }
	    double ipriceMin = 0;
	    double ipriceMax = 65535;
	    Date ddateMin = null;
	    Date ddateMax = null;
	    
	   try {
		   SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
		   ipriceMin = Double.parseDouble(priceMin);
		   ipriceMax = Double.parseDouble(priceMax);
		   ddateMin = formatter.parse(dateMin);
		   ddateMax = formatter.parse(dateMax);
	   } catch (Exception e) {
		   System.out.println("error converting number & dates");
	   }
//	   System.out.println("/board.jsp?boardName=" + boardName+"&filter="+filter+"&priceMin="+ipriceMin+"&priceMax="+ipriceMax+"&dateMin="+ddateMin+"&dateMax="+ddateMax);
		  
	   // String delete=req.getParameter("delete");
	   // System.out.println("boardName: "+boardName+" filter: "+filter+" delete: "+delete);
	  
	   try {
		   List<Advertisement> advertisements = ObjectifyService.ofy()
			          .load()
			          .type(Advertisement.class)
			          .filter("price >", ipriceMin).filter("price <", ipriceMax)
			          .list();
		   
		   System.out.println("Database Loaded");
		      
		   for (Advertisement advertisement : advertisements) {
			   System.out.println("looking for delete at : "+advertisement.title);
			   if(advertisement.title.contains(filter)){
//				   System.out.println("date"+advertisement.date+" dmin"+ddateMin+" dmax"+ddateMax);
				   if(advertisement.date.before(ddateMax) && advertisement.date.after(ddateMin)){
					   ObjectifyService.ofy().delete().entity(advertisement);
					   System.out.println("remove : "+advertisement.title);
				   }
				  }
			  else{
				  if(filter == null || filter.isEmpty() || filter.contains("null")){ 
					  if(advertisement.date.before(ddateMax) && advertisement.date.after(ddateMin)){
						  ObjectifyService.ofy().delete().entity(advertisement);
						  System.out.println("remove : "+advertisement.title);
					  }
				  }
			  }
		   }
		   resp.sendRedirect("/board.jsp?boardName=" + boardName+"&filter="+filter);
		} catch (Exception e) {
			System.err.println("plouf !!");
			e.printStackTrace();
			// TODO: handle exception
		}

	  }
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


		System.err.println("Servlet Delete post");
		// Find out who the user is.
		String boardName=req.getParameter("boardName");
		String filter = null;
		String priceMin = null;
		String priceMax = null;
		String dateMin = null;
		String dateMax = null;
		try {
			filter=req.getParameter("filter");
			priceMin=req.getParameter("priceMin");
			priceMax=req.getParameter("priceMax");
			dateMin=req.getParameter("dateMin");
			dateMax=req.getParameter("dateMax");
		} catch (Exception e) {
			if(filter == null) filter = "";
			if(priceMin == null) priceMin = "0";
			if(priceMax == null) priceMax = "65535";
			if(dateMin == null) dateMin = "01-01-2000";
			if(dateMax == null) dateMax = "01-01-2020";
		}
		double ipriceMin = 0;
		double ipriceMax = 65535;
		Date ddateMin = null;
		Date ddateMax = null;

		try {
			SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
			ipriceMin = Double.parseDouble(priceMin);
			ipriceMax = Double.parseDouble(priceMax);
			ddateMin = formatter.parse(dateMin);
			ddateMax = formatter.parse(dateMax);
		} catch (Exception e) {
			System.out.println("error converting number & dates");
		}
		//		   System.out.println("/board.jsp?boardName=" + boardName+"&filter="+filter+"&priceMin="+ipriceMin+"&priceMax="+ipriceMax+"&dateMin="+ddateMin+"&dateMax="+ddateMax);

		// String delete=req.getParameter("delete");
		// System.out.println("boardName: "+boardName+" filter: "+filter+" delete: "+delete);

		try {
			List<Advertisement> advertisements = ObjectifyService.ofy()
					.load()
					.type(Advertisement.class)
					.filter("price >", ipriceMin).filter("price <", ipriceMax)
					.list();

			System.out.println("Database Loaded");

			for (Advertisement advertisement : advertisements) {
				System.out.println("looking for delete at : "+advertisement.title);
				if(advertisement.title.contains(filter)){
					//					   System.out.println("date"+advertisement.date+" dmin"+ddateMin+" dmax"+ddateMax);
					if(advertisement.date.before(ddateMax) && advertisement.date.after(ddateMin)){
						ObjectifyService.ofy().delete().entity(advertisement);
						System.out.println("remove : "+advertisement.title);
					}
				}
				else{
					if(filter == null || filter.isEmpty() || filter.contains("null")){ 
						if(advertisement.date.before(ddateMax) && advertisement.date.after(ddateMin)){
							ObjectifyService.ofy().delete().entity(advertisement);
							System.out.println("remove : "+advertisement.title);
						}
					}
				}
			}
			resp.sendRedirect("/board.jsp?boardName=" + boardName+"&filter="+filter);
		} catch (Exception e) {
			System.err.println("plouf !!");
			e.printStackTrace();
			// TODO: handle exception
		}

	}
}

