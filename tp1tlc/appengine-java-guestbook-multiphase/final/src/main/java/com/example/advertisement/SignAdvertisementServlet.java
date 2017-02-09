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

import com.example.guestbook.Greeting;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Form Handling Servlet
 * Most of the action for this sample is in webapp/guestbook.jsp, which displays the
 * {@link Greeting}'s. This servlet has one method
 * {@link #doPost(<#HttpServletRequest req#>, <#HttpServletResponse resp#>)} which takes the form
 * data and saves it.
 */
public class SignAdvertisementServlet extends HttpServlet {

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
    
    SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
    Date advertisementDate;
	try {
			advertisementDate = formatter.parse(req.getParameter("date"));
		if (user != null) {
		      advertisement = new Advertisement(user.getEmail(),user.getUserId(), advertisementTitle,advertisementPrice,advertisementDate);
		    } else {
		      advertisement = new Advertisement(advertisementTitle,advertisementPrice,advertisementDate);
		    }
		 ObjectifyService.ofy().save().entity(advertisement).now();

		    resp.sendRedirect("/board.jsp?boardName=" + boardName);
		  
	} catch (ParseException e) {
		resp.sendRedirect("/board.jsp?boardName=" + boardName);
	}
  }
 }
