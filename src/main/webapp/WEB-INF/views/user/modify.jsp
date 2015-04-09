<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>My Account</title>
</head>
<body>

<form id="entry" name="entry" action="modify" method="POST" onsubmit="return validate();">
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
          <option value="4" ${modifyuser.userType.code == 4 ? 'selected' : ''}>Commissioner</option>
        </select>
      </td>
    </tr>
    <tr>
      <td>User Status:</td>
      <td>
        <select name="status">
          <option value="1" ${modifyuser.status.code == 1 ? 'selected' : ''}>Active</option>
          <option value="2" ${modifyuser.status.code == 2 ? 'selected' : ''}>Inactive</option>
          <option value="3" ${modifyuser.status.code == 3 ? 'selected' : ''}>Request</option>
        </select>
      </td>
    </tr>
  </c:if>
  <tr>
    <td>Username:</td>
    <td><input type="text" name="username" value="${modifyuser.userName}" /></td>
  </tr>
  <tr>
    <td>Password:</td>
    <td><input type="password" name="password" value="${modifyuser.password}" /></td>
  </tr>
  <tr>
    <td valign="top">Opt out of Email Notifications:</td>
    <c:choose>
    <c:when test="${modifyuser.skipNotifications}">
    	<td><input name="notifications" type="checkbox" value="True" checked></td>
    </c:when>
    <c:otherwise>
    	<td><input name="notifications" type="checkbox" value="True"></td>
    </c:otherwise>
    </c:choose>
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
<!--   <tr> -->
<!--       <td>Cell Service:</td> -->
<!--       <td> -->
<!--         <select name="carrier"> -->
<%--           <option value="" ${modifyuser.carrier.code == null ? 'selected' : ''}>Select</option> --%>
<%--           <option value="1" ${modifyuser.carrier.code == 1 ? 'selected' : ''}>AT&T</option> --%>
<%--           <option value="2" ${modifyuser.carrier.code == 2 ? 'selected' : ''}>Sprint</option> --%>
<%--           <option value="3" ${modifyuser.carrier.code == 3 ? 'selected' : ''}>T-Mobile</option> --%>
<%--           <option value="4" ${modifyuser.carrier.code == 4 ? 'selected' : ''}>Verizon</option> --%>
<%--           <option value="5" ${modifyuser.carrier.code == 5 ? 'selected' : ''}>Nextel</option> --%>
<%--           <option value="6" ${modifyuser.carrier.code == 6 ? 'selected' : ''}>Cricket</option> --%>
<!--         </select> -->
<!--       </td> -->
<!--     </tr> -->
  <tr>
    <td>Phone Number:</td>
    <td><input type="text" size="40" name="phone" value="${modifyuser.phone}"></td>
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

<script language="javascript">
function validate() {
  if (document.entry.username.value=="") {
	alert("Username is required.");
	return false;
  }else if (document.entry.password.value=="") {
		alert("Password is required.");
		return false;
  }else if (document.entry.firstName.value=="") {
		alert("First Name is required.");
		return false;
  }else if (document.entry.lastName.value=="") {
		alert("Last Name is required.");
		return false;
  }else if (document.entry.emailAddress.value=="") {
		alert("Email Address is required.");
		return false;
  }else if (document.entry.phone.value=="") {
		alert("Phone Number is required.");
		return false;
  }else {
	return true;
  }
}
</script>

</body>
</html>