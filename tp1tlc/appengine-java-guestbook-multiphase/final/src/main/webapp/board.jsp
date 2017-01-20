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

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<body>

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

<p>Hello, ${fn:escapeXml(user.nickname)}! (You can
    <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
    } else {
%>
<p>Hello!
    <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
    to include your name with greetings you post.</p>
<%
    }
%>

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
        for (Advertisement advertisement : Advertisements) {
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
<p><b>${fn:escapeXml(advertisement_user)}</b> wrote:</p>
<blockquote>${fn:escapeXml(advertisement_title)}</blockquote>
<blockquote>${fn:escapeXml(advertisement_price)}</blockquote>
<blockquote>${fn:escapeXml(advertisement_date)}</blockquote>
<%
        }
    }
%>
<form action="/sign" method="post">
    <div><textarea name="content" rows="3" cols="60"></textarea></div>
    <div><input type="submit" value="Post Advertisement"/></div>
    <input type="hidden" name="boardName" value="${fn:escapeXml(boardName)}"/>
</form>
<%-- //[END datastore]--%>
<form action="/board.jsp" method="get">
    <div><input type="text" name="boardName" value="${fn:escapeXml(boardName)}"/></div>
    <div><input type="submit" value="Switch Board"/></div>
</form>

</body>
</html>
<%-- //[END all]--%>
