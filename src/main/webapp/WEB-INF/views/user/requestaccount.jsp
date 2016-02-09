<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<title>VASA Scheduler</title>
	
	<link href="/scheduling/styles/style.css" rel="stylesheet" type="text/css" />
	
</head>
<body>

<div class="header">
<img src="/scheduling/images/banner.png">
</div>

<form id="entry" name="entry" action="requestaccount" method="POST"  onsubmit="return validate();">

<table class="modify">
<tr>
  <td>

<table class="modify">
  <tr class="header">
    <td colspan="2">Login Information</td>
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


</td>
<td valign="top">

<table class="modify">
  <tr class="header">
    <td colspan="2">Purpose</td>
  </tr>
  <tr>
    <td>Sport:</td>
    <td>
      <select name="sport">
        <option value="baseball">Baseball</option>
        <option value="softball">Softball</option>
        <option value="other">Other</option>
      </select>
    </td>
  </tr>
  <tr>
    <td>General Comments:</td>
    <td><textarea rows="10" cols="35" name="comments"></textarea></td>
  </tr>
</table>

</td>
</tr>
</table>


<input type="submit" value="Save">
<input id="cancel" onclick="history.back();" type="button" value="Cancel">
</form>

<script language="javascript">
function validate() {
  if (document.entry.username.value=="") {
	alert("Username is required.");
	return false;
  }else if (document.entry.password.value=="") {
		alert("Password is required.");
		return false;
  }else if (document.entry.emailAddress.value=="") {
		alert("Email Address is required.");
		return false;
  }else if (document.entry.phone.value=="") {
		alert("Phone Number is required.");
		return false;
  }else if (document.entry.firstName.value=="") {
		alert("First Name is required.");
		return false;
  }else if (document.entry.lastName.value=="") {
		alert("Last Name is required.");
		return false;
  }else {
	return true;
  }
}
</script>

</body>
</html>