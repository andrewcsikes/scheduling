<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- http://cssmenumaker.com/ -->




<%
String userAgent = request.getHeader("user-agent");
if(userAgent.contains("iPhone") || userAgent.contains("Android")){
%>

<div id='cssmenu'>
<ul>
   <li><a href='/scheduling/user/home'><span>Home</span></a></li>
   <c:if test='${user.userType.displayName == "ADMIN" || user.userType.displayName == "Commissioner"}'>
     <li><a href='/scheduling/user/list'><span>All Users</span></a></li>
   </c:if>
   <li><a href='/scheduling/team/list'><span>Teams</span></a></li>
   <!-- 
   <c:choose>
     <c:when test='${user.team.sport.name=="Basketball" || user.userType.displayName == "ADMIN"}'>
       <li><a href='/scheduling/schedule/basketball'><span>Basketball Calendar</span></a></li>
     </c:when>
     <c:otherwise>
       <li><a href='/scheduling/schedule/calendar'><span>Calendar</span></a></li>
     </c:otherwise>
   </c:choose>
   -->
   <li><a href='/scheduling/schedule/calendar'><span>Calendar</span></a></li>
   <li><a href='/scheduling/schedule/list'><span>Searchable List</span></a></li>

   <c:if test='${user.userType.displayName == "ADMIN" || user.userType.displayName == "Commissioner"}'>
     <li><a href='/scheduling/game/add'><span>Add Game</span></a></li>
     <li><a href='/scheduling/user/postmessage'><span>System Message</span></a></li>
   </c:if>
   <li><a href='/scheduling/user/modify'><span>My Account</span></a></li>
</ul>
</div>


<%
} else if(userAgent.contains("iPad")){
%>
	
	
<div id='cssmenu'>
<ul>
   <li><a href='/scheduling/user/home'><span>Home</span></a></li>
      <!-- 
         <c:choose>
           <c:when test='${user.userType.displayName == "ADMIN"}'>
             <li><a href='/scheduling/schedule/basketball'><span>Basketball Calendar</span></a></li>
             <li><a href='/scheduling/schedule/calendar'><span>Baseball Calendar</span></a></li>
           </c:when>
           <c:when test='${user.team.sport.name=="Basketball"}'>
             <li><a href='/scheduling/schedule/basketball'><span>Basketball Calendar</span></a></li>
           </c:when>
           <c:otherwise>
             <li><a href='/scheduling/schedule/calendar'><span>Calendar</span></a></li>
           </c:otherwise>
         </c:choose>
         -->
         <li><a href='/scheduling/schedule/calendar'><span>Calendar</span></a></li>
         <li><a href='/scheduling/schedule/list'><span>Searchable List</span></a></li>

   <c:if test='${user.userType.displayName == "ADMIN" || user.userType.displayName == "Commissioner"}'>
     <li><a href='/scheduling/game/list'><span>Games</span></a></li>
   </c:if>
   <c:if test='${user.userType.displayName == "Commissioner" || user.userType.displayName == "ADMIN"}'>
   	<li><a href='/scheduling/user/list'><span>Users</span></a></li>
   </c:if>
   <c:choose>
     <c:when test='${user.userType.displayName == "ADMIN"}'>
       <li><a href='/scheduling/team/list'><span>Teams</span></a></li>
       <li><a href='/scheduling/sport/list'><span>Sports</span></a></li>
       <li><a href='/scheduling/season/list'><span>Seasons</span></a></li>
       <li><a href='/scheduling/fields/list'><span>Fields</span></a></li>
   	   <li><a href='/scheduling/user/postmessage'><span>System Message</span></a></li>
   	 </c:when>
     <c:when test='${user.userType.displayName == "Commissioner"}'>
     	<li><a href='/scheduling/team/list'><span>Teams</span></a></li>
   	    <li><a href='/scheduling/user/postmessage'><span>System Message</span></a></li>
   	 </c:when>
     <c:otherwise>
       <li><a href='/scheduling/team/list'><span>Teams</span></a></li>
     </c:otherwise>
   </c:choose>
   <li><a href='/scheduling/user/modify'><span>My Account</span></a></li>
</ul>
</div>
	
	
<%
}else{
%>

<div id='cssmenu'>
<ul>
   <li><a href='/scheduling/user/home'><span>Home</span></a></li>
   <li class='has-sub'><a>Schedule</a>
      <ul>
      <!-- 
         <c:choose>
           <c:when test='${user.userType.displayName == "ADMIN"}'>
             <li><a href='/scheduling/schedule/basketball'><span>Basketball Calendar</span></a></li>
             <li><a href='/scheduling/schedule/calendar'><span>Baseball Calendar</span></a></li>
           </c:when>
           <c:when test='${user.team.sport.name=="Basketball"}'>
             <li><a href='/scheduling/schedule/basketball'><span>Basketball Calendar</span></a></li>
           </c:when>
           <c:otherwise>
             <li><a href='/scheduling/schedule/calendar'><span>Calendar</span></a></li>
           </c:otherwise>
         </c:choose>
         --><li><a href='/scheduling/schedule/calendar'><span>Calendar</span></a></li>
         <li><a href='/scheduling/schedule/list'><span>Searchable List</span></a></li>
      </ul>
   </li>
   <c:if test='${user.userType.displayName == "ADMIN" || user.userType.displayName == "Commissioner"}'>
     <li class='has-sub'><a>Games</a>
       <ul>
         <li><a href='/scheduling/game/list'><span>All Games</span></a></li>
         <c:if test='${user.userType.displayName == "ADMIN"}'>
         	<li><a href='/scheduling/game/upload'><span>Upload Games</span></a></li>
         </c:if>
       </ul>
     </li>
   </c:if>
   <c:if test='${user.userType.displayName == "Commissioner"}'>
   	<li><a href='/scheduling/user/list'><span>Users</span></a></li>
   </c:if>
   <c:if test='${user.userType.displayName == "ADMIN"}'>
   <li class='has-sub'><a>Users</a>
     <ul>
     	<li><a href='/scheduling/user/list'><span>All Users</span></a></li>
     	<li><a href='/scheduling/user/lastactive'><span>Last Active User</span></a></li>
     </ul>
   </li>
   </c:if>
   <c:choose>
     <c:when test='${user.userType.displayName == "ADMIN"}'>
       <li class='has-sub'><a>Admin</a>
         <ul>
           <li><a href='/scheduling/team/list'><span>Teams</span></a></li>
           <li><a href='/scheduling/sport/list'><span>Sports</span></a></li>
           <li><a href='/scheduling/season/list'><span>Seasons</span></a></li>
           <li><a href='/scheduling/fields/list'><span>Fields</span></a></li>
           <li><a href='/scheduling/logs/list'><span>Logs</span></a></li>
   	       <li><a href='/scheduling/user/postmessage'><span>System Message</span></a></li>
         </ul>
       </li>
     </c:when>
     <c:when test='${user.userType.displayName == "Commissioner"}'>
     	<li class='has-sub'><a>Admin</a>
         <ul>
           <li><a href='/scheduling/team/list'><span>Teams</span></a></li>
   	       <li><a href='/scheduling/user/postmessage'><span>System Message</span></a></li>
   	     </ul>
   	    </li>
     </c:when>
     <c:otherwise>
       <li><a href='/scheduling/team/list'><span>Teams</span></a></li>
     </c:otherwise>
   </c:choose>
   <li><a href='/scheduling/user/modify'><span>My Account</span></a></li>
</ul>
</div>


<%
}
%>