<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<title>Login page</title>
</head>
<body>
	<h1>Login page!</h1>

	<c:if test="${not empty msgLoginController}">
		<div class="msg">${msgLoginController}</div>
	</c:if>
		
	<form:form name="loginForm" action="<c:url value='/j_spring_security_check' />" method="POST">
		<table>
			<tr>
				<td><form:label path="username">Username:</form:label></td>
				<td><form:input path="username" /></td>
			</tr>
			<tr>
				<td><form:label path="password">Password:</form:label></td>
				<td><form:password path="password" /></td>
			</tr>

			<tr>
				<td colspan="2"><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form:form>

</body>
</html>
