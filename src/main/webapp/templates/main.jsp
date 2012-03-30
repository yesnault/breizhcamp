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
<head>
  <link href="/static/css/bootstrap.css" rel="stylesheet">
  <link href="/static/css/bootstrap-responsive.css" rel="stylesheet">
  <link href='http://fonts.googleapis.com/css?family=Sonsie+One' rel='stylesheet' type='text/css'>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><tiles:getAsString name="title" /></title>

	
</head>

<body>
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="content" />
	<tiles:insertAttribute name="footer" />
    <script src="/static/js/bootstrap-transition.js"></script>
</body>
</html>
