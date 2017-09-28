<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- http://cssmenumaker.com/ -->




<%
String userAgent = request.getHeader("user-agent");
if(userAgent.contains("iPhone") || userAgent.contains("Android")){
%>

<div id='cssmenu'>
<ul>
   <li><a href='/user/home'><span>Home</span></a></li>
   <c:if test='${user.userType.displayName == "ADMIN" || user.userType.displayName == "Commissioner"}'>
     <li><a href='/user/list'><span>All Users</span></a></li>
   </c:if>
   <li><a href='/team/list'><span>Teams</span></a></li>
   <!-- 
   <c:choose>
     <c:when test='${user.team.sport.name=="Basketball" || user.userType.displayName == "ADMIN"}'>
       <li><a href='/schedule/basketball'><span>Basketball Calendar</span></a></li>
     </c:when>
     <c:otherwise>
       <li><a href='/schedule/calendar'><span>Calendar</span></a></li>
     </c:otherwise>
   </c:choose>
   -->
   <li><a href='/schedule/calendar'><span>Calendar</span></a></li>
   <li><a href='/schedule/list'><span>Searchable List</span></a></li>

   <c:if test='${user.userType.displayName == "ADMIN" || user.userType.displayName == "Commissioner"}'>
     <li><a href='/game/add'><span>Add Game</span></a></li>
     <li><a href='/user/postmessage'><span>System Message</span></a></li>
     <li><a href='/messages/list'><span>Important Dates</span></a></li>
   </c:if>
   <li><a href='/user/modify'><span>My Account</span></a></li>
</ul>
</div>


<%
} else if(userAgent.contains("iPad")){
%>
	
	
<div id='cssmenu'>
<ul>
   <li><a href='/user/home'><span>Home</span></a></li>
      <!-- 
         <c:choose>
           <c:when test='${user.userType.displayName == "ADMIN"}'>
             <li><a href='/schedule/basketball'><span>Basketball Calendar</span></a></li>
             <li><a href='/schedule/calendar'><span>Baseball Calendar</span></a></li>
           </c:when>
           <c:when test='${user.team.sport.name=="Basketball"}'>
             <li><a href='/schedule/basketball'><span>Basketball Calendar</span></a></li>
           </c:when>
           <c:otherwise>
             <li><a href='/schedule/calendar'><span>Calendar</span></a></li>
           </c:otherwise>
         </c:choose>
         -->
         <li><a href='/schedule/calendar'><span>Calendar</span></a></li>
         <li><a href='/schedule/list'><span>Searchable List</span></a></li>

   <c:if test='${user.userType.displayName == "ADMIN" || user.userType.displayName == "Commissioner"}'>
     <li><a href='/game/list'><span>Games</span></a></li>
   </c:if>
   <c:if test='${user.userType.displayName == "Commissioner" || user.userType.displayName == "ADMIN"}'>
   	<li><a href='/user/list'><span>Users</span></a></li>
   </c:if>
   <c:choose>
     <c:when test='${user.userType.displayName == "ADMIN"}'>
       <li><a href='/team/list'><span>Teams</span></a></li>
       <li><a href='/sport/list'><span>Sports</span></a></li>
       <li><a href='/season/list'><span>Seasons</span></a></li>
       <li><a href='/fields/list'><span>Fields</span></a></li>
   	   <li><a href='/user/postmessage'><span>System Message</span></a></li>
     <li><a href='/messages/list'><span>Important Dates</span></a></li>
     <li><a href='/rules/list'><span>Rules</span></a></li>
   	 </c:when>
     <c:when test='${user.userType.displayName == "Commissioner"}'>
     	<li><a href='/team/list'><span>Teams</span></a></li>
   	    <li><a href='/user/postmessage'><span>System Message</span></a></li>
     <li><a href='/messages/list'><span>Important Dates</span></a></li>
   	 </c:when>
     <c:otherwise>
       <li><a href='/team/list'><span>Teams</span></a></li>
     </c:otherwise>
   </c:choose>
   <li><a href='/user/modify'><span>My Account</span></a></li>
</ul>
</div>
	
	
<%
}else{
%>

<div id='cssmenu'>
<ul>
   <li><a href='/user/home'><span>Home</span></a></li>
   <li class='has-sub'><a>Schedule</a>
      <ul>
      <!-- 
         <c:choose>
           <c:when test='${user.userType.displayName == "ADMIN"}'>
             <li><a href='/schedule/basketball'><span>Basketball Calendar</span></a></li>
             <li><a href='/schedule/calendar'><span>Baseball Calendar</span></a></li>
           </c:when>
           <c:when test='${user.team.sport.name=="Basketball"}'>
             <li><a href='/schedule/basketball'><span>Basketball Calendar</span></a></li>
           </c:when>
           <c:otherwise>
             <li><a href='/schedule/calendar'><span>Calendar</span></a></li>
           </c:otherwise>
         </c:choose>
         --><li><a href='/schedule/calendar'><span>Calendar</span></a></li>
         <li><a href='/schedule/list'><span>Searchable List</span></a></li>
      </ul>
   </li>
   <c:if test='${user.userType.displayName == "ADMIN" || user.userType.displayName == "Commissioner"}'>
     <li class='has-sub'><a>Games</a>
       <ul>
         <li><a href='/game/list'><span>All Games</span></a></li>
         <c:if test='${user.userType.displayName == "ADMIN"}'>
         	<li><a href='/game/upload'><span>Upload Games</span></a></li>
         </c:if>
       </ul>
     </li>
   </c:if>
   <c:if test='${user.userType.displayName == "Commissioner"}'>
   	<li><a href='/user/list'><span>Users</span></a></li>
   </c:if>
   <c:if test='${user.userType.displayName == "ADMIN"}'>
   <li class='has-sub'><a>Users</a>
     <ul>
     	<li><a href='/user/list'><span>All Users</span></a></li>
     	<li><a href='/user/lastactive'><span>Last Active User</span></a></li>
     </ul>
   </li>
   </c:if>
   <c:choose>
     <c:when test='${user.userType.displayName == "ADMIN"}'>
       <li class='has-sub'><a>Admin</a>
         <ul>
           <li><a href='/team/list'><span>Teams</span></a></li>
           <li><a href='/sport/list'><span>Sports</span></a></li>
           <li><a href='/season/list'><span>Seasons</span></a></li>
           <li><a href='/fields/list'><span>Fields</span></a></li>
           <li><a href='/logs/list'><span>Logs</span></a></li>
   	       <li><a href='/user/postmessage'><span>System Message</span></a></li>
     <li><a href='/messages/list'><span>Important Dates</span></a></li>
         </ul>
       </li>
     </c:when>
     <c:when test='${user.userType.displayName == "Commissioner"}'>
     	<li class='has-sub'><a>Admin</a>
         <ul>
           <li><a href='/team/list'><span>Teams</span></a></li>
   	       <li><a href='/user/postmessage'><span>System Message</span></a></li>
     <li><a href='/messages/list'><span>Important Dates</span></a></li>
   	     </ul>
   	    </li>
     </c:when>
     <c:otherwise>
       <li><a href='/team/list'><span>Teams</span></a></li>
     </c:otherwise>
   </c:choose>
   <li><a href='/user/modify'><span>My Account</span></a></li>
</ul>
</div>


<%
}
%>