<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VASA Field Scheduling - Logs</title>
</head>
<body>

<script src="/scheduling/scripts/sorttable.js"></script>

    <table class="list">
    <thead>
	<tr class="header">
		<th>Date</th>
		<th>Event</th>
	</tr>
	</thead>
	<c:forEach var="log" items="${logs}">
		<tr>
<%-- 		    <td><c:out value="${log.creationDate}" /></td> --%>
		    
		    <fmt:formatDate value="${log.creationDate}" var="formattedDate" 
                type="date" pattern="EEEEE, MMM dd, yyyy    hh:mm a" />
			
			<td><c:out value="${formattedDate}" /></td>
		    
		    <td><c:out value="${log.description}" /></td>
		</tr>
	</c:forEach>
	</table>
</body>
</html>