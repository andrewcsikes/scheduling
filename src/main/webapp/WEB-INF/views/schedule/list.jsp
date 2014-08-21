<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VASA Field Scheduling - Team List</title>
</head>
<body>

<form id="entry" name="entry" action="list" method="POST">
<table class="search">
<tr>
    <td valign="top">Team:</td>
    <td>
      <select name="team">
        <option value="All">All</option>
        <c:forEach items="${teams}" var="team">
          <option value="${team.id}" ${filterTeam == team.id ? 'selected' : ''}>${team.name} - ${team.coach.lastName} - ${team.ageGroup.name}</option>
        </c:forEach>
      </select>
      <select name="month">
        <option value="1" ${filterMonth == 1 ? 'selected' : ''}>January</option>
        <option value="2" ${filterMonth == 2 ? 'selected' : ''}>February</option>
        <option value="3" ${filterMonth == 3 ? 'selected' : ''}>March</option>
        <option value="4" ${filterMonth == 4 ? 'selected' : ''}>April</option>
        <option value="5" ${filterMonth == 5 ? 'selected' : ''}>May</option>
        <option value="6" ${filterMonth == 6 ? 'selected' : ''}>June</option>
        <option value="7" ${filterMonth == 7 ? 'selected' : ''}>July</option>
        <option value="8" ${filterMonth == 8 ? 'selected' : ''}>August</option>
        <option value="9" ${filterMonth == 9 ? 'selected' : ''}>September</option>
        <option value="10" ${filterMonth == 10 ? 'selected' : ''}>October</option>
        <option value="11" ${filterMonth == 11 ? 'selected' : ''}>November</option>
        <option value="12" ${filterMonth == 12 ? 'selected' : ''}>December</option>
      </select>
    </td>
    
    <td><input type="submit" value="Search"></td>
  </tr>
</table>
</form>

    <h1>List of User</h1>
    <table class="list">
	<tr class="header">
		<th>Team</th>
		<th>Coach</th>
		<th>Sport</th>
		<th>Date</th>
	</tr>
	
	<c:forEach var="shedule_day" items="${shedule}">
		<tr>
		    <td><c:out value="${shedule_day.team.name}" /></td>
			<td><c:out value="${shedule_day.team.coach.firstName}"/> <c:out value="${shedule_day.team.coach.lastName}"/></td>
			<td><c:out value="${shedule_day.team.sport.name}" /></td>
			
			<fmt:formatDate value="${shedule_day.date}" var="formattedDate" 
                type="date" pattern="MM-dd-yyyy hh:mm a" />
			
			<td><c:out value="${formattedDate}" /></td>
		</tr>
	</c:forEach>
	</table>
</body>
</html>