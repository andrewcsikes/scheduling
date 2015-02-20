<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VASA Field Scheduling - Team List</title>
</head>
<body>

<script src="/scheduling/scripts/sorttable.js"></script>

<form id="entry" name="entry" action="list" method="POST">
<table class="search">
<tr>
    <td valign="top">Sport:</td>
    <td>
      <select name="sport">
        <option value="All">All</option>
        <c:forEach items="${sports}" var="sport">
          <option value="${sport.id}" ${modifyteam.sport.id == sport.id ? 'selected' : ''}>${sport.name}</option>
        </c:forEach>
      </select>
    </td>
    <td valign="top">Season:</td>
    <td>
      <select name="season">
        <option value="All">All</option>
        <c:forEach items="${seasons}" var="season">
          <option value="${season.id}" ${modifyteam.season.id == season.id ? 'selected' : ''}>${season.name}</option>
        </c:forEach>
      </select>
    </td>
    <td valign="top">Age Group:</td>
    <td>
      <select name="ageGroup">
        <option value="All">All</option>
        <c:forEach items="${agegroups}" var="agegroup">
          <option value="${agegroup.id}" ${modifyteam.ageGroup.id == agegroup.id ? 'selected' : ''}>${agegroup.name}</option>
        </c:forEach>
      </select>
    </td>
    <td><input type="submit" value="Search"></td>
  </tr>
</table>
</form>

    <div style="width: 100%; overflow: hidden;">
    	<div style="width: 10%px; float: left;"><h1>List of Teams</h1></div>
    	<c:if test='${user.userType.displayName == "ADMIN" || user.userType.displayName == "Commissioner"}'>
    		<div style="margin-left: 70%; float: right;"><a href="add"><img src='/scheduling/images/plus-icon.png' />Add Team</a></div>
    	</c:if>
	</div>
    
    <table class="list">
    <thead>
	<tr class="header">
		<th>Age Group</th>
		<th>Name</th>
		<th>Coach</th>
		<th>Sport</th>
		<th>Season</th>
		<th>Classification</th>
		<c:if test='${user.userType.displayName == "ADMIN" || user.userType.displayName == "Commissioner"}'>
			<th>Action</th>
		</c:if>
	</tr>
	</thead>
	<c:forEach var="team" items="${teams}">
		<tr>
		    <td><c:out value="${team.ageGroup.name}" /></td>
		    <td><c:out value="${team.name}" /></td>
			<td><c:out value="${team.coach.firstName}"/> <c:out value="${team.coach.lastName}"/></td>
			<td><c:out value="${team.sport.name}" /></td>
			<td><c:out value="${team.season.name}" /></td>
			<td><c:out value="${team.classification.displayName}" /></td>
			<c:if test='${user.userType.displayName == "ADMIN" || user.userType.displayName == "Commissioner"}'>
			  <td><a href="modify?team=${team.id}">Edit</a></td>
			</c:if>
		</tr>
	</c:forEach>
	</table>
</body>
</html>