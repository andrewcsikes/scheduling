<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VASA Field Scheduling</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">

	<style>
		th#date::before{
			content: "+";
			font-size: 20px;
			position: absolute;
			text-align: center;
			right: 30%;
		}
		th#date-clicked::before{
			content: "+";
			font-size: 20px;
			position: absolute;
			text-align: center;
			right: 30%;
			transform: rotate(315deg);
		}
	</style>

	<script>
	$(function() {
		$( "#datepicker" ).datepicker();
		$('table#schedule').find('tbody').hide();
		$('table#schedule').find('tr#fields').hide();
	});
	
	function showHide(div){
		var $table = $(div).closest('table');
		var $rows = $table.find('tbody');
		var $header = $table.find('tr#fields');
		var $th = $(div).find('th');

	    if($rows.is(':visible')) {
	      $rows.hide();
		  $header.hide();
		  $th.attr('id', 'date');
	    }else{
	      $rows.show();
		  $header.show();
		  $th.attr('id', 'date-clicked');
	    }
  	}
$(document).ready(function(){
		
		hideDay(2);
		hideDay(3);
		hideDay(4);
		hideDay(5);
		hideDay(6);
		
	});
	
	function hideDay(day){

		$("table.list").eq(day).find("tr:nth-child(1)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(1)").find("th:nth-child(2)").hide();
		
		$("table.list").eq(day).find("tr:nth-child(2)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(2)").find("th:nth-child(1)").hide();

		$("table.list").eq(day).find("tr:nth-child(3)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(3)").find("th:nth-child(1)").hide();

		$("table.list").eq(day).find("tr:nth-child(4)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(4)").find("th:nth-child(1)").hide();

		$("table.list").eq(day).find("tr:nth-child(5)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(5)").find("th:nth-child(1)").hide();

		$("table.list").eq(day).find("tr:nth-child(6)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(6)").find("th:nth-child(1)").hide();

		$("table.list").eq(day).find("tr:nth-child(7)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(7)").find("th:nth-child(1)").hide();

		$("table.list").eq(day).find("tr:nth-child(8)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(8)").find("th:nth-child(1)").hide();

		$("table.list").eq(day).find("tr:nth-child(9)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(9)").find("th:nth-child(1)").hide();

		$("table.list").eq(day).find("tr:nth-child(10)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(10)").find("th:nth-child(1)").hide();

		$("table.list").eq(day).find("tr:nth-child(11)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(11)").find("th:nth-child(1)").hide();

		$("table.list").eq(day).find("tr:nth-child(12)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(12)").find("th:nth-child(1)").hide();

		$("table.list").eq(day).find("tr:nth-child(13)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(13)").find("th:nth-child(1)").hide();

		$("table.list").eq(day).find("tr:nth-child(14)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(14)").find("th:nth-child(1)").hide();

		$("table.list").eq(day).find("tr:nth-child(15)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(15)").find("th:nth-child(1)").hide();	
	}
	</script>

</head>
<body>

<c:if test="${error != null}">
  <h3><font color="red">${error}</font></h3>
</c:if>

<%
java.util.ArrayList<com.vasa.scheduling.domain.Fields> fields = (java.util.ArrayList<com.vasa.scheduling.domain.Fields>)request.getAttribute("fields");
boolean anylocked = (Boolean)request.getAttribute("locked");
java.text.SimpleDateFormat format1 = new java.text.SimpleDateFormat("MM/dd/yyyy");
java.text.SimpleDateFormat format2 = new java.text.SimpleDateFormat("MMM dd, yyyy");

java.util.Calendar scheduleDay = java.util.Calendar.getInstance();
scheduleDay.setTime((java.util.Date)request.getAttribute("sunday"));

java.util.Calendar addDay = java.util.Calendar.getInstance();
addDay.setTime((java.util.Date)request.getAttribute("sunday"));
addDay.add(java.util.Calendar.DAY_OF_MONTH, -7);

com.vasa.scheduling.domain.User user = (com.vasa.scheduling.domain.User)request.getAttribute("user");
if(user != null && user.getTeam() != null){
	for(com.vasa.scheduling.domain.Fields field:fields){
		boolean locked = (Boolean)request.getAttribute(field.getName()+"locked");
		if(locked){out.println("<p><b><font color='red'>Scheduling is locked for "+field.getName()+" at this time.</font></b></p>");}
	}
}

