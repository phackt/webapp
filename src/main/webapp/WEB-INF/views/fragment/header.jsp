<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> 
				<span class="icon-bar"></span> 
			</button>
			<a class="navbar-brand" href="<c:url value='/home' />">DemoWebApp</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="<c:url value='/showFiles' />"><spring:message code="header.label.upload_files" /></a></li>
			</ul>
			<ul class="nav navbar-nav">
				<li><a href="#"><spring:message code="header.label.scan_cors" /></a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" 
					class="dropdown-toggle"
					data-toggle="dropdown" 
					role="button" 
					aria-haspopup="true"
					aria-expanded="false">
					<b><sec:authentication property="principal.username" /></b>
					<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li>
							<a href="#" id="submitFormLogout">Logout</a>
						</li>
						<c:url var="logoutRoute" value='/logout' />
						<form:form action="${logoutRoute}" method="POST" id="formLogout" />
					</ul>
				</li>
				<li class="dropdown">
					<a href="#" 
					class="dropdown-toggle"
					data-toggle="dropdown" 
					role="button" 
					aria-haspopup="true"
					aria-expanded="false">
					<b>locale ${pageContext.response.locale}</b>
					<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li>
							<c:choose>
								<c:when test="${pageContext.response.locale == 'en'}">
									<c:set var="lang" value="fr" />
								</c:when>
								<c:otherwise>
									<c:set var="lang" value="en" />
								</c:otherwise>
							</c:choose> 
							<a href="<c:url value='/home' />?lang=${lang}">
								<c:out value="${lang}"/>
							</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
	<!--/.container-fluid -->
</nav>
