<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<h1>Show Files</h1>

<sec:authorize access="hasAuthority('PERM_UPLOAD_FILE')">

	<span style="color: red;"> 
		<spring:hasBindErrors name="filesUploadForm">
			<c:forEach var="err" items="${errors.allErrors}">
				<c:out value="${err.field}" /> :
				<c:out value="${err.defaultMessage}" />
				<br />
			</c:forEach>
		</spring:hasBindErrors>
	</span>
	
	<br />
	<br />
	<h3>Add a new file </h3>

	<form:form method="POST" modelAttribute="filesUploadForm" action="uploadFile" id="idFilesUploadForm" enctype="multipart/form-data">
		<table id="filesTable">
			<tr>					
				<td><label for="filesUploaded[0].description">Description:</label></td>
			</tr>
			<tr>
				<td><textarea name="filesUploaded[0].description" rows="5" cols="30"></textarea></td>	
			</tr>
			<tr>
				<td><input type="file" name="filesUploaded[0].file" /></td>
			</tr>
			<tr>
				<button type="button" id="addFile">Add</button>
				<td colspan="2"><input type="submit" value="Save File" /></td>
			</tr>
			<c:if test="${not empty msgFileController}">
				<tr>
					<td>${msgFileController}</td>
				</tr>	
			</c:if>
		</table>
	</form:form>
</sec:authorize>

	<table>
		<tr>
			<th>Description</th>
			<th>Name</th>
			<th>Version</th>
			<th>Date Upload</th>
			<th></th>
			<th></th>
		</tr>
		<c:forEach var="file" items="${listFiles}">
			<tr>
				<td><c:out value="${file.description}" /></td>
				<td><c:out value="${file.originalFilename}" /></td>
				<td><c:out value="${file.version}" /></td>
				<td>
					<fmt:formatDate value="${file.dateUpload}" pattern="dd/MM/yyyy" />
				</td>
				<td>
					<sec:authorize access="hasAuthority('PERM_DOWNLOAD_FILE')">
						<a href="downloadFile?id=${file.id}">Download</a>
					</sec:authorize>
				</td>
				<td>
					<sec:authorize access="hasAuthority('PERM_DELETE_FILE')">
						<a href="deleteFile?id=${file.id}">Delete</a>
					</sec:authorize>
				</td>
			</tr>
		</c:forEach>
	</table>
