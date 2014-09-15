<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Team - ${modifyteam.name} - Modify</title>
</head>
<body>

<form id="entry" name="entry" action="modify" method="POST"  onsubmit="return validate();">
<input type="hidden" name="id" value="${modifyteam.id}" />

<table class="modify">
  <tr>
    <td valign="top">Name:</td>
    <td><input type="text" size="33" name="name" value="${modifyteam.name}" /></td>
  </tr>
  <tr>
    <td>Classification:</td>
    <td>
      <select name="classification">
        <option value="1" ${modifyteam.classification.code == 1 ? 'selected' : ''}>VASA</option>
        <option value="2" ${modifyteam.classification.code == 2 ? 'selected' : ''}>NON-VASA</option>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Coach:</td>
    <td>
      <select name="coach">
        <c:forEach items="${coaches}" var="coach">
          <option value="${coach.id}" ${modifyteam.coach.id == coach.id ? 'selected' : ''}>${coach.firstName} ${coach.lastName}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
<!--   <tr> -->
<!--     <td valign="top">Sport:</td> -->
<!--     <td> -->
<!--       <select name="sport"> -->
<%--         <c:forEach items="${sports}" var="sport"> --%>
<%--           <option value="${sport.id}" ${modifyteam.sport.id == sport.id ? 'selected' : ''}>${sport.name}</option> --%>
<%--         </c:forEach> --%>
<!--       </select> -->
<!--     </td> -->
<!--   </tr> -->
  <tr>
    <td valign="top">Season:</td>
    <td>
      <select name="season">
        <c:forEach items="${seasons}" var="season">
          <option value="${season.id}" ${modifyteam.season.id == season.id ? 'selected' : ''}>${season.sport.name} - ${season.name}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Age Group:</td>
    <td>
      <select name="ageGroup">
        <c:forEach items="${agegroups}" var="agegroup">
          <option value="${agegroup.id}" ${modifyteam.ageGroup.id == agegroup.id ? 'selected' : ''}>${agegroup.name}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Practice Limit:</td>
    <td><input type="text" size="5" name="practiceLimit" value="${modifyteam.practiceLimit}" /> hours/day</td>
  </tr>
  <tr>
    <td valign="top">Weekly Practice Limit:</td>
    <td><input type="text" size="5" name="weeklyPracticeLimit" value="${modifyteam.weeklyPracticeLimit}" /> hours/week</td>
  </tr>
</table>
<input type="submit" value="Save">
</form>

<script language="javascript">

function validate() {
  if (document.entry.name.value=="") {
	alert("Team name is required.");
	return false;
  }else {
	return true;
  }
}

</script>

</body>
</html>