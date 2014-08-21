<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Team Add</title>
</head>
<body>

<form id="entry" name="entry" action="add" method="POST">

<table class="modify">
  <tr>
    <td valign="top">Name:</td>
    <td><input type="text" size="33" name="name" /></td>
  </tr>
  <tr>
    <td valign="top">Coach:</td>
    <td>
      <select name="coach">
        <c:forEach items="${coaches}" var="coach">
          <option value="${coach.id}">${coach.firstName} ${coach.lastName}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Sport:</td>
    <td>
      <select name="sport">
        <c:forEach items="${sports}" var="sport">
          <option value="${sport.id}">${sport.name}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Season:</td>
    <td>
      <select name="season">
        <c:forEach items="${seasons}" var="season">
          <option value="${season.id}">${season.name}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Age Group:</td>
    <td>
      <select name="ageGroup">
        <c:forEach items="${agegroups}" var="agegroup">
          <option value="${agegroup.id}">${agegroup.name}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
</table>
<input type="submit" value="Save">
</form>

</body>
</html>