if((user.getUserType().equals(com.vasa.scheduling.enums.UserType.ADMIN) ||
		user.getUserType().equals(com.vasa.scheduling.enums.UserType.COMMISSIONER))){

%>

<form id="entry" name="entry" action="/schedule/calendar/add" method="POST">
<table class="search">
<tbody>
<tr><td colspan="11">Commissioner Quick Practice Entry</td></tr>
<tr>
    <td valign="top">Team:</td>
    <td>
      <select name="team">
      <%
      // Display the list of teams
		java.util.List<com.vasa.scheduling.domain.Team> teams = (java.util.List<com.vasa.scheduling.domain.Team>)request.getAttribute("teams");
		for(com.vasa.scheduling.domain.Team t:teams){
			out.append("<option value='"+t.getId()+"'>" + t.getAgeGroup().getName()+" - " + t.getName()+" - "+t.getCoach().getLastName()+"</option>");
		}
      %>
      </select>
    </td>
    <td>Field:</td>
    <td>
      <select name="field">
      <%
      // Display the list of fields
		for(com.vasa.scheduling.domain.Fields t:fields){
			out.append("<option value='"+t.getName()+"'>" + t.getName()+"</option>");
		}
      %>
      </select>
    </td>
    <td>Date:</td>
    <td>
      <input name="date" type="text" id="datepicker" size="10">
    </td>
    <td>Time:</td>
    <td>
      <input name="hour" type="text" size="2" maxlength="2" value="hh">
      <input name="minute" type="text" size="2" maxlength="2" value="mm">
       (military time)
    </td>
    <td>Duration:</td>
    <td>
     <input name="duration" type="text" size="7">
     format: 2h 30m
    </td>
    <td><input type="submit" value="Add"></td>
  </tr>
</tbody></table>
</form>

<% } %>

	<div style="width: 100%; overflow: hidden;">
    	<div style="width: 100px; float: left;"><a href="/schedule/calendar?date=<%out.println(format1.format(addDay.getTime()));%>"><b>Previous Week</b></a></div>
        <% addDay.add(java.util.Calendar.DAY_OF_MONTH, 14); %>
    	<div style="margin-left: 80%; float: right;"><a href="/schedule/calendar?date=<%out.println(format1.format(addDay.getTime()));%>"><b>Next Week</b></a></div>
	</div>
	
	<table class="list" width="100%">
	<tr class="header">
	  <th>
	    Week of <% out.println(format2.format(scheduleDay.getTime())); %>
	  </th>
	</tr>
	</table>
	
	<c:if test="${SundayWeather != null }">
	<table class="weather" width="100%">
	<tbody>
	<tr><td>${SundayWeather}</td></tr>
	</tbody>
	</table>
	</c:if>
	
	<table id="schedule" class="list">
	
	<%
	java.util.Map<String,java.util.Map<String,java.util.ArrayList<String>>> schedule = (java.util.Map<String,java.util.Map<String,java.util.ArrayList<String>>>)request.getAttribute("schedule");
	
	out.println("<thead><tr class='header' onclick='showHide(this);' style='cursor: pointer; opacity: 0.75;'>");
	out.println("<th id='date' width='100px' colspan='"+(fields.size()+1)+"'>Sunday "+format1.format(scheduleDay.getTime())+"</th></tr>");
	
	out.println("<tr class='header' id='fields' style='width:100%; display: table; table-layout: fixed; margin-top:0px; margin-bottom:0px;'><td width='70px'>&nbsp;</td>");
	
	for(com.vasa.scheduling.domain.Fields field:fields){
		out.println("<th>"+field.getName()+"</th>");
	}
	
	out.println("</tr></thead>");
	out.println("<tbody style='height:300px; flex: 1 1 auto; display: block; overflow-y: scroll; table-layout: fixed;'>");
	out.println("<tr style='width:100%; display: table; table-layout: fixed;'>");
	
	out.println(printDay(user, scheduleDay, "Sunday", fields, schedule, request));
	scheduleDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
	out.println("</tbody></table>");
	
	%>
	<c:if test="${MondayWeather != null }">
	<table class="weather" width="100%">
	<tbody>
	<tr><td>${MondayWeather}</td></tr>
	</tbody>
	</table>
	</c:if>
	
	<table id="schedule" class="list">
	<%
	out.println("<thead><tr class='header' onclick='showHide(this);' style='cursor: pointer; opacity: 0.75;'>");
	out.println("<th id='date' width='100px' colspan='"+(fields.size()+1)+"'>Monday "+format1.format(scheduleDay.getTime())+"</th></tr>");
	
	out.println("<tr class='header' id='fields' style='width:100%; display: table; table-layout: fixed; margin-top:0px; margin-bottom:0px;'><td width='70px'>&nbsp;</td>");
	
	for(com.vasa.scheduling.domain.Fields field:fields){
		out.println("<th>"+field.getName()+"</th>");
	}
	
	out.println("</tr></thead>");
	out.println("<tbody style='height:300px; flex: 1 1 auto; display: block; overflow-y: scroll; table-layout: fixed;'>");
	out.println("<tr style='width:100%; display: table; table-layout: fixed;'>");
	
	out.println(printDay(user, scheduleDay, "Monday", fields, schedule, request));
	scheduleDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
	out.println("</tbody></table>");
	
	%>
	<c:if test="${TuesdayWeather != null }">
	<table class="weather" width="100%">
	<tbody>
	<tr><td>${TuesdayWeather}</td></tr>
	</tbody>
	</table>
	</c:if>
	
	<table id="schedule" class="list">
	<%
	out.println("<thead><tr class='header' onclick='showHide(this);' style='cursor: pointer; opacity: 0.75;'>");
	out.println("<th id='date' width='100px' colspan='"+(fields.size()+1)+"'>Tuesday "+format1.format(scheduleDay.getTime())+"</th></tr>");
	
	out.println("<tr class='header' id='fields' style='width:100%; display: table; table-layout: fixed; margin-top:0px; margin-bottom:0px;'><td width='70px'>&nbsp;</td>");
	
	for(com.vasa.scheduling.domain.Fields field:fields){
		out.println("<th>"+field.getName()+"</th>");
	}
	
	out.println("</tr></thead>");
	out.println("<tbody style='height:300px; flex: 1 1 auto; display: block; overflow-y: scroll; table-layout: fixed;'>");
	out.println("<tr style='width:100%; display: table; table-layout: fixed;'>");
	
	out.println(printDay(user, scheduleDay, "Tuesday", fields, schedule, request));
	scheduleDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
	out.println("</tbody></table>");
	
	%>
	<c:if test="${WednesdayWeather != null }">
	<table class="weather" width="100%">
	<tbody>
	<tr><td>${WednesdayWeather}</td></tr>
	</tbody>
	</table>
	</c:if>
	
	<table id="schedule" class="list">
	<%
	out.println("<thead><tr class='header' onclick='showHide(this);' style='cursor: pointer; opacity: 0.75;'>");
	out.println("<th id='date' width='100px' colspan='"+(fields.size()+1)+"'>Wednesday "+format1.format(scheduleDay.getTime())+"</th></tr>");
	
	out.println("<tr class='header' id='fields' style='width:100%; display: table; table-layout: fixed; margin-top:0px; margin-bottom:0px;'><td width='70px'>&nbsp;</td>");
	
	for(com.vasa.scheduling.domain.Fields field:fields){
		out.println("<th>"+field.getName()+"</th>");
	}
	
	out.println("</tr></thead>");
	out.println("<tbody style='height:300px; flex: 1 1 auto; display: block; overflow-y: scroll; table-layout: fixed;'>");
	out.println("<tr style='width:100%; display: table; table-layout: fixed;'>");
	
	out.println(printDay(user, scheduleDay, "Wednesday", fields, schedule, request));
	scheduleDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
	out.println("</tbody></table>");
	
	%>
	<c:if test="${ThursdayWeather != null }">
	<table class="weather" width="100%">
	<tbody>
	<tr><td>${ThursdayWeather}</td></tr>
	</tbody>
	</table>
	</c:if>
	
	<table id="schedule" class="list">
	<%
	out.println("<thead><tr class='header' onclick='showHide(this);' style='cursor: pointer; opacity: 0.75;'>");
	out.println("<th id='date' width='100px' colspan='"+(fields.size()+1)+"'>Thursday "+format1.format(scheduleDay.getTime())+"</th></tr>");
	
	out.println("<tr class='header' id='fields' style='width:100%; display: table; table-layout: fixed; margin-top:0px; margin-bottom:0px;'><td width='70px'>&nbsp;</td>");
	
	for(com.vasa.scheduling.domain.Fields field:fields){
		out.println("<th>"+field.getName()+"</th>");
	}
	
	out.println("</tr></thead>");
	out.println("<tbody style='height:300px; flex: 1 1 auto; display: block; overflow-y: scroll; table-layout: fixed;'>");
	out.println("<tr style='width:100%; display: table; table-layout: fixed;'>");
	
	out.println(printDay(user, scheduleDay, "Thursday", fields, schedule, request));
	scheduleDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
	out.println("</tbody></table>");
	
	%>
	<c:if test="${FridayWeather != null }">
	<table class="weather" width="100%">
	<tbody>
	<tr><td>${FridayWeather}</td></tr>
	</tbody>
	</table>
	</c:if>
	
	<table id="schedule" class="list">
	<%
	out.println("<thead><tr class='header' onclick='showHide(this);' style='cursor: pointer; opacity: 0.75;'>");
	out.println("<th id='date' width='100px' colspan='"+(fields.size()+1)+"'>Friday "+format1.format(scheduleDay.getTime())+"</th></tr>");
	
	out.println("<tr class='header' id='fields' style='width:100%; display: table; table-layout: fixed; margin-top:0px; margin-bottom:0px;'><td width='70px'>&nbsp;</td>");
	
	for(com.vasa.scheduling.domain.Fields field:fields){
		out.println("<th>"+field.getName()+"</th>");
	}
	
	out.println("</tr></thead>");
	out.println("<tbody style='height:300px; flex: 1 1 auto; display: block; overflow-y: scroll; table-layout: fixed;'>");
	out.println("<tr style='width:100%; display: table; table-layout: fixed;'>");
	
	out.println(printDay(user, scheduleDay, "Friday", fields, schedule, request));
	scheduleDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
	out.println("</tbody></table>");
	
	%>
	<c:if test="${SaturdayWeather != null }">
	<table class="weather" width="100%">
	<tbody>
	<tr><td>${SaturdayWeather}</td></tr>
	</tbody>
	</table>
	</c:if>
	
	<table id="schedule" class="list">
	<%
	out.println("<thead><tr class='header' onclick='showHide(this);' style='cursor: pointer; opacity: 0.75;'>");
	out.println("<th id='date' width='100px' colspan='"+(fields.size()+1)+"'>Saturday "+format1.format(scheduleDay.getTime())+"</th></tr>");
	
	out.println("<tr class='header' id='fields' style='width:100%; display: table; table-layout: fixed; margin-top:0px; margin-bottom:0px;'><td width='70px'>&nbsp;</td>");
	
	for(com.vasa.scheduling.domain.Fields field:fields){
		out.println("<th>"+field.getName()+"</th>");
	}
	
	out.println("</tr></thead>");
	out.println("<tbody style='height:300px; flex: 1 1 auto; display: block; overflow-y: scroll; table-layout: fixed;'>");
	out.println("<tr style='width:100%; display: table; table-layout: fixed;'>");
	
	out.println(printDay(user, scheduleDay, "Saturday", fields, schedule, request));
	scheduleDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
	out.println("</tbody></table>");
	
	%>
	
	</tr><tr>
	
	
	<%!
	private StringBuffer printDay(com.vasa.scheduling.domain.User user, 
			java.util.Calendar time, 
			String dayName, 
			java.util.ArrayList<com.vasa.scheduling.domain.Fields> fields, 
			java.util.Map<String,java.util.Map<String,java.util.ArrayList<String>>> schedule,
			HttpServletRequest request){

		StringBuffer out = new StringBuffer();
		
		java.text.SimpleDateFormat format1 = new java.text.SimpleDateFormat("hh:mm aa");
		java.text.SimpleDateFormat format2 = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm");
		

		time.set(java.util.Calendar.HOUR_OF_DAY, 8);
		time.set(java.util.Calendar.MINUTE, 30);
		
		for(int x=0; x<26; x++){
			int fieldCount=0;
			if(x>0){
				out.append("<tr style='width:100%; display: table; table-layout: fixed;'>");
			}
			if(fieldCount==0){
				time.add(java.util.Calendar.MINUTE, 30);
			}
			for(com.vasa.scheduling.domain.Fields field:fields){
				boolean locked = (Boolean)request.getAttribute(field.getName()+"locked");
				java.util.Map<String,java.util.ArrayList<String>> week = schedule.get(field.getName());
				java.util.ArrayList<String> day = week.get(dayName);
				
				if(fieldCount==0){
					out.append("<td width='70px' bgcolor='#5f8aad'><font color='#ffffff'>"+format1.format(time.getTime())+"</font></td>");
				}
				
				String hour = day.get(x);
				if(hour!=null && hour.contains("Game")){
					out.append("<td bgcolor='yellow'>"+hour+"</td>");
				}
				else if(hour!=null && (hour.startsWith("N/A") || hour.startsWith("Reserved"))){
					out.append("<td bgcolor='gray'>"+hour+"</td>");
				}
				else if(locked){
					if(hour==null) hour="&nbsp;";
					else if(user.getUserType().equals(com.vasa.scheduling.enums.UserType.ADMIN) ||
							user.getUserType().equals(com.vasa.scheduling.enums.UserType.COMMISSIONER)){
						hour="<a href='/schedule/calendar/delete?date="+format2.format(time.getTime())+"&field="+field.getName()+"'><img src='/images/minus-icon.png' /></a> "+hour;
					}
					out.append("<td>"+hour+"</td>");
				}
				else if(hour == null && (user.getUserType().equals(com.vasa.scheduling.enums.UserType.ADMIN) ||
						user.getUserType().equals(com.vasa.scheduling.enums.UserType.COACH) ||
						user.getUserType().equals(com.vasa.scheduling.enums.UserType.COMMISSIONER))){
					out.append("<td><a href='/schedule/calendar/add?date="+format2.format(time.getTime())+"&field="+field.getName()+"'><img src='/images/plus-icon.png' /></a>");
					
// 					// Display the list of teams
// 					if((user.getUserType().equals(com.vasa.scheduling.enums.UserType.ADMIN) ||
// 							user.getUserType().equals(com.vasa.scheduling.enums.UserType.COMMISSIONER))){
						
// 						java.util.List<com.vasa.scheduling.domain.Team> teams = (java.util.List<com.vasa.scheduling.domain.Team>)request.getAttribute("teams");
// 						out.append("<form id='entry' name='entry' action='/schedule/calendar/add' method='POST'><select name='team' style='max-width:40%;'>");
// 						for(com.vasa.scheduling.domain.Team t:teams){
// 							out.append("<option value='"+t.getId()+"'>" + t.getName()+" - "+t.getCoach().getLastName()+"</option>");
// 						}
// 						out.append("</select>");
// 						out.append("<input type='hidden' name='date' value='"+format2.format(time.getTime())+"'>");
// 						out.append("<input type='hidden' name='field' value='"+field.getName()+"'>");
// 						out.append("<input type='submit' value='Add'>");
// 						out.append("</form>");
// 					}
					out.append("</td>");
					
				}else if(hour != null && (user.getUserType().equals(com.vasa.scheduling.enums.UserType.ADMIN) ||
						user.getUserType().equals(com.vasa.scheduling.enums.UserType.COACH) ||
						user.getUserType().equals(com.vasa.scheduling.enums.UserType.COMMISSIONER))){
					if(user.getUserType().equals(com.vasa.scheduling.enums.UserType.ADMIN) || 
							user.getUserType().equals(com.vasa.scheduling.enums.UserType.COMMISSIONER) || 
							hour.equals(user.getTeam().getName() + " - " + user.getLastName() + " - " + user.getTeam().getAgeGroup().getName())){
						out.append("<td><a href='/schedule/calendar/delete?date="+format2.format(time.getTime())+"&field="+field.getName()+"'><img src='/images/minus-icon.png' /></a> "+hour+"</td>");
					}else{
						out.append("<td>"+hour+"</td>");
					}
				}else if(hour==null){
					out.append("<td>&nbsp;</td>");
				}else{
					out.append("<td>"+hour+"</td>");
				}
				fieldCount++;
			}
			out.append("</tr>");
		}
		return out;
	}
	%>
	
	</table>
	
</body>
</html>