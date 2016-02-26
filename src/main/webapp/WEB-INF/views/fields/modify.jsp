<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Team - ${modifyfield.name} - Modify</title>
</head>
<body>

<form id="entry" name="entry" action="modify" method="POST"  onsubmit="return validate();">
<input type="hidden" name="id" value="${modifyfield.id}" />

<table class="modify">
  <tr>
    <td valign="top">Name:</td>
    <td><input type="text" size="33" name="name" value="${modifyfield.name}" /></td>
  </tr>
  <tr>
    <td valign="top">Sport:</td>
    <td>
      <select name="sport">
        <c:forEach items="${sports}" var="sport">
          <option value="${sport.id}" ${modifyfield.sport.id == sport.id ? 'selected' : ''}>${sport.name}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td>Status:</td>
    <td>
      <select name="status">
        <option value="1" ${modifyfield.status.code == 1 ? 'selected' : ''}>Active</option>
        <option value="2" ${modifyfield.status.code == 2 ? 'selected' : ''}>Inactive</option>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Lights:</td>
    <c:choose>
    <c:when test="${modifyfield.lights}">
    	<td><input name="lights" type="checkbox" checked></td>
    </c:when>
    <c:otherwise>
    	<td><input name="lights" type="checkbox"></td>
    </c:otherwise>
    </c:choose>
  </tr>
</table>

 <hr width="350px;" align="left">
 <table width="100%">
 <tr>
 <td width="50%">
 
 <table>
  <tr>
    <td width="100px">Sunday</td>
    <td>
      <table style="border:=0px">
        <tr>
          <td>Available</td>
          <td>
		    <c:choose>
              <c:when test="${modifyfield.availableSunday}">
    	        <input name="sunday" type="checkbox" checked>
              </c:when>
              <c:otherwise>
    	        <input name="sunday" type="checkbox">
              </c:otherwise>
            </c:choose>
		  </td>
        </tr>
        <tr>
          <td>Start Time</td>
          <td><input type="text" size="4" name="sundayStart" value="${modifyfield.sundayStartTime}"/>Hours only (24 Hour)</td>
        </tr>
        <tr>
          <td>End Time</td>
          <td><input type="text" size="4" name="sundayEnd" value="${modifyfield.sundayEndTime}"/>Hours only (24 Hour)</td>
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
          <td>
		    <c:choose>
              <c:when test="${modifyfield.availableMonday}">
    	        <input name="monday" type="checkbox" checked>
              </c:when>
              <c:otherwise>
    	        <input name="monday" type="checkbox">
              </c:otherwise>
            </c:choose>
		  </td>
        </tr>
        <tr>
          <td>Start Time</td>
          <td><input type="text" size="4" name="mondayStart" value="${modifyfield.mondayStartTime}"/>Hours only (24 Hour)</td>
        </tr>
        <tr>
          <td>End Time</td>
          <td><input type="text" size="4" name="mondayEnd" value="${modifyfield.mondayEndTime}"/>Hours only (24 Hour)</td>
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
          <td>
		    <c:choose>
              <c:when test="${modifyfield.availableTuesday}">
    	        <input name="tuesday" type="checkbox" checked>
              </c:when>
              <c:otherwise>
    	        <input name="tuesday" type="checkbox">
              </c:otherwise>
            </c:choose>
		  </td>
        </tr>
        <tr>
          <td>Start Time</td>
          <td><input type="text" size="4" name="tuesdayStart" value="${modifyfield.tuesdayStartTime}"/>Hours only (24 Hour)</td>
        </tr>
        <tr>
          <td>End Time</td>
          <td><input type="text" size="4" name="tuesdayEnd" value="${modifyfield.tuesdayEndTime}"/>Hours only (24 Hour)</td>
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
          <td>
		    <c:choose>
              <c:when test="${modifyfield.availableWednesday}">
    	        <input name="wednesday" type="checkbox" checked>
              </c:when>
              <c:otherwise>
    	        <input name="wednesday" type="checkbox">
              </c:otherwise>
            </c:choose>
		  </td>
        </tr>
        <tr>
          <td>Start Time</td>
          <td><input type="text" size="4" name="wednesdayStart" value="${modifyfield.wednesdayStartTime}"/>Hours only (24 Hour)</td>
        </tr>
        <tr>
          <td>End Time</td>
          <td><input type="text" size="4" name="wednesdayEnd" value="${modifyfield.wednesdayEndTime}"/>Hours only (24 Hour)</td>
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
          <td>
		    <c:choose>
              <c:when test="${modifyfield.availableThursday}">
    	        <input name="thursday" type="checkbox" checked>
              </c:when>
              <c:otherwise>
    	        <input name="thursday" type="checkbox">
              </c:otherwise>
            </c:choose>
		  </td>
        </tr>
        <tr>
          <td>Start Time</td>
          <td><input type="text" size="4" name="thursdayStart" value="${modifyfield.thursdayStartTime}"/>Hours only (24 Hour)</td>
        </tr>
        <tr>
          <td>End Time</td>
          <td><input type="text" size="4" name="thursdayEnd" value="${modifyfield.thursdayEndTime}"/>Hours only (24 Hour)</td>
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
          <td>
		    <c:choose>
              <c:when test="${modifyfield.availableFriday}">
    	        <input name="friday" type="checkbox" checked>
              </c:when>
              <c:otherwise>
    	        <input name="friday" type="checkbox">
              </c:otherwise>
            </c:choose>
		  </td>
        </tr>
        <tr>
          <td>Start Time</td>
          <td><input type="text" size="4" name="fridayStart" value="${modifyfield.fridayStartTime}"/>Hours only (24 Hour)</td>
        </tr>
        <tr>
          <td>End Time</td>
          <td><input type="text" size="4" name="fridayEnd" value="${modifyfield.fridayEndTime}"/>Hours only (24 Hour)</td>
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
          <td>
		    <c:choose>
              <c:when test="${modifyfield.availableSaturday}">
    	        <input name="saturday" type="checkbox" checked>
              </c:when>
              <c:otherwise>
    	        <input name="saturday" type="checkbox">
              </c:otherwise>
            </c:choose>
		  </td>
        </tr>
        <tr>
          <td>Start Time</td>
          <td><input type="text" size="4" name="saturdayStart" value="${modifyfield.saturdayStartTime}"/>Hours only (24 Hour)</td>
        </tr>
        <tr>
          <td>End Time</td>
          <td><input type="text" size="4" name="saturdayEnd" value="${modifyfield.saturdayEndTime}"/>Hours only (24 Hour)</td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<hr width="350px;" align="left">

