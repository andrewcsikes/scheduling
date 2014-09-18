<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Season Add</title>
	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script>
$(function() {
$( "#datepicker" ).datepicker();
});
</script>

</head>
<body>

<form id="entry" name="entry" action="add" method="POST" onsubmit="return validate();">

<table class="modify">
  <tr>
    <td valign="top">Season Name:</td>
    <td><input type="text" size="33" name="name" /></td>
  </tr>
  <tr>
    <td>Status:</td>
    <td>
      <select name="status">
        <option value="1">Active</option>
        <option value="2">Inactive</option>
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
    <td valign="top">Start Date:</td>
    <td><input name="startDate" type="text" id="datepicker" size="10"></td>
  </tr>
  <tr>
    <td>Apply Scheduling Rules</td>
    <td><input name="applySchedulingRules" type="checkbox" /></td>
  </tr>
</table>
<input type="submit" value="Save">
</form>

<script language="javascript">

function validate() {
  if (document.entry.name.value=="") {
	alert("Season name is required.");
	return false;
  }else {
	return true;
  }
}

</script>

</body>
</html>