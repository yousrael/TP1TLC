/**
 * Copyright 2014-2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//[START all]
package com.example.advertisement;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Form Handling Servlet
 */
public class SignAdvertisementServlet extends HttpServlet {
	static boolean debug = false;

  // Process the http POST of the form
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    Advertisement advertisement;

	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();  // Find out who the user is.
	    String boardName=req.getParameter("boardName");
	    String advertisementTitle = req.getParameter("title");
	    
	    double advertisementPrice = 0;
	    try{
	    	advertisementPrice = (double) Double.parseDouble(req.getParameter("price"));
	    }
	    catch(NumberFormatException e){
	    	resp.sendRedirect("/board.jsp?boardName=" + boardName);
	    }
	    Date advertisementDate=null;
	    SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
	    try {
	    	advertisementDate = formatter.parse(req.getParameter("date"));
	    } catch (ParseException e) {
			resp.sendRedirect("/board.jsp?boardName=" + boardName);
		}
	    if(debug)
	    	System.out.println("title: "+advertisementTitle+" price: "+advertisementPrice+" date: "+advertisementDate);
			if (user != null) {
			      advertisement = new Advertisement(user.getEmail(),user.getUserId(), advertisementTitle,advertisementPrice,advertisementDate);
			    } else {
			      advertisement = new Advertisement(advertisementTitle,advertisementPrice,advertisementDate);
			    }
			 ObjectifyService.ofy().save().entity(advertisement).now();

			    resp.sendRedirect("/board.jsp?boardName=" + boardName);
			  
	  }
  
  // Process the http POST of the form
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    Advertisement advertisement;

	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();  // Find out who the user is.
	    String boardName=req.getParameter("boardName");
	    String advertisementTitle = req.getParameter("title");
	    
	    double advertisementPrice = 0;
	    try{
	    	advertisementPrice = (double) Double.parseDouble(req.getParameter("price"));
	    }
	    catch(NumberFormatException e){
	    	resp.sendRedirect("/board.jsp?boardName=" + boardName);
	    }
	    Date advertisementDate=null;
	    SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
	    try {
	    	advertisementDate = formatter.parse(req.getParameter("date"));
	    } catch (ParseException e) {
			resp.sendRedirect("/board.jsp?boardName=" + boardName);
		}
	    if(debug)
	    	System.out.println("title: "+advertisementTitle+" price: "+advertisementPrice+" date: "+advertisementDate);
			if (user != null) {
			      advertisement = new Advertisement(user.getEmail(),user.getUserId(), advertisementTitle,advertisementPrice,advertisementDate);
			    } else {
			      advertisement = new Advertisement(advertisementTitle,advertisementPrice,advertisementDate);
			    }
			 ObjectifyService.ofy().save().entity(advertisement).now();

			    resp.sendRedirect("/board.jsp?boardName=" + boardName);
			  
	  }
 }
