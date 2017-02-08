<%-- //[START all]--%>
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
                    <form class="navbar-form navbar-left" role="search">
                      <div class="form-group">
                        <input type="text" class="form-control" placeholder="Search">
                      </div>
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
          .ancestor(board)    // Anyone in this board
          .order("-date")       // Most recent first - date is indexed.
          .limit(5)             // Only show 5 of them.
          .list();

    if (advertisements.isEmpty()) {
%>
<p>Board '${fn:escapeXml(boardName)}' has no advertisement.</p>
<%
    } else {
%>
<p>Advertisement in Board '${fn:escapeXml(boardName)}'.</p>
<%
      // Look at all of our greetings
        for (Advertisement advertisement : advertisements) {
            pageContext.setAttribute("advertisement_title", advertisement.getTitle());
            pageContext.setAttribute("advertisement_price", advertisement.getPrice());
            pageContext.setAttribute("advertisement_date", advertisement.getDate());
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
<p><b>${fn:escapeXml(advertisement_user)}</b> wrote:</p>
<blockquote>${fn:escapeXml(advertisement_title)}</blockquote>
<blockquote>${fn:escapeXml(advertisement_price)}</blockquote>
<blockquote>${fn:escapeXml(advertisement_date)}</blockquote>
<%
        }
    }
%>
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
    <div class="col-md-5">
  		<form action="/board.jsp" class="navbar-form navbar-left" method="get">
  		    <div class="form-group">
            <input class="form-control" name="boardName" value="${fn:escapeXml(boardName)}"/>
    		    <button type="submit" class="btn btn-success">Switch Board</button>
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
                       Master 2 GLA : Simon, Stephen, Yoursra
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

