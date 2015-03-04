<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VASA Field Scheduling</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>


	<script>
	
	$(document).ready(function(){
		
		hideDay(1);
		hideDay(2);
		hideDay(3);
		hideDay(4);
		hideDay(5);
		
	});
	
	function hideDay(day){

		$("table.list").eq(day).find("tr:nth-child(2)").find("td").hide();
		$("table.list").eq(day).find("tr:nth-child(2)").find("th:nth-child(2)").hide();

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
%>
	
	<div style="width: 100%; overflow: hidden;">
    	<div style="width: 100px; float: left;"><a href="/scheduling/schedule/calendar?date=<%out.println(format1.format(addDay.getTime()));%>"><b>Previous Week</b></a></div>
    	 <% addDay.add(java.util.Calendar.DAY_OF_MONTH, 14); %>
    	<div style="margin-left: 80%; float: right;"><a href="/scheduling/schedule/calendar?date=<%out.println(format1.format(addDay.getTime()));%>"><b>Next Week</b></a></div>
	</div>
	
	<table class="list">
	<tr class="header">
	  <th colspan=<%out.println(fields.size()+2); %>>
	    Week of <% out.println(format2.format(scheduleDay.getTime())); %>
	  </th>
	</tr>
	<tr class="header">
		<th> </th>
		<th> </th>
	
	<%
	java.util.Map<String,java.util.Map<String,java.util.ArrayList<String>>> schedule = (java.util.Map<String,java.util.Map<String,java.util.ArrayList<String>>>)request.getAttribute("schedule");
	
	for(com.vasa.scheduling.domain.Fields field:fields){
		out.println("<th>"+field.getName()+"</th>");
	}
	
	out.println("</tr><tr>");
	
	out.println("<th width='100px' rowspan='26'>Sunday<br />"+format1.format(scheduleDay.getTime())+"</th>");
	out.println(printDay(user, scheduleDay, "Sunday", fields, schedule, request));
	scheduleDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
	out.println("</table>");
	
	%>
	<table class="list">
	<tr class="header">
		<th> </th>
		<th> </th>
	<%
	for(com.vasa.scheduling.domain.Fields field:fields){
		out.println("<th>"+field.getName()+"</th>");
	}
	
	out.println("</tr><tr>");
	
	out.println("<th width='100px' rowspan='26'>Monday<br />"+format1.format(scheduleDay.getTime())+"</th>");
	out.println(printDay(user, scheduleDay, "Monday", fields, schedule, request));
	scheduleDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
	out.println("</table>");
	
	%>
	<table class="list">
	<tr class="header">
		<th> </th>
		<th> </th>
	<%
	for(com.vasa.scheduling.domain.Fields field:fields){
		out.println("<th>"+field.getName()+"</th>");
	}
	
	out.println("</tr><tr>");
	
	out.println("<th width='100px' rowspan='26'>Tuesday<br />"+format1.format(scheduleDay.getTime())+"</th>");
	out.println(printDay(user, scheduleDay, "Tuesday", fields, schedule, request));
	scheduleDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
	out.println("</table>");
	
	%>
	<table class="list">
	<tr class="header">
		<th> </th>
		<th> </th>
	<%
	for(com.vasa.scheduling.domain.Fields field:fields){
		out.println("<th>"+field.getName()+"</th>");
	}
	
	out.println("</tr><tr>");
	
	out.println("<th width='100px' rowspan='26'>Wednesday<br />"+format1.format(scheduleDay.getTime())+"</th>");
	out.println(printDay(user, scheduleDay, "Wednesday", fields, schedule, request));
	scheduleDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
	out.println("</table>");
	
	%>
	<table class="list">
	<tr class="header">
		<th> </th>
		<th> </th>
	<%
	for(com.vasa.scheduling.domain.Fields field:fields){
		out.println("<th>"+field.getName()+"</th>");
	}
	
	out.println("</tr><tr>");
	
	out.println("<th width='100px' rowspan='26'>Thursday<br />"+format1.format(scheduleDay.getTime())+"</th>");
	out.println(printDay(user, scheduleDay, "Thursday", fields, schedule, request));
	scheduleDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
	out.println("</table>");
	
	%>
	<table class="list">
	<tr class="header">
		<th> </th>
		<th> </th>
	<%
	for(com.vasa.scheduling.domain.Fields field:fields){
		out.println("<th>"+field.getName()+"</th>");
	}
	
	out.println("</tr><tr>");
	
	out.println("<th width='100px' rowspan='26'>Friday<br />"+format1.format(scheduleDay.getTime())+"</th>");
	out.println(printDay(user, scheduleDay, "Friday", fields, schedule, request));
	scheduleDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
	out.println("</table>");
	
	%>
	<table class="list">
	<tr class="header">
		<th> </th>
		<th> </th>
	<%
	for(com.vasa.scheduling.domain.Fields field:fields){
		out.println("<th>"+field.getName()+"</th>");
	}
	
	out.println("</tr><tr>");
	
	out.println("<th width='100px' rowspan='26'>Saturday<br />"+format1.format(scheduleDay.getTime())+"</th>");
	out.println(printDay(user, scheduleDay, "Saturday", fields, schedule, request));
	scheduleDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
	out.println("</table>");
	
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
				out.append("<tr>");
			}
			if(fieldCount==0){
				time.add(java.util.Calendar.MINUTE, 30);
			}
			for(com.vasa.scheduling.domain.Fields field:fields){
				boolean locked = (Boolean)request.getAttribute(field.getName()+"locked");
				java.util.Map<String,java.util.ArrayList<String>> week = schedule.get(field.getName());
				java.util.ArrayList<String> day = week.get(dayName);
				
				if(fieldCount==0){
					out.append("<th width='70px' bgcolor='#5f8aad'><font color='#ffffff'>"+format1.format(time.getTime())+"</font></th>");
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
					out.append("<td>"+hour+"</td>");
				}
				else if(hour == null && (user.getUserType().equals(com.vasa.scheduling.enums.UserType.ADMIN) ||
						user.getUserType().equals(com.vasa.scheduling.enums.UserType.COACH) ||
						user.getUserType().equals(com.vasa.scheduling.enums.UserType.COMMISSIONER))){
					out.append("<td><a href='/scheduling/schedule/calendar/add?date="+format2.format(time.getTime())+"&field="+field.getName()+"'><img src='/scheduling/images/plus-icon.png' /></a>");
					
					// Display the list of teams
					if((user.getUserType().equals(com.vasa.scheduling.enums.UserType.ADMIN) ||
							user.getUserType().equals(com.vasa.scheduling.enums.UserType.COMMISSIONER))){
						
						java.util.List<com.vasa.scheduling.domain.Team> teams = (java.util.List<com.vasa.scheduling.domain.Team>)request.getAttribute("teams");
						out.append("<form id='entry' name='entry' action='/scheduling/schedule/calendar/add' method='POST'><select name='team' style='max-width:40%;'>");
						for(com.vasa.scheduling.domain.Team t:teams){
							out.append("<option value='"+t.getId()+"'>"+t.getName()+" - "+t.getCoach().getLastName()+"</option>");
						}
						out.append("</select>");
						out.append("<input type='hidden' name='date' value='"+format2.format(time.getTime())+"'>");
						out.append("<input type='hidden' name='field' value='"+field.getName()+"'>");
						out.append("<input type='submit' value='Add'>");
						out.append("</form></td>");	
					}
					
				}else if(hour != null && (user.getUserType().equals(com.vasa.scheduling.enums.UserType.ADMIN) ||
						user.getUserType().equals(com.vasa.scheduling.enums.UserType.COACH) ||
						user.getUserType().equals(com.vasa.scheduling.enums.UserType.COMMISSIONER))){
					if(user.getUserType().equals(com.vasa.scheduling.enums.UserType.ADMIN) || 
							user.getUserType().equals(com.vasa.scheduling.enums.UserType.COMMISSIONER) || 
							hour.equals(user.getTeam().getName() + " - " + user.getLastName() + " - " + user.getTeam().getAgeGroup().getName())){
						out.append("<td><a href='/scheduling/schedule/calendar/delete?date="+format2.format(time.getTime())+"&field="+field.getName()+"'><img src='/scheduling/images/minus-icon.png' /></a> "+hour+"</td>");
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