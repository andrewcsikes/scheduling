<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VASA Field Scheduling</title>
</head>
<body>

<script src="/scheduling/scripts/sorttable.js"></script>

<form id="entry" name="entry" action="list" method="POST">
<table class="search">
<tr>
    <td valign="top">Team:</td>
    <td>
      <select name="team">
        <option value="All">ALL</option>
        <c:forEach items="${teams}" var="team">
          <option value="${team.id}" ${filterTeam == team.id ? 'selected' : ''}>${team.name} - ${team.coach.lastName} - ${team.ageGroup.name}</option>
        </c:forEach>
      </select>
    </td>
    <td>Field:</td>
    <td>
      <select name="field">
        <option value="All">ALL</option>
        <c:forEach items="${fields}" var="field">
          <option value="${field.name}" ${filterField == field.name ? 'selected' : ''}>${field.name}</option>
        </c:forEach>
      </select>
    </td>
    <td>
      <select name="game">
        <option value="0" ${game == 0 ? 'selected' : ''}>ALL</option>
        <option value="1" ${game == 1 ? 'selected' : ''}>Practices</option>
        <option value="2" ${game == 2 ? 'selected' : ''}>Games</option>
      </select>
    </td>
    <td>
      <select name="class">
        <option value="0" ${filterClass == 0 ? 'selected' : ''}>ALL</option>
        <option value="1" ${filterClass == 1 ? 'selected' : ''}>VASA Team</option>
        <option value="2" ${filterClass == 2 ? 'selected' : ''}>NON-VASA Team</option>
      </select>
    </td>
    <td valign="top">Month:</td>
    <td>
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

<c:if test="${error != null}">
  <h3><font color="red">${error}</font></h3>
</c:if>

    <h1>Field Usage</h1>
    <table class="list">
	<tr class="header">
		<th>Team</th>
		<th>Coach</th>
		<th>Sport</th>
		<th>Field</th>
		<th>Description</th>
		<th>Date</th>
	</tr>
	
	<c:forEach var="shedule_day" items="${shedule}">
		<tr>
		    <td><c:out value="${shedule_day.team.name}" /></td>
			<td><c:out value="${shedule_day.team.coach.firstName}"/> <c:out value="${shedule_day.team.coach.lastName}"/></td>
			<td><c:out value="${shedule_day.team.sport.name}" /></td>
			<td><c:out value="${shedule_day.field.name}" /></td>
			<td>Practice</td>
			
			<fmt:formatDate value="${shedule_day.date}" var="formattedDate" 
                type="date" pattern="EEEEE, MMM dd, yyyy    hh:mm a" />
			
			<td><c:out value="${formattedDate}" /></td>
		</tr>
	</c:forEach>
	
	<c:forEach var="game" items="${games}">
		<tr>
		    <td>&nbsp;</td>
			<td>&nbsp;</td>
			<td><c:out value="${game.field.sport.name}" /></td>
			<td><c:out value="${game.field.name}" /></td>
			<td><c:out value="Game: ${game.homeTeam} vs ${game.awayTeam}" /></td>
			
			
			<fmt:formatDate value="${game.date}" var="formattedDate" 
                type="date" pattern="EEEEE, MMM dd, yyyy    hh:mm a" />
			
			<td><c:out value="${formattedDate}" /></td>
		</tr>
	</c:forEach>
	</table>
</body>
</html>