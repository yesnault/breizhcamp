<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script type='text/javascript' charset='utf-8'>
         setActive('speakers');
</script>

<table border="1" bordercolor="#eee" cellspacing="2px" style="text-align:center;border-collapse:collapse;border-top-color:rgb(238,238,238);border-right-color:rgb(238,238,238);border-bottom-color:rgb(238,238,238);border-left-color:rgb(238,238,238);border-top-width:1px;border-right-width:1px;border-bottom-width:1px;border-left-width:1px">
    <tbody>
        <tr>
            <c:set var="count" value="0"/>
            <c:forEach var="speaker" items="${speakers}">
                <c:if test="${count==6}">
                    <c:set var="count" value="0"/>
                    </tr>
                    <tr>
                </c:if>
                <td style="width:100px">
                    <div style="display:block;text-align:left">
                        <a href="/speaker/${speaker.id}.htm<c:if test="${hide}">?hide=true</c:if>" imageanchor="1">
                            <img border="0" src="${speaker.picture}" style="height:128px;width:128px;" height="128px" width="128px">
                        </a>
                    </div>
                    <div style="text-align:center;display:block;font: normal 10pt Arial,sans-serif;">${speaker.firstName} ${speaker.lastName}</div>
                </td>
                <c:set var="count" value="${count+1}"/>
            </c:forEach>
        </tr>
    </tbody>
</table>

