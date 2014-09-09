<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- http://cssmenumaker.com/ -->

<div id='cssmenu'>
<ul>
   <li><a href='/scheduling/user/home'><span>Home</span></a></li>
   <c:if test='${user.userType.displayName == "ADMIN" || user.userType.displayName == "Commissioner"}'>
     <li><a href='/scheduling/user/list'><span>All Users</span></a></li>
   </c:if>
   <li><a href='/scheduling/team/list'><span>Teams</span></a></li>
   <li class='has-sub'><a>Schedule</a>
      <ul>
         <li><a href='/scheduling/schedule/calendar'><span>Calendar</span></a></li>
         <li><a href='/scheduling/schedule/list'><span>Searchable List</span></a></li>
      </ul>
   </li>
   <c:if test='${user.userType.displayName == "ADMIN" || user.userType.displayName == "Commissioner"}'>
     <li class='has-sub'><a>Games</a>
       <ul>
         <li><a href='/scheduling/game/add'><span>Add Single Game</span></a></li>
         <c:if test='${user.userType.displayName == "ADMIN"}'>
         	<li><a href='/scheduling/game/upload'><span>Upload Games</span></a></li>
         </c:if>
       </ul>
     </li>
   </c:if>
   <li><a href='/scheduling/user/modify'><span>My Account</span></a></li>
</ul>
</div>