<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Important Date - Modify</title>
</head>
<body>

<form id="entry" name="entry" action="modify" method="POST"  onsubmit="return validate();">
<input type="hidden" name="id" value="${message.id}" />

<table class="modify">
  <tr>
    <td valign="top">Date:</td>
    <td><input name="date" type="text" id="datepicker" size="10" value="${date}"></td>
  </tr>
  <tr>
    <td valign="top">Message:</td>
    <td><input name="message" type="text" size="40" maxlength="255" value="${message.message}"/>
  </tr>
  <tr>
    <td>Example:</td>
    <td>4th grade STAR testing tomorrow.</td>
  </tr>
</table>

<input type="submit" value="Save"> <input type="submit" name="delete" value="Delete">
</form>

</body>
</html>