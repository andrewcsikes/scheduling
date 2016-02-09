<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VASA Field Scheduling - Important Dates List</title>
</head>
<body>

<script src="/scheduling/scripts/sorttable.js"></script>

    <div style="width: 100%; overflow: hidden;">
    	<div style="width: 10%px; float: left;"><h1>Important Dates</h1></div>
    	<c:if test='${user.userType.displayName == "ADMIN"}'>
    		<div style="margin-left: 70%; float: right;"><a href="add"><img src='/scheduling/images/plus-icon.png' />Add Important Date</a></div>
    	</c:if>
	</div>
    
    <table class="list">
    <thead>
	<tr class="header">
		<th>Date</th>
		<th>Message</th>
		<c:if test='${user.userType.displayName == "ADMIN"}'>
			<th>Action</th>
		</c:if>
	</tr>
	</thead>
	<c:forEach var="im" items="${message}">
		<tr>
			<fmt:formatDate value="${im.date}" var="formattedDate" 
                type="date" pattern="EEEEE, MMM dd, yyyy" />
			
			<td><c:out value="${formattedDate}" /></td>
			
		    <td><c:out value="${im.message}" /></td>
			<c:if test='${user.userType.displayName == "ADMIN"}'>
			  <td><a href="modify?messageId=${im.id}">Edit</a></td>
			</c:if>
		</tr>
	</c:forEach>
	</table>
</body>
</html>