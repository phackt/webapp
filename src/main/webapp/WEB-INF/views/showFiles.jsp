<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<div class="container">	
<sec:authorize access="hasAuthority('PERM_UPLOAD_FILE')">
	<!-- Features Row -->
	<div class="row">
		<div class="col-lg-12">
			<h2 class="page-header"><spring:message code="showFiles.header.upload" />:</h2>
		</div>
		<div class="col-lg-12">
			
			<form:form method="POST" modelAttribute="filesUploadForm" action="uploadFile" id="idFilesUploadForm" enctype="multipart/form-data">
				<table class="table table-hover" id="filesTable">
					<thead>
						<tr>
							<th colspan="2">
								<input class="btn btn-default" type="button" value="<spring:message code="showFiles.button.add_file" />" id="addFile">
								<button type="submit" class="btn btn-default"><spring:message code="showFiles.button.save_files" /></button>
							</th>
						</tr>
						<spring:hasBindErrors name="filesUploadForm">
							<tr>
								<th colspan="2" class="no-background">
									<c:forEach var="err" items="${errors.allErrors}">
										<div class="alert alert-danger" role="alert">
											<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
										 	<c:out value="${err.field}" />: <c:out value="${err.defaultMessage}" />
										</div>
									</c:forEach>
								</th>
							</tr>
						</spring:hasBindErrors>
						<c:if test="${not empty msgListFileController}">
							<tr>
								<th colspan="2" class="no-background">
									<c:forEach var="msgFileController" items="${msgListFileController}">
										<div class="alert alert-success" role="alert">
											<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
										 	${msgFileController}
										</div>
									</c:forEach>
								</th>
							</tr>
						</c:if>
					</thead>
					<tbody>					
						<tr>
							<td>
								<div class="form-group">
									<textarea name="filesUploaded[0].description" class="form-control" rows="3" placeholder="<spring:message code="showFiles.field.description.placeholder" />..."></textarea>					 
								</div>
							</td>
							<td>
								<div class="form-group">
			    					<input type="file" name="filesUploaded[0].file" >
								</div> 
							</td>
						</tr>
					</tbody>
				</table> 
			</form:form>	
		</div>
	</div>
</sec:authorize>
	<div class="row">
		<div class="col-lg-12">
			<h2 class="page-header"><spring:message code="showFiles.header.uploaded" />:</h2>
		</div>
		<div class="col-lg-12">
			<table class="table table-hover" id="filesTable">
				<thead>
					<tr>
						<th><spring:message code="showFiles.table.th.description" /></th>
						<th><spring:message code="showFiles.table.th.name" /></th>
						<th><spring:message code="showFiles.table.th.version" /></th>
						<th><spring:message code="showFiles.table.th.date_upload" /></th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
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
									<a href="downloadFile?id=${file.id}"><spring:message code="showFiles.link.download" /></a>
								</sec:authorize>
							</td>
							<td>
								<sec:authorize access="hasAuthority('PERM_DELETE_FILE')">
									<a href="deleteFile?id=${file.id}"><spring:message code="showFiles.link.delete" /></a>
								</sec:authorize>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>