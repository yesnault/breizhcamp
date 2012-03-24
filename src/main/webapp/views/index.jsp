<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <link href='http://fonts.googleapis.com/css?family=Sonsie+One' rel='stylesheet' type='text/css'>
  <style type="text/css">
   html {
     background: url('<c:url value='/static/BreizhCamp.png'/>') no-repeat top center;
   }
   h2 {
     font-family: 'Sonsie One', cursive;
     margin-top:400px;
     text-align: center;
     font-size:60px;
   }
  </style>
  <title>CloudBees au BreizhCamp !</title>
</head>
<body>
<h2>Programme du BreizhCamp !</h2>

<ul>
	<c:forEach var="talk" items="${talks}">
    <li>${talk.title}</li>
	</c:forEach>
</ul>

<img src="<c:url value='/static/Button-Built-on-CB-1.png'/>" style="position: fixed;bottom:60px">
<img src="<c:url value='/static/Button-Powered-by-CB.png'/>" style="position: fixed;bottom:2px">

</body>
</html>
