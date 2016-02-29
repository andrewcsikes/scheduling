<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Sport Add</title>
</head>
<body>

<form id="entry" name="entry" action="add" method="POST" onsubmit="return validate();">

<table class="modify">
  <tr>
    <td valign="top">Sport Name:</td>
    <td><input type="text" size="33" name="name" /></td>
  </tr>
  <tr>
    <td valign="top">Lock Before:</td>
    <td>
      <select name="dayOfWeek">
        <c:forEach items="${days}" var="day">
          <option value="${day.key}">${day.value}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Non VASA Teams:</td>
    <td>
      <select name="nonVasaDayOfWeek">
        <c:forEach items="${days}" var="day">
          <option value="${day.key}">${day.value}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Unlock Time:</td>
    <td><input type="text" size="2" maxLength='2' name="time" />(24H format)</td>
  </tr>
</table>
<input type="submit" value="Save">
</form>

<script language="javascript">

function validate() {
  if (document.entry.name.value=="") {
	alert("Sport name is required.");
	return false;
  }else {
	return true;
  }
}

</script>

</body>
</html>