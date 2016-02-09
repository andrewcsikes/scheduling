<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Important Date Add</title>
	
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
    <td valign="top">Date:</td>
    <td><input name="date" type="text" id="datepicker" size="10"></td>
  </tr>
  <tr>
    <td valign="top">Message:</td>
    <td><input name="message" type="text" size="20" maxlength="255"/></td>
  </tr>
</table>
<input type="submit" value="Save">
</form>

<script language="javascript">

function validate() {
  if (document.entry.date.value=="") {
	alert("Date is required.");
	return false;
  }else if (document.entry.message.value=="") {
		alert("Message is required.");
		return false;
  }else {
	return true;
  }
}

</script>

</body>
</html>