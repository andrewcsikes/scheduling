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