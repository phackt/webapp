<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<!-- http://frameworkonly.com/spring-mvc-4-tiles-3-integration/ -->
<html>
<head>

<%-- 	by default referer is not send for cross origin request (same-origin by default) --%>


<%--    Spring security is automatically adding http security headers --%>
<%--    http://docs.spring.io/spring-security/site/docs/current/reference/html/headers.html --%>
<%--
Cache-Control: no-cache, no-store, max-age=0, must-revalidate  ==> HTTP/1.0
Pragma: no-cache  ==> HTTP/1.1
Expires: 0  ==> ressource is stale (périmée)
X-Content-Type-Options: nosniff  ==> don't attempt to deduce the file format (polyglots files)
Strict-Transport-Security: max-age=31536000 ; includeSubDomains  ==> force browser with https connexion
X-Frame-Options: DENY  ==> prevent page from being included into a frame (for legacy browsers, see 
https://www.owasp.org/index.php/Clickjacking_Defense_Cheat_Sheet#Best-for-now_Legacy_Browser_Frame_Breaking_Script
X-XSS-Protection: 1; mode=block ==> tell the browser to block page if xss is detected by the browser
https://www.owasp.org/index.php/Testing_for_Reflected_Cross_site_scripting_(OWASP-DV-001)
Strict-Transport-Security: max-age=31536000 ; includeSubDomains  =>  fot HTTPS connexions
--%>
	
	<title><tiles:insertAttribute name="title" ignore="true" /></title>	
	<tiles:importAttribute name="stylesheets"/>
	<c:forEach var="css" items="${stylesheets}">
		<link rel="stylesheet" type="text/css" href="<c:url value="${css}"/>">
	</c:forEach>
</head>
<body>
	<div id="wrap">
        <tiles:insertAttribute name="header" />
        <div id="main">
        	<tiles:insertAttribute name="body" />
        </div>
    </div>	
    <tiles:insertAttribute name="footer" />     
    <tiles:importAttribute name="javascripts"/>
    <c:forEach var="js" items="${javascripts}">
    	<script type="text/javascript" src="<c:url value='${js}'/>"></script>
	</c:forEach>

</body>
</html>
