<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VASA Field Scheduling - Field List</title>

<link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script>
$(function() {
$( "#datepicker" ).datepicker();
});
</script>

</head>
<body>

<script src="/scripts/sorttable.js"></script>
	
<form id="entry" name="entry" action="list" method="POST">
<table class="search">
<tr>
    <td>Field:</td>
    <td>
      <select name="field">
        <option value="All">ALL</option>
        <c:forEach items="${fields}" var="field">
          <option value="${field.name}" ${filterField == field.name ? 'selected' : ''}>${field.name}</option>
        </c:forEach>
      </select>
    </td>
    <td>Date:</td>
    <td>
      <input name="date" type="text" id="datepicker" size="10">
    </td>
    
    <td><input type="submit" value="Search"></td>
  </tr>
</table>
</form>

    <div style="width: 100%; overflow: hidden;">
    	<div style="width: 10%px; float: left;"><h1>List of Games</h1></div>
    	<div style="margin-left: 70%; float: right;"><a href="add"><img src='/images/plus-icon.png' />Add Game</a></div>
	</div>
    
    <table class="list">
    <thead>
	<tr class="header">
		<th>Field</th>
		<th>Date</th>
		<th>Age Group</th>
		<th>Home Team</th>
		<th>Away Team</th>
		<c:if test='${user.userType.displayName == "ADMIN" || user.userType.displayName == "Commissioner"}'>
			<th>Action</th>
		</c:if>
	</tr>
	</thead>
	<c:forEach var="game" items="${games}">
		<tr>
		    <td><c:out value="${game.field.name}" /></td>
		    
		    <fmt:formatDate value="${game.date}" var="formattedDate" 
                type="date" pattern="EEEEE, MMM dd, yyyy    hh:mm a" />
			
			<td><c:out value="${formattedDate}" /></td>
			
		    <td><c:out value="${game.ageGroup.name}" /></td>
		    <td><c:out value="${game.homeTeam}" /></td>
		    <td><c:out value="${game.awayTeam}" /></td>
			<c:if test='${user.userType.displayName == "ADMIN" || user.userType.displayName == "Commissioner"}'>
			  <td><a href="modify?game=${game.id}">Edit</a></td>
			</c:if>
		</tr>
	</c:forEach>
	</table>
</body>
</html>