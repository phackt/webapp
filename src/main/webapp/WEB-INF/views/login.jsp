<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html;charset=UTF-8"%>


<form name="loginForm" action="login" method="POST" class="form-signin">
	<h2 class="form-signin-heading">
		<spring:message code="login.label.auth" />:
	</h2>
	
	<label for="inputPassword" class="sr-only"><spring:message code="login.label.username" /></label>
    <input type="text" id="inputPassword" name="username" class="form-control" placeholder="<spring:message code="login.label.username" />" required autofocus>
    
    <label for="inputPassword" class="sr-only"><spring:message code="login.label.password" /></label>
    <input type="password" id="inputPassword" name="password" class="form-control" placeholder="<spring:message code="login.label.password" />" required>
    
    <c:if test="${not empty msgLoginController}">
    
    	<c:set var="classAlert" value="alert-success"/>
    	<c:if test="${isErrorClassActive}">
    		<c:set var="classAlert" value="alert-danger"/>
    	</c:if>
    	
	    <div class="alert ${classAlert}" role="alert">
		  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
		  <span class="sr-only">Error:</span>
		  ${msgLoginController}
		</div>
	</c:if>
	
    <div class="checkbox">
		<label>
			<input type="checkbox" name="remember-me" value="remember-me"><spring:message code="login.label.remember_me" />
		</label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="login.label.sign_in" /></button>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<div id="login_reco" class="alert alert-warning" role="alert">
	<span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
	<span class="sr-only">Warning:</span>
	<strong><spring:message code="login.recommendations.header" /></strong>
	<ul>
		<li><spring:message code="login.recommendations.reco1" /></li>
		<li><spring:message code="login.recommendations.reco2" /></li>
	</ul>
</div>



