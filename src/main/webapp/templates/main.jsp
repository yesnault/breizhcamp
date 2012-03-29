<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
  <link href="/static/css/bootstrap.css" rel="stylesheet">
  <link href="/static/css/bootstrap-responsive.css" rel="stylesheet">

  <link href='http://fonts.googleapis.com/css?family=Sonsie+One' rel='stylesheet' type='text/css'>
  <link href='/static/css/bootstrap.min.css' rel='stylesheet' type='text/css'>
  <script href='/static/js/bootstrap.min.js' type='text/javascript' charset='utf-8'></script>
  <style type="text/css">
   html {
     background: url('<c:url value='/static/BreizhCamp.png'/>') no-repeat top center;
   }
   body {
     margin-top:400px;
   }
   h2 {
     font-family: 'Sonsie One', cursive;
     text-align: center;
     font-size:60px;
   }
    h3 {
     font-family: 'Sonsie One', cursive;
     text-align: center;
     font-size:30px;
   }
  </style>
  <title><tiles:getAsString name="title" /></title>

	
</head>

<body>
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="content" />
	<tiles:insertAttribute name="footer" />


    <script src="/static/js/bootstrap-transition.js"></script>
</body>
</html>