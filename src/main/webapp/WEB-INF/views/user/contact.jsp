<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<title>VASA Scheduler</title>
	
	<link href="/styles/style.css" rel="stylesheet" type="text/css" />
	
</head>
<body>

<div class="loginbox">
	<h3>Send Private Message To </br> ${name}!</h3>
	<form id="contact" name="contact" action="/user/contact" method="POST" onsubmit="return validate();">
		<input type="hidden" name="username" value="${username}" />
		<label for="Message">Message</label>
		<textarea rows="10" cols="35" name="message"></textarea>
		<div class="form-actions">
			<input id="proceed" class="btn btn-primary" type="submit" value="Send Message">
		</div>
	</form>
</div>

<script language="javascript">

function validate() {
  return true;
}

</script>

</body>
</html>
