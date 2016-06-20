<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<h1>
	ALL links to features  
</h1>
<br/>
<%-- <a href="<c:url value="/logout" />">Logout</a> --%>
<form:form action="logout" method="POST">
    <input type="submit" value="Logout" />
</form:form>

<P>  Describe all features here. </P>
<a href="showFiles">Upload files</a>
<br />

