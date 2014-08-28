<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>My Account</title>
</head>
<body>

<form id="entry" name="entry" action="add" method="POST">

<table class="modify">
  <tr class="header">
    <td colspan="2">Login Information</td>
  </tr>
  <tr>
    <td>User Type:</td>
    <td>
      <select name="userType">
        <option value="1">Admin</option>
        <option value="2">Coach</option>
        <option value="3">User</option>
        <option value="4">Commissioner</option>
      </select>
    </td>
  </tr>
  <tr>
    <td>User Status:</td>
    <td>
      <select name="status">
        <option value="1">Active</option>
        <option value="2">Inactive</option>
      </select>
    </td>
  </tr>
  <tr>
    <td>Username:</td>
    <td><input type="text" name="username" /></td>
  </tr>
  <tr>
    <td>Password:</td>
    <td><input type="password" name="password" /></td>
  </tr>
</table>


<table class="modify">
  <tr class="header">
    <td colspan="2">User Information</td>
  </tr>
  <tr>
    <td>First Name:</td>
    <td><input type="text" name="firstName" /></td>
  </tr>
  <tr>
    <td>Last Name:</td>
    <td><input type="text" name="lastName" /></td>
  </tr>
  <tr>
    <td>Email Address:</td>
    <td><input type="text" size="40" name="emailAddress" ></td>
  </tr>
  <tr>
    <td>Phone Number:</td>
    <td><input type="text" size="40" name="phone" ></td>
  </tr>
</table>
<table class="modify">
  <tr class="header">
    <td colspan="2">Address</td>
  </tr>
  <tr>
    <td valign="top">Address:</td>
    <td><input type="text" size="33" name="address1" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="text" size="33" name="address2" /></td>
  </tr>
  <tr>
    <td>City:</td>
    <td><input type="text" name="city" /></td>
  </tr>
  <tr>
    <td>State:</td>
    <td>
      <select name="state">
        <c:forEach items="${states}" var="state">
          <option value="${state.key}">${state.value}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td>Postal Code:</td>
    <td><input type="text" name="postalCode" /></td>
  </tr>
</table>
<input type="submit" value="Save">
</form>

</body>
</html>