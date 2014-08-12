<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>My Account</title>
</head>
<body>

<form id="entry" name="entry" action="modify" method="POST">
<table class="modify">
  <tr class="header">
    <td colspan="2">User Information</td>
  </tr>
  <tr>
    <td>First Name:</td>
    <td><input type="text" name="firstName" value="${modifyuser.firstName}" /></td>
  </tr>
  <tr>
    <td>Last Name:</td>
    <td><input type="text" name="lastName" value="${modifyuser.lastName}" /></td>
  </tr>
  <tr>
    <td>Email Address:</td>
    <td><input type="text" size="40" name="emailAddress" value="${modifyuser.emailAddress}" /></td>
  </tr>
</table>
<table class="modify">
  <tr class="header">
    <td colspan="2">Address</td>
  </tr>
  <tr>
    <td valign="top">Address:</td>
    <td><input type="text" size="33" name="address1" value="${modifyuser.address1}" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="text" size="33" name="address2" value="${modifyuser.address2}" /></td>
  </tr>
  <tr>
    <td>City:</td>
    <td><input type="text" name="city" value="${modifyuser.city}" /></td>
  </tr>
  <tr>
    <td>State:</td>
    <td>
      <select name="state">
        <c:forEach items="${states}" var="state">
          <option value="${state.key}" ${modifyuser.state == state.key ? 'selected' : ''}>${state.value}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td>Postal Code:</td>
    <td><input type="text" name="postalCode" value="${modifyuser.postalCode}" /></td>
  </tr>
</table>
<input type="submit" value="Save">
</form>

</body>
</html>