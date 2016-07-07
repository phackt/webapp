<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
	<%@ page contentType="text/html;charset=UTF-8"%>
	
<div class="container">
	<!-- Features Row -->
	<div class="row">
		<div class="col-lg-12">
			<h2 class="page-header"><spring:message code="home.label.functionality" />:</h2>
		</div>
		<!-- Spring Form + Servlet -->
		<div class="col-lg-4 col-sm-6 text-center">
			<img usemap="#image-map" 
				class="img-circle img-responsive img-center" 
				src="<c:url value='/resources' />/images/spring_logo_200x200.png" 
				alt="Upload files">
			
	<!-- 				https://www.image-map.net/	 -->
			<map name="image-map">
			    <area shape="circle" coords="100,100,99" href="<c:url value='/showFiles' />">
			</map>
			<h3>
				<a href="<c:url value='/showFiles' />"><spring:message code="home.link.upload_files" /></a>
				<small><spring:message code="home.label.uploadFiles.springForm.small_func_description" /></small>
			</h3>
			<p><spring:message code="home.label.uploadFiles.full_func_description" /></p>
		</div>
		
		<!-- Angular + Rest controller -->
		<div class="col-lg-4 col-sm-6 text-center">
			<img usemap="#image-map" 
				class="img-circle img-responsive img-center" 
				src="<c:url value='/resources' />/images/angularjs_logo_200x200.png" 
				alt="Scan for CORS">
			
	<!-- 				https://www.image-map.net/	 -->
			<map name="image-map">
			    <area shape="circle" coords="100,100,99" href="<c:url value='/showFiles' />">
			</map>
			<h3>
				<a href="#"><spring:message code="home.link.scan_cors" /></a>
				<small><spring:message code="home.label.uploadFiles.angularRest.small_func_description" /></small>
			</h3>
			<p><spring:message code="home.label.scan_cors.full_func_description" /></p>
		</div>
	</div>
</div>	