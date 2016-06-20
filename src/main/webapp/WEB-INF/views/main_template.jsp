<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<!-- http://frameworkonly.com/spring-mvc-4-tiles-3-integration/ -->
<html>
<head>
        <title><tiles:insertAttribute name="title" ignore="true" /></title>
        <c:forEach var="css" items="${stylesheets}">
        	<link rel="stylesheet" type="text/css" href="<c:url value="${css}"/>">
    	</c:forEach>
</head>
<body>
        <tiles:insertAttribute name="header" />
        <tiles:insertAttribute name="body" />
        <tiles:insertAttribute name="footer" />
        <c:forEach var="js" items="${javascripts}">
        	<script type="text/javascript" src="<c:url value='${js}'/>"></script>
    	</c:forEach>
</body>
</html>
