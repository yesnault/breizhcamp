<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type='text/javascript' charset='utf-8'>
         setActive('speakers');
</script>
<h2>Speakers</h2>


<div class="row">
  <div class="span12">
    <% int i = 0; %>
    <c:forEach var="speaker" items="${speakers}">
         <% if(i==0){ %>
            <div class="row">
        <% }  %>
          <div class="span6">${speaker.firstName} ${speaker.lastName}</div>
        <% if(i==1){ %>
         </div>
        <%  i=0;
          } else{
            i++;
          }
          %>
    </c:forEach>
  </div>
</div>
