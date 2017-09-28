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

<div class="loginbox">
	<h3>Welcome!</h3>
	<form id="login" name="login" action="/user/forgotpassword" method="POST" onsubmit="return validate();">
		<label for="username">Username</label>
		<input id="username" type="text" name="username" style="width: 150px">
		<div class="form-actions">
			<input id="proceed" class="btn btn-primary" type="submit" value="Forgot Password">
		</div>
	</form>
</div>

<script language="javascript">

function validate() {
  if (document.login.username.value=="") {
	alert("You did not fill in your Username.");
	return false;
  }
  else {
	return true;
  }
}

</script>

</body>
</html>
