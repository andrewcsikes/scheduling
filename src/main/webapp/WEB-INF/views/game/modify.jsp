<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Game - Modify</title>
</head>
<body>

<form id="entry" name="entry" action="modify" method="POST"  onsubmit="return validate();">
<input type="hidden" name="id" value="${modifygame.id}" />

<table class="modify">
  <tr>
    <td valign="top">Field:</td>
    <td>
      <select name="field">
        <c:forEach items="${fields}" var="field">
          <option value="${field.name}" ${modifygame.field.id == field.id ? 'selected' : ''}>${field.name}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Age Group:</td>
    <td>
      <select name="ageGroup">
        <c:forEach items="${agegroups}" var="agegroup">
          <option value="${agegroup.id}" ${modifygame.ageGroup.id == agegroup.id ? 'selected' : ''}>${agegroup.name}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Home Team:</td>
    <td><input name="homeTeam" type="text" size="40" value="${modifygame.homeTeam}" /></td>
  </tr>
  <tr>
    <td valign="top">Away Team:</td>
    <td><input name="awayTeam" type="text" size="40" value="${modifygame.awayTeam}" /></td>
  </tr>
  <tr>
    <td valign="top">Date:</td>
    <td><input name="date" type="text" id="datepicker" size="10" value="${date}"></td>
  </tr>
  <tr>
    <td valign="top">Hour/Minute:</td>
    <td><input name="hour" type="text" size="2" maxlength="2" value="${hour}"/> <input name="minute" type="text" size="2" maxlength="2" value="${minute}"/> (military time)</td>
  </tr>
  <tr>
    <td valign="top">Duration:</td>
    <td><input name="duration" type="text" size="10" value="${modifygame.duration}" />format: 2h 30m</td>
  </tr>
</table>

<input type="submit" value="Save"> <input type="submit" name="delete" value="Delete">
</form>

</body>
</html>