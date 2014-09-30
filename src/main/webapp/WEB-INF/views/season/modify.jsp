<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>
<html>
<head>
	<title>Team - ${modifysport.name} - Modify</title>
	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<script>
$(function() {
$( "#datepicker" ).datepicker();
});
</script>

</head>
<body>

<form id="entry" name="entry" action="modify" method="POST"  onsubmit="return validate();">
<input type="hidden" name="id" value="${modifyseason.id}" />

<table class="modify">
  <tr>
    <td valign="top">Name:</td>
    <td><input type="text" size="33" name="name" value="${modifyseason.name}" /></td>
  </tr>
  <tr>
    <td>Status:</td>
    <td>
      <select name="status">
        <option value="1" ${modifyseason.status.code == 1 ? 'selected' : ''}>Active</option>
        <option value="2" ${modifyseason.status.code == 2 ? 'selected' : ''}>Inactive</option>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Sport:</td>
    <td>
      <select name="sport">
        <c:forEach items="${sports}" var="sport">
          <option value="${sport.id}" ${modifyseason.sport.id == sport.id ? 'selected' : ''}>${sport.name}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Start Date:</td>
    <td><input name="startDate" type="text" id="datepicker" size="10" value="${formattedDate}"></td>
  </tr>
  <tr>
    <td valign="top">Apply Scheduling Rules:</td>
    <c:choose>
    <c:when test="${modifyseason.applySchedulingRules}">
    	<td><input name="applyRules" type="checkbox" value="True" checked></td>
    </c:when>
    <c:otherwise>
    	<td><input name="applyRules" type="checkbox" value="True"></td>
    </c:otherwise>
    </c:choose>
  </tr>
</table>
<input type="submit" value="Save"> <input type="submit" name="delete" value="Delete">
</form>

<script language="javascript">

function validate() {
  if (document.entry.name.value=="") {
	alert("Sport name is required.");
	return false;
  }else {
	return true;
  }
}

</script>

</body>
</html>