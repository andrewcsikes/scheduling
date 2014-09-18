<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Team - ${modifysport.name} - Modify</title>
</head>
<body>

<form id="entry" name="entry" action="modify" method="POST"  onsubmit="return validate();">
<input type="hidden" name="id" value="${modifysport.id}" />

<table class="modify">
  <tr>
    <td valign="top">Name:</td>
    <td><input type="text" size="33" name="name" value="${modifysport.name}" /></td>
  </tr>
</table>
<input type="submit" value="Save"> <input type="submit" name="delete" value="Delete">
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