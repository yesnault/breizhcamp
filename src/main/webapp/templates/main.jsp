<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<script type="text/javascript" language="javascript">
NavName = navigator.appName;
if ( NavName == "Microsoft Internet Explorer") {
    window.location="/error/notie.htm";
}
</script>
<script src="/static/js/jquery-1.7.1.min.js"></script>
<script src="/static/js/jquery-ui-1.8.18.custom.min.js"></script>
<script src="/static/js/jquery.ui.datepicker-fr.js"></script>
<head>
  <link href="/static/css/jquery-ui-1.8.18.custom.css" rel="stylesheet">
  <link href="/static/css/bootstrap.css" rel="stylesheet">
  <link href="/static/css/bootstrap-responsive.css" rel="stylesheet">
  <link href='http://fonts.googleapis.com/css?family=Sonsie+One' rel='stylesheet' type='text/css'>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><tiles:getAsString name="title" /></title>
	
</head>

<body>
    <script type='text/javascript' charset='utf-8'>
        $(document).ready(function() {
             parent.scrollTo(0, 0);
        });
    </script>
    <c:if test="${hide!=true}">
	    <tiles:insertAttribute name="header" />
	</c:if>
	<c:if test="${hide==true}">
        <script type='text/javascript' charset='utf-8'>
            function setActive(id){
            }
        </script>
	</c:if>
	<tiles:insertAttribute name="content" />

    <c:if test="${hide!=true}">
	<tiles:insertAttribute name="footer" />
	</c:if>
</body>
</html>
