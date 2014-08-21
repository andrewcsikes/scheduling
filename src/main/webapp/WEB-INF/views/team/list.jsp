<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VASA Field Scheduling - Team List</title>
</head>
<body>
    <h1>List of Teams</h1>
    
    <table class="list">
	<tr class="header">
		<th>Name</th>
		<th>Coach</th>
		<th>Sport</th>
		<th>Season</th>
		<th>Age Group</th>
		<c:if test='${user.userType.displayName == "ADMIN"}'>
			<th>Action</th>
		</c:if>
	</tr>
	<c:forEach var="team" items="${teams}">
		<tr>
		    <td><c:out value="${team.name}" /></td>
			<td><c:out value="${team.coach.firstName}"/> <c:out value="${team.coach.lastName}"/></td>
			<td><c:out value="${team.sport.name}" /></td>
			<td><c:out value="${team.season.name}" /></td>
			<td><c:out value="${team.ageGroup.name}" /></td>
			<c:if test='${user.userType.displayName == "ADMIN"}'>
			  <td><a href="modify?team=${team.id}">Edit</a></td>
			</c:if>
		</tr>
	</c:forEach>
	</table>
</body>
</html>