<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VASA Field Scheduling - User List</title>
</head>
<body>
    <h1>List of Users</h1>
    
    <table class="list">
	<tr class="header">
		<th>Type</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>Username</th>
		<th>Team</th>
		<th>Action</th>
	</tr>
	<c:forEach var="modifyuser" items="${users}">
		<tr>
		    <td><c:out value="${modifyuser.userType.displayName}" /></td>
			<td><c:out value="${modifyuser.firstName}" /></td>
			<td><c:out value="${modifyuser.lastName}" /></td>
			<td><c:out value="${modifyuser.userName}" /></td>
			<td><c:out value="${modifyuser.team.name} - ${modifyuser.team.season.name}" /></td>
			<td><a href="modify?user=${modifyuser.id}">Edit</a></td>
		</tr>
	</c:forEach>
	</table>
</body>
</html>