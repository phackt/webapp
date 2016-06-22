<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	<%@ page contentType="text/html;charset=UTF-8"%>
	
<div class="container">
	<!-- Features Row -->
	<div class="row">
		<div class="col-lg-12">
			<h2 class="page-header">Select your functionality:</h2>
		</div>
		<div class="col-lg-4 col-sm-6 text-center">
			<img usemap="#image-map" 
				class="img-circle img-responsive img-center" 
				src="<c:url value='/resources' />/images/upload_files_200x200.png" 
				alt="Upload files">
			
	<!-- 				https://www.image-map.net/	 -->
			<map name="image-map">
			    <area shape="circle" coords="100,100,99" href="<c:url value='/showFiles' />">
			</map>
			<h3>
				<a href="<c:url value='/showFiles' />">Upload files</a>
			</h3>
			<p>Be totally hidden while updating your files</p>
		</div>
	</div>
</div>	