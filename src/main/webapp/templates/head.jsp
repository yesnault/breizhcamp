<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <style type="text/css">
   html {
     background: url('<c:url value='/static/BreizhCamp.png'/>') no-repeat top center;
   }
   body {
     margin-top:350px;
     margin-left:10px;
     margin-right:10px;
     margin-bottom:70px;
   }
   h2 {
     font-family: 'Sonsie One', cursive;
     margin-top:50px;
     text-align: center;
     font-size:60px;
   }
    h3 {
     font-family: 'Sonsie One', cursive;
     text-align: center;
     font-size:30px;
   }
  </style>
       <script type='text/javascript' charset='utf-8'>
                function setActive(id){
                     document.getElementById(id).className='active';
                }
       </script>
<!-- Onglets -->
<ul class="nav  nav-tabs">
  <li id="programme"><a href="/index.htm">Programme</a></li>
  <li id="speakers"><a href="/speakers.htm">Speakers</a></li>
  <li id="rooms"><a href="#">Rooms</a></li>
  <li id="github"><a href="https://github.com/cloudbees/breizhcamp">GitHub</a></li>
</ul>

<!-- Liens de navigation -->
<div style="position: fixed;top:2px;right:10px">
<a href="<c:url value='/index.htm'/>">Home</a>&nbsp;&nbsp;
<a href="<c:url value='/crud/room/index.htm'/>">Admin</a>
</div>