</td>
<td width="50%" valign="top">
<table id="ruleTable">
<tr><td><h3>Scheduling Rules</h3></td></tr>
<tr><td><p>Restrict practicing for an Age Group for a time (Week days only)</p></td></tr>

<c:set var="count" scope="page" value="1"/>

<c:forEach items="${rules}" var="rule">
  <tr>
    <td>
      <input type='hidden' name='ruleId${count}' value='${rule.id}'>
      
      Restrict <select name='ageGroup${count}'>
        <c:forEach items="${ages}" var="agegroup">
          <option value="${agegroup.id}" ${rule.ageGroup.id == agegroup.id ? 'selected' : ''}>${agegroup.name}</option>
        </c:forEach>
      </select>
        
      <select name='operation${count}'>
        <c:forEach items='${operations}' var='oper'>
          <option value='${oper.key}' ${rule.operation.code == oper.key ? 'selected' : ''}>${oper.value}</option>
        </c:forEach>
      </select>
        
      <input type='text' size='2' maxlength='2' name='time${count}' value="${rule.time}"> (24H format)
      <input type="checkbox" name='delete${count}'> Delete

  <c:set var="count" value="${count + 1}" scope="page"/>

</c:forEach>

</table>
<a><img src='/scheduling/images/plus-icon.png' /> Add Rule</a>
</td>
</tr>
</table>

<input type="submit" value="Save"> <input type="submit" name="delete" value="Delete">
</form>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script>
$(document).ready(function(){
	var count=${count};
    $("a").click(function(){
        //add a row to the div
        
        var ag = "Restrict <select name='ageGroup"+count+"'>";
        ag += "<c:forEach items='${ages}' var='ageGroup'>";
        ag +="<option value='${ageGroup.id}'>${ageGroup.name}</option>";
        ag +="</c:forEach></select>";
        
        var op = "<select name='operation"+count+"'>";
        op += "<c:forEach items='${operations}' var='oper'>";
        op +="<option value='${oper.key}'>${oper.value}</option>";
        op +="</c:forEach></select>";
        
        var time = "<input type='text' size='2' maxlength='2' name='time"+count+"'>";
        
        $('#ruleTable > tbody:last-child').append("<tr><td>"+ag+" "+op+" "+time+" (24H format)</td></tr>");
        count++;
    });
});
</script>

<script language="javascript">

function validate() {
  if (document.entry.name.value=="") {
	alert("Field name is required.");
	return false;
  }else {
	return true;
  }
}

</script>

</body>
</html>