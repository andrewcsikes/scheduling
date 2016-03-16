<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>

<c:if test="${message != null}">
  <h3><font color="red">${message}</font></h3>
  <p>&nbsp;</p>
</c:if>

<h1>
	Welcome to the new VASA Field Scheduling tool. 
</h1>

<a href="help">Directions on using the System.</a>

<p>&nbsp;</p>

<c:set var="rulesApplied" scope="request" value="false"/>
<h3>Active Scheduling Rules</h3>

<ul>
	<li>6U and 8U are limited to 1 hour practices with a max of 2 hours/week</li>
	<li>10U and 12U are limited to 1 1/2 hour practices with a max of 3 hours/week</li>
	<li>Batting Cages do not count against your practice times.</li>
	<li>Non-VASA teams can not schedule until the Friday before.</li>
</ul>

<c:forEach var="season" items="${seasons}">
<c:if test="${season.status.displayName.equals('Active')}">
	<c:if test="${season.applySchedulingRules==true}">
	
		<c:if test="${season.sport.name.equals('Baseball')}">
			<p>
				<b>Baseball - ${season.name}</b>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Once the Season Starts the following rules are in place ...</b>
				<ul>
					<li>6U and 8U are limited to 1 hour practices with a max of 1 practice/week</li>
					<li>10U and 12U are limited to 1 1/2 hour practices with a max of 1 practice/week</li>
					<li>On Saturday of the week prior to your practice, you can add more time based on what's available</li>
					<li>The Schedule is locked for all fields until Tuesday @ 10PM prior to the week being scheduled for Baseball fields.</li>
					<li>The Schedule is locked for Softball fields until Wednesday @ 10PM prior to the week being scheduled.</li>
					<li>10U, 12U, and 14U are not allowed to schedule anything before 7:00 PM until the Saturday prior to the week</li>
				</ul>
			</p>
			<c:set var="rulesApplied" scope="request" value="true"/>
		</c:if>
		<c:if test="${season.sport.name.equals('Softball')}">
			<p>
				<b>Softball - ${season.name}</b>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Once the Season Starts the following rules are in place ...</b>
				<ul>
					<li>6U and 8U are limited to 1 hour practices with a max of 1 practice/week</li>
					<li>10U and 12U are limited to 1 1/2 hour practices with a max of 1 practice/week</li>
					<li>On Saturday of the week prior to your practice, you can add more time based on what's available</li>
					<li>The Schedule is locked for all fields until Tuesday @ 10PM prior to the week being scheduled for Softball fields.</li>
					<li>The Schedule is locked for Baseball fields until Wednesday @ 10PM prior to the week being scheduled.</li>
					<li>6U and 8U are not allowed to schedule anything on Big East until the Saturday prior to the week.</li>
					<li>10U, 12U, and 14U are not allowed to schedule anything on Little East until the Saturday prior to the week.</li>
				</ul>
			</p>
			<c:set var="rulesApplied" scope="request" value="true"/>
		</c:if>
	</c:if>
</c:if>
</c:forEach>

<c:if test="${!rulesApplied}">
<p>None at this time</p>
</c:if>


<%
String userAgent = request.getHeader("user-agent");
out.println("<p><font color='white'>"+userAgent+"</font></p>");
%>
</body>
</html>