<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Welcome to the new VASA Field Scheduling tool. 
</h1>

<a href="help">Directions on using the System.</a>

<p>&nbsp;</p>


<c:set var="rulesApplied" scope="request" value="false"/>
<h3>Active Scheduling Rules</h3>

<c:forEach var="season" items="${seasons}">
<c:if test="${season.status.displayName.equals('Active')}">
	<c:if test="${season.applySchedulingRules==true}">
		<c:if test="${season.sport.name.equals('Baseball')}">
			<p>
				<b>Baseball - ${season.name}</b>
				<br />Schedule is locked until the Friday prior to the week.
				<br />Non-VASA teams can not schedule until that Saturday.
				<br />10U, 12U, and 14U are not allowed to schedule anything before 7:00 PM until the Saturday prior to the week.
			</p>
			<c:set var="rulesApplied" scope="request" value="true"/>
		</c:if>
		<c:if test="${season.sport.name.equals('Softball')}">
			<p>
				<b>Softball - ${season.name}</b>
				<br />Schedule is locked until the Thursday prior to the week.
				<br />Non-VASA teams can not schedule until that Saturday.
				<br />6U and 8U are not allowed to schedule anything on Big East until the Saturday prior to the week.
				<br />10U, 12U, and 14U are not allowed to schedule anything on Little East until the Saturday prior to the week.
			</p>
			<c:set var="rulesApplied" scope="request" value="true"/>
		</c:if>
	</c:if>
</c:if>
</c:forEach>

<c:if test="${!rulesApplied}">
<p>None at this time</p>
</c:if>

</body>
</html>