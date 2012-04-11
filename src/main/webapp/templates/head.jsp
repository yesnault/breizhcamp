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
   fieldset {
    padding:10px;
   }
   .baspage {
     margin-bottom:35px;
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

  <script type="text/javascript">
  	jQuery(function(){
        var kKeys = [];
        function Kpress(e){
            kKeys.push(e.keyCode);
            if (kKeys.toString().indexOf("38,38,40,40,37,39,37,39,66,65") >= 0) {
                jQuery(this).unbind('keydown', Kpress);
                kExec();
            }
        }
        jQuery(document).keydown(Kpress);
    });
    function kExec(){
        $('html').css({"background":"url('<c:url value='/static/BreizhCamp_invert.png'/>') no-repeat top center"});
    }
  </script>

       <script type='text/javascript' charset='utf-8'>
                function setActive(id){
                     document.getElementById(id).className='active';
                }
       </script>
<!-- Onglets -->
<ul class="nav  nav-tabs">
  <li id="programme"><a href="/index.htm">Programme</a></li>
  <li id="speakers"><a href="/speakers.htm">Speakers</a></li>
  <li id="github"><a href="https://github.com/BreizhJUG/breizhcamp" target="_blank">GitHub</a></li>
  <li id="breizhjug"><a href="http://www.breizhjug.org/" target="_blank">BreizhJug</a></li>
  <li id="contact"><a href="/contact.htm">Contact</a></li>
</ul>

<!-- Liens de navigation -->
<div style="position: fixed;top:2px;right:10px">
<a href="<c:url value='/index.htm'/>">Home</a>&nbsp;&nbsp;
<a href="<c:url value='/crud/room/index.htm'/>">Admin</a>
</div>
