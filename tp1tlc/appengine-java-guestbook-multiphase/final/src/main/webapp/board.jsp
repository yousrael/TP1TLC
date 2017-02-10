<%-- //[START all]--%>


<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%-- //[START imports]--%>
<%@ page import="com.example.advertisement.Advertisement" %>
<%@ page import="com.example.advertisement.Board" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%-- //[END imports]--%>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Advertisement WebSite</title>
    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Theme CSS -->
    <link href="css/freelancer.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
   
  
    </head>
   
<body id="page-top" class="index">
    <!-- Navigation -->
    <nav id="mainNav" class="navbar navbar-default navbar-fixed-top navbar-custom">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand" href="#page-top">Home</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li class="hidden">
                        <a href="#page-top"></a>
                    </li>
					<%
					    String boardName = request.getParameter("boardName");
					    if (boardName == null) {
					        boardName = "default";
					    }
					    pageContext.setAttribute("boardName", boardName);
					    UserService userService = UserServiceFactory.getUserService();
					    User user = userService.getCurrentUser();
					    if (user != null) {
					        pageContext.setAttribute("user", user);
					%>
						<button class="btn btn-danger" onclick="location.href='<%= userService.createLogoutURL(request.getRequestURI()) %>'" type="button">Sign out</button>
					<%
					    } else {
					%>
					    <button class="btn btn-info" onclick="location.href='<%= userService.createLoginURL(request.getRequestURI()) %>'" type="button">Sign in</button>
					<%
					    }
					%>
                    <form class="navbar-form navbar-left" role="search" action="/filter" method="post">
                      <div class="form-group">
                        <input type="text" class="form-control" name="filter" placeholder="Search">
                      </div>
                      
  
  <div data-role="rangeslider">
    <label for="range-1a" > Minimum Price:</label>
    <input name="priceMin"  min="0" max="100" value="0" type="range" >
    <label for="range-1b" >Maximum Price:</label>
    <input name="priceMax"  min="0" max="100" value="100" type="range" >
  </div>

                  <!--   <div align="center"> 0 <input type="range" name="priceMin" min="0" max="500" />500</div>
                     <div align="center"> 500 <input type="range" name="priceMax" min="500" max="10000" />10000</div>-->
                   
                      <button type="submit" class="btn btn-success">Search</button>
                    </form>
                   
                </ul>
            </div>
        </div>
       
    </nav>
    
    
    <!-- Header -->
    <header>
        <div class="container">

