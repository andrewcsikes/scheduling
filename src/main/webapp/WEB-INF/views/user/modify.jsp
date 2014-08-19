<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>My Account</title>
</head>
<body>

<form id="entry" name="entry" action="modify" method="POST">
<input type="hidden" name="id" value="${modifyuser.id}" />

<table class="modify">
  <tr class="header">
    <td colspan="2">Login Information</td>
  </tr>
  <c:if test="${user.userType.code==1}">
    <tr>
      <td>User Type:</td>
      <td>
        <select name="userType">
          <option value="1" ${modifyuser.userType.code == 1 ? 'selected' : ''}>Admin</option>
          <option value="2" ${modifyuser.userType.code == 2 ? 'selected' : ''}>Coach</option>
          <option value="3" ${modifyuser.userType.code == 3 ? 'selected' : ''}>User</option>
        </select>
      </td>
    </tr>
    <tr>
      <td>User Status:</td>
      <td>
        <select name="status">
          <option value="1" ${modifyuser.status.code == 1 ? 'selected' : ''}>Active</option>
          <option value="2" ${modifyuser.status.code == 2 ? 'selected' : ''}>Inactive</option>
        </select>
      </td>
    </tr>
  </c:if>
  <tr>
    <td>Username:</td>
    <td><input type="text" name="username" value="${modifyuser.username}" /></td>
  </tr>
  <tr>
    <td>Password:</td>
    <td><input type="password" name="password" value="${modifyuser.password}" /></td>
  </tr>
</table>


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