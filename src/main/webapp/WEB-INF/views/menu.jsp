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
<!--    <li><a href='/scheduling/schedule/calendar'><span>Schedule</span></a></li> -->
<!--    <li><a href='/CardShop/member/cards'><span>My Cards</span></a></li> -->
<!--    <li><a href='/CardShop/card/sellhistory'><span>My Sells</span></a></li> -->
<!--    <li><a href='/CardShop/card/trade'><span>My Trade Offers</span></a></li> -->
   <!-- 
   <li class='has-sub '><span>My OffersSelling</span>
      <ul>
         <li><a href='/CardShop/card/sell'><span>Sell A Card</span></a></li>
      </ul>
   </li>
   -->
<!--    <li><a href='/CardShop/card/value'><span>Lookup Card Value</span></a></li> -->
    <li><a href='/scheduling/user/modify'><span>My Account</span></a></li>
</ul>
</div>