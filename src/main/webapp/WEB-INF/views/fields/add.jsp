<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Sport Add</title>
</head>
<body>

<form id="entry" name="entry" action="add" method="POST" onsubmit="return validate();">

<table class="modify">
  <tr>
    <td valign="top">Field Name:</td>
    <td><input type="text" size="33" name="name" /></td>
  </tr>
  <tr>
    <td valign="top">Sport:</td>
    <td>
      <select name="sport">
        <c:forEach items="${sports}" var="sport">
          <option value="${sport.id}">${sport.name}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td>Status:</td>
    <td>
      <select name="status">
        <option value="1">Active</option>
        <option value="2">Inactive</option>
      </select>
    </td>
  </tr>
  <tr>
    <td>Lights</td>
    <td><input name="lights" type="checkbox" /></td>
  </tr>
  <tr>
    <td>Location</td>
    <td><input name="location" type="text" /></td>
  </tr>
 </table>
 
 <hr width="350px;" align="left">
 <table>
  <tr>
    <td width="100px">Sunday</td>
    <td>
      <table style="border:=0px">
        <tr>
          <td>Available</td>
          <td><input name="sunday" type="checkbox" /></td>
        </tr>
        <tr>
          <td>Start Time</td>
          <td><input type="text" size="4" name="sundayStart" />Hours only (24 Hour)</td>
        </tr>
        <tr>
          <td>End Time</td>
          <td><input type="text" size="4" name="sundayEnd" />Hours only (24 Hour)</td>
        </tr>
      </table>
    </td>
  </tr>
 </table>
 
 <hr width="350px;" align="left">
 <table>
  <tr>
    <td width="100px">Monday</td>
    <td>
      <table style="border:=0px">
        <tr>
          <td>Available</td>
          <td><input name="monday" type="checkbox" /></td>
        </tr>
        <tr>
          <td>Start Time</td>
          <td><input type="text" size="4" name="mondayStart" />Hours only (24 Hour)</td>
        </tr>
        <tr>
          <td>End Time</td>
          <td><input type="text" size="4" name="mondayEnd" />Hours only (24 Hour)</td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<hr width="350px;" align="left">
 <table>
  <tr>
    <td width="100px">Tuesday</td>
    <td>
      <table style="border:=0px">
        <tr>
          <td>Available</td>
          <td><input name="tuesday" type="checkbox" /></td>
        </tr>
        <tr>
          <td>Start Time</td>
          <td><input type="text" size="4" name="tuesdayStart" />Hours only (24 Hour)</td>
        </tr>
        <tr>
          <td>End Time</td>
          <td><input type="text" size="4" name="tuesdayEnd" />Hours only (24 Hour)</td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<hr width="350px;" align="left">
 <table>
  <tr>
    <td width="100px">Wednesday</td>
    <td>
      <table style="border:=0px">
        <tr>
          <td>Available</td>
          <td><input name="wednesday" type="checkbox" /></td>
        </tr>
        <tr>
          <td>Start Time</td>
          <td><input type="text" size="4" name="wednesdayStart" />Hours only (24 Hour)</td>
        </tr>
        <tr>
          <td>End Time</td>
          <td><input type="text" size="4" name="wednesdayEnd" />Hours only (24 Hour)</td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<hr width="350px;" align="left">
 <table>
  <tr>
    <td width="100px">Thursday</td>
    <td>
      <table style="border:=0px">
        <tr>
          <td>Available</td>
          <td><input name="thursday" type="checkbox" /></td>
        </tr>
        <tr>
          <td>Start Time</td>
          <td><input type="text" size="4" name="thursdayStart" />Hours only (24 Hour)</td>
        </tr>
        <tr>
          <td>End Time</td>
          <td><input type="text" size="4" name="thursdayEnd" />Hours only (24 Hour)</td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<hr width="350px;" align="left">
 <table>
  <tr>
    <td width="100px">Friday</td>
    <td>
      <table style="border:=0px">
        <tr>
          <td>Available</td>
          <td><input name="friday" type="checkbox" /></td>
        </tr>
        <tr>
          <td>Start Time</td>
          <td><input type="text" size="4" name="fridayStart" />Hours only (24 Hour)</td>
        </tr>
        <tr>
          <td>End Time</td>
          <td><input type="text" size="4" name="fridayEnd" />Hours only (24 Hour)</td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<hr width="350px;" align="left">
 <table>
  <tr>
    <td width="100px">Saturday</td>
    <td>
      <table style="border:=0px">
        <tr>
          <td>Available</td>
          <td><input name="saturday" type="checkbox" /></td>
        </tr>
        <tr>
          <td>Start Time</td>
          <td><input type="text" size="4" name="saturdayStart" />Hours only (24 Hour)</td>
        </tr>
        <tr>
          <td>End Time</td>
          <td><input type="text" size="4" name="saturdayEnd" />Hours only (24 Hour)</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<hr width="350px;" align="left">

<input type="submit" value="Save">
</form>

<script language="javascript">

function validate() {
  if (document.entry.name.value=="") {
	alert("Field name is required.");
	return false;
  }else if (document.entry.location.value=="") {
		alert("Location name is required.");
		return false;
  }else {
	return true;
  }
}

</script>

</body>
</html>