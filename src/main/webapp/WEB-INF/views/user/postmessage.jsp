<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<title>VASA Scheduler</title>
	
	<link href="/scheduling/styles/style.css" rel="stylesheet" type="text/css" />
	
</head>
<body>

<div class="loginbox">
	<h3>Set/Send Global Message</h3>
	<form id="postmessage" name="postmessage" action="/scheduling/user/postmessage" method="POST" onsubmit="return validate();">
		<label for="Message">Message</label>
		<textarea rows="10" cols="35" name="message"></textarea>
		<div class="form-actions">
			<input id="proceed" name="submit" class="btn btn-primary" type="submit" value="Post Message">
			&nbsp;&nbsp;&nbsp;<input id="proceed" name="submit" class="btn btn-primary" type="submit" value="Delete Message">
			<br /> <br /><input id="proceed" name="submit" class="btn btn-primary" type="submit" value="Email Baseball">
			&nbsp;&nbsp;&nbsp;<input id="proceed" name="submit" class="btn btn-primary" type="submit" value="Email Softball">
			<br /> <br /><input id="proceed" name="submit" class="btn btn-primary" type="submit" value="Text Baseball">
			&nbsp;&nbsp;&nbsp;<input id="proceed" name="submit" class="btn btn-primary" type="submit" value="Text Softball">
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