<%-- //[START datastore]--%>
<%
    // Create the correct Ancestor key
      Key<Board> board = Key.create(Board.class, boardName);

    // Run an ancestor query to ensure we see the most up-to-date
    // view of the Advertisements belonging to the selected Board.
      List<Advertisement> advertisements = ObjectifyService.ofy()
          .load()
          .type(Advertisement.class) // We want only Advertisements
        //  .ancestor(board)    // Anyone in this board
         // .order("-date")       // Most recent first - date is indexed.
         //.limit(10)             // Only show 5 of them.
          .list();
     
    if (advertisements.isEmpty()) {
%>
<p>Board '${fn:escapeXml(boardName)}' has no advertisement.</p>
<%
    } else
    	 {
%>
<p>Advertisement in Board '${fn:escapeXml(boardName)}'.</p>
	<div class="container">
    <div class="row">
      <div class="table-responsive">
        <table class="table table-hover">
          <tbody id="myTable">
<%
      // Look at all of our greetings
      	int nAdvertisement = 0;
        
        	if(request.getParameter("filter")==null){
        		for (Advertisement advertisement : advertisements) {
        	nAdvertisement++;
            pageContext.setAttribute("advertisement_title", advertisement.title);
            pageContext.setAttribute("advertisement_price", advertisement.price);
            pageContext.setAttribute("advertisement_date", advertisement.date);
            String author;
            if (advertisement.author_email == null) {
                author = "An anonymous person";
            } else {
                author = advertisement.author_email;
                String author_id = advertisement.author_id;
                if (user != null && user.getUserId().equals(author_id)) {
                    author += " (You)";
                }
            }
            pageContext.setAttribute("advertisement_user", author);
%>

              <tr>	<div class="advertisement">
		<h1><b>Advertisement n°<%=nAdvertisement%></b></h1>
		<p><b>Title  : </b>${fn:escapeXml(advertisement_title)}</p>
		<p><b>Price  : </b>${fn:escapeXml(advertisement_price)} $</p>
		<p><b>Date   : </b>${fn:escapeXml(advertisement_date)}</p>
		<p><b>Author : </b>${fn:escapeXml(advertisement_user)}</p>
	</div></tr>
<%
        } 
        	}
else 
 { //search
	//List<Double> priceRange=new ArrayList();
	//  priceRange.add(Double.parseDouble(request.getParameter("priceMin")));
	//  priceRange.add(Double.parseDouble(request.getParameter("priceMax")));
	
	  List<Advertisement> advertisements2= ObjectifyService.ofy()
	          .load()
	          .type(Advertisement.class) // We want only Advertisements
	          .filter("price >", Double.parseDouble(request.getParameter("priceMin"))).filter("price <", Double.parseDouble(request.getParameter("priceMax"))).list();
	  for (Advertisement advertisement : advertisements2) {
	pageContext.setAttribute("advertisement_title", advertisement.title);
    pageContext.setAttribute("advertisement_price", advertisement.price);
    pageContext.setAttribute("advertisement_date", advertisement.date);
    
	//if(advertisement.title.contains(request.getParameter("filter")) ){
		%> <tr>	<div class="advertisement">
		<h1><b>Advertisement n°<%=nAdvertisement%></b></h1>
		<p><b>Title  : </b>${fn:escapeXml(advertisement_title)}</p>
		<p><b>Price  : </b>${fn:escapeXml(advertisement_price)} $</p>
		<p><b>Date   : </b>${fn:escapeXml(advertisement_date)}</p>
		<p><b>Author : </b>${fn:escapeXml(advertisement_user)}</p>
	</div></tr> <%
	}
}
}
    //}
%>
          </tbody>
        </table>   
      </div>
      <div class="col-md-12 text-center">
      <ul class="pagination pagination-lg pager" id="myPager"></ul>
      </div>
	</div>
</div>

	<form action="/delete" method="post">
		<div class="form-group">
			 </br>
    		 <button type="submit" class="btn btn-danger">Remove All Visible Entry</button>
	  			
	  			  <input type="hidden" name="filter" value="<% out.print(request.getParameter("filter")); %>"/>
	  			 
   		 </div>
	</form>
    
  <div class="col-md-5">
	<form action="/sign" method="post">
    <h3 style="margin-bottom: 25px; text-align: center;">Create your new adverstisement</h3>
	  <div class="form-group">
		  <input id="inputTitle" class="form-control" name="title" placeholder="Title"></input>
   </div>
   <div class="form-group">
			<input id="inputPrice" class="form-control" name="price" placeholder="Price"></input>
    </div>
    <div class="form-group">
			<input id="inputDate" class="form-control " name="date" placeholder="Date"></input>
    </div>
    <div class="form-group">
     <button type="submit" class="btn btn-success">Post Advertisement</button>
	   <input type="hidden" name="boardName" value="${fn:escapeXml(boardName)}"/>
    </div>
	</form>

	
</div>
    </header>
    </body>
    <footer class="text-center">
       <div class="footer-above">
           <div class="container">
               <div class="row">
                   <div class="footer-col col-md-4">
                   </div>
                   <div class="footer-col col-md-4">
                   </div>
               </div>
           </div>
       </div>
       <div class="footer-below">
           <div class="container">
               <div class="row">
                   <div class="col-lg-12">
                       Master 2 GLA : Simon, Stephen, Yousra
                   </div>
               </div>
           </div>
       </div>
   </footer>
   <!-- jQuery -->
   <script src="vendor/jquery/jquery.min.js"></script>
   <!-- Bootstrap Core JavaScript -->
   <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
 
</html>

