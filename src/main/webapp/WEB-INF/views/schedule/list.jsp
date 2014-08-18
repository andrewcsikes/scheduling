<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VASA Field Scheduling - User List</title>
</head>
<body>

<%
java.util.ArrayList<com.vasa.scheduling.domain.Fields> fields = (java.util.ArrayList<com.vasa.scheduling.domain.Fields>)request.getAttribute("fields");
java.text.SimpleDateFormat format1 = new java.text.SimpleDateFormat("MM/dd/yyyy");
java.text.SimpleDateFormat format2 = new java.text.SimpleDateFormat("MMM dd, yyyy");

java.util.Calendar scheduleDay = java.util.Calendar.getInstance();
scheduleDay.setTime((java.util.Date)request.getAttribute("sunday"));

java.util.Calendar addDay = java.util.Calendar.getInstance();
addDay.setTime((java.util.Date)request.getAttribute("sunday"));
addDay.add(java.util.Calendar.DAY_OF_MONTH, -7);
%>
	
	<table class="list">
	<tr class="header">
	  <th><a href="/scheduling/schedule/list?date=<%out.println(format1.format(addDay.getTime()));%>">Previous Week</a></th>
	  <th colspan=<%out.println(fields.size()); %>>
	    Week of <% out.println(format2.format(scheduleDay.getTime())); %>
	  </th>
	  <% addDay.add(java.util.Calendar.DAY_OF_MONTH, 14); %>
	  <th><a href="/scheduling/schedule/list?date=<%out.println(format1.format(addDay.getTime()));%>">Next Week</a></th>
	</tr>
	<tr class="header">
		<th> </th>
		<th> </th>
	
	<%
	java.util.Map<String,java.util.Map<String,java.util.ArrayList<String>>> schedule = (java.util.Map<String,java.util.Map<String,java.util.ArrayList<String>>>)request.getAttribute("schedule");
	com.vasa.scheduling.domain.User user = (com.vasa.scheduling.domain.User)request.getAttribute("user"); 
	
	for(com.vasa.scheduling.domain.Fields field:fields){
		out.println("<th>"+field.getName()+"</th>");
	}
	
	out.println("</tr><tr>");
	
	out.println("<th width='100px' rowspan='26'>Sunday<br />"+format1.format(scheduleDay.getTime())+"</th>");
	out.println(printDay(user, scheduleDay, "Sunday", fields, schedule));
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
	out.println(printDay(user, scheduleDay, "Monday", fields, schedule));
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
	out.println(printDay(user, scheduleDay, "Tuesday", fields, schedule));
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
	out.println(printDay(user, scheduleDay, "Wednesday", fields, schedule));
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
	out.println(printDay(user, scheduleDay, "Thursday", fields, schedule));
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
	out.println(printDay(user, scheduleDay, "Friday", fields, schedule));
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
	out.println(printDay(user, scheduleDay, "Saturday", fields, schedule));
	scheduleDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
	out.println("</table>");
	
	%>
	
	</tr><tr>
	
	
	<%!
	private StringBuffer printDay(com.vasa.scheduling.domain.User user, 
			java.util.Calendar time, 
			String dayName, 
			java.util.ArrayList<com.vasa.scheduling.domain.Fields> fields, 
			java.util.Map<String,java.util.Map<String,java.util.ArrayList<String>>> schedule){

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
				java.util.Map<String,java.util.ArrayList<String>> week = schedule.get(field.getName());
				java.util.ArrayList<String> day = week.get(dayName);
				
				if(fieldCount==0){
					out.append("<th width='70px' bgcolor='#5f8aad'><font color='#ffffff'>"+format1.format(time.getTime())+"</font></th>");
				}
				
				String hour = day.get(x);
				if(hour == null && (user.getUserType().equals(com.vasa.scheduling.enums.UserType.ADMIN) ||
						user.getUserType().equals(com.vasa.scheduling.enums.UserType.COACH))){
					out.append("<td><a href='/scheduling/schedule/list/add?date="+format2.format(time.getTime())+"&field="+field.getName()+"'><img src='/scheduling/images/plus-icon.png' /></a></td>");
				}else if(hour != null && hour.equals(user.getTeam().getName() + " - " + user.getLastName() + " - " + user.getTeam().getAgeGroup().getName())){
					out.append("<td><a href='/scheduling/schedule/list/delete?date="+format2.format(time.getTime())+"&field="+field.getName()+"'><img src='/scheduling/images/minus-icon.png' /></a> "+hour+"</td>");
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