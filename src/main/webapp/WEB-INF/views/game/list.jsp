<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VASA Field Scheduling - Field List</title>
</head>
<body>

<script src="/scheduling/scripts/sorttable.js"></script>

    <div style="width: 100%; overflow: hidden;">
    	<div style="width: 10%px; float: left;"><h1>List of Games</h1></div>
    	<c:if test='${user.userType.displayName == "ADMIN"}'>
    		<div style="margin-left: 70%; float: right;"><a href="add"><img src='/scheduling/images/plus-icon.png' />Add Field</a></div>
    	</c:if>
	</div>
    
    <table class="list">
    <thead>
	<tr class="header">
		<th>Field</th>
		<th>Date</th>
		<th>Age Group</th>
		<th>Home Team</th>
		<th>Away Team</th>
		<c:if test='${user.userType.displayName == "ADMIN"}'>
			<th>Action</th>
		</c:if>
	</tr>
	</thead>
	<c:forEach var="game" items="${games}">
		<tr>
		    <td><c:out value="${game.field.name}" /></td>
		    <td><c:out value="${game.date}" /></td>
		    <td><c:out value="${game.ageGroup.name}" /></td>
		    <td><c:out value="${game.homeTeam}" /></td>
		    <td><c:out value="${game.awayTeam}" /></td>
			<c:if test='${user.userType.displayName == "ADMIN"}'>
			  <td><a href="modify?game=${game.id}">Edit</a></td>
			</c:if>
		</tr>
	</c:forEach>
	</table>
</body>
</html>