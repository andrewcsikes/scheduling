<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<title>VASA Scheduler</title>
	
	<link href="/styles/style.css" rel="stylesheet" type="text/css" />
	
</head>
<body>

<div class="header">
<img src="/images/banner.png">
</div>

<h3 class="error">${loginerror}</h3>

<div class="loginbox">
	<h3>Welcome!</h3>
	<form id="login" name="login" action="/user/home" method="POST" onsubmit="return validate();">
		<label for="username">Username</label>
		<input id="username" type="text" name="username" style="width: 150px">
		<label for="password">Password</label>
		<input id="password" type="password" name="password" style="width: 150px" required>
		<p><a href="/user/forgotpassword">Forgot Password</a></p>
		<p><a href="/user/requestaccount">Request an Account</a></p>
		<div class="form-actions">
			<input id="proceed" class="btn btn-primary" type="submit" value="Submit">
		</div>
	</form>
</div>

<div class="loginbox">
<p align="center"><a href="/schedule/calendar/quick">Schedule Calendar View</a></p>
<p align="center"><a href="/schedule/list/quick">Schedule List View</a></p>
<p align="center"><a href="/files/home">Facility Usage Fees</a></p>
</div>

<script language="javascript">

function validate() {
  if (document.login.username.value=="") {
	alert("You did not fill in your Username.");
	return false;
  }else if (document.login.password.value=="") {
	alert("You did not fill in your password.");
	return false;
  }
  else {
	return true;
  }
}

</script>

</body>
</html>
