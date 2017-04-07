<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Team Sikes</title>
</head>
<body>

<table class="modify">
  <tr>
    <td>

<c:if test="${positions != null}">
  <table class="modify">
    <tr><td>Pitcher:</td><td><c:out value="${positions.pitcher}" /></td></tr>
    <tr><td>Catcher:</td><td><c:out value="${positions.catcher}" /></td></tr>
    <tr><td>1st:</td><td><c:out value="${positions.first}" /></td></tr>
    <tr><td>2nd:</td><td><c:out value="${positions.second}" /></td></tr>
    <tr><td>SS:</td><td><c:out value="${positions.ss}" /></td></tr>
    <tr><td>3rd:</td><td><c:out value="${positions.third}" /></td></tr>
    <tr><td>LF:</td><td><c:out value="${positions.lf}" /></td></tr>
    <tr><td>CF:</td><td><c:out value="${positions.cf}" /></td></tr>
    <tr><td>RF:</td><td><c:out value="${positions.rf}" /></td></tr>
    <tr><td>Bench 1:</td><td><c:out value="${positions.bench1}" /></td></tr>
    <tr><td>Bench 2:</td><td><c:out value="${positions.bench2}" /></td></tr>
  </table>
</c:if>

</td>
<td valign="top" align="right">

<form id="entry" name="entry" action="sikes" method="POST" onsubmit="return validate();">
<table class="modify">
  <tr>
    <td valign="top">Pitcher:</td>
    <td>
      <select name="pitcher">
        <option value="Brady">Brady</option>
        <option value="Brayden">Brayden</option>
        <option value="Garrett">Garrett</option>
        <option value="Nick">Nick</option>
        <option value="Rhett">Rhett</option>
        <option value="Tucker">Tucker</option>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Catcher:</td>
    <td>
      <select name="catcher">
        <option value="Brady">Brady</option>
        <option value="Brayden">Brayden</option>
        <option value="Garrett">Garrett</option>
        <option value="Nick">Nick</option>
        <option value="Rhett">Rhett</option>
        <option value="Tucker">Tucker</option>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Bench1:</td>
    <td>
      <select name="bench1">
      <option value="Brady">Brady</option>
      <option value="Brayden">Brayden</option>
      <option value="Conner">Conner</option>
      <option value="Garrett">Garrett</option>
      <option value="James">James</option>
      <option value="Luke">Luke</option>
      <option value="Mitch">Mitch</option>
      <option value="Nick">Nick</option>
      <option value="Rhett">Rhett</option>
      <option value="Robert">Robert</option>
      <option value="Tucker">Tucker</option>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="top">Bench2:</td>
    <td>
      <select name="bench2">
      <option value="Brady">Brady</option>
      <option value="Brayden">Brayden</option>
      <option value="Conner">Conner</option>
      <option value="Garrett">Garrett</option>
      <option value="James">James</option>
      <option value="Luke">Luke</option>
      <option value="Mitch">Mitch</option>
      <option value="Nick">Nick</option>
      <option value="Rhett">Rhett</option>
      <option value="Robert">Robert</option>
      <option value="Tucker">Tucker</option>
      </select>
    </td>
  </tr>
</table>

<input type="submit" value="Submit">
</form>

</td>
</tr>
</table>

</body>
</html>