<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Game Add</title>
	
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
    <td valign="top">Field:</td>
    <td>
      <select name="field">
        <c:forEach items="${fields}" var="field">
          <option value="${field.name}">${field.name}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Description:</td>
    <td><input name="description" type="text" size="40" /></td>
  </tr>
  <tr>
    <td valign="top">Date:</td>
    <td><input name="date" type="text" id="datepicker" size="10"></td>
  </tr>
  <tr>
    <td valign="top">Hour/Minute:</td>
    <td><input name="hour" type="text" size="2" maxlength="2"/> <input name="minute" type="text" size="2" maxlength="2"/> (military time)</td>
  </tr>
  <tr>
    <td valign="top">Duration:</td>
    <td><input name="duration" type="text" size="10" />format: 2h 30m</td>
  </tr>
</table>
<input type="submit" value="Save">
</form>

<script language="javascript">

function validate() {
  if (document.entry.date.value=="") {
	alert("Date is required.");
	return false;
  }else if (document.entry.hour.value=="") {
	alert("Hour is required.");
	return false;
  }else if (document.entry.minute.value=="") {
		alert("Minute is required.");
		return false;
  }else if (document.entry.duration.value=="") {
		alert("Duration is required.");
		return false;
  }else {
	return true;
  }
}

</script>

</body>
</html>