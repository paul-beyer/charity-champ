<%@ page import="com.charitychamp.Activity" %>
<g:applyLayout name="groupLayout">
<html>
	<head>
		<title>Edit Activity</title>
	
	</head>
	<body>
	
	
		<div id="edit-activity" class="content scaffold-create" role="main">
			<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${activityInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${activityInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${groupInstance.id}" />
				<g:hiddenField name="activityId" value="${activityInstance.id}" />
				<g:hiddenField name="activityVersion" value="${activityInstance?.version}" />
				<fieldset class="form">
					<g:render template="activityForm"/>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="updateActivity" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="delete" action="deleteActivity" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					<g:actionSubmit class="" action="activities" value="${message(code: 'default.button.canel.label', default: 'Cancel')}" />
				</fieldset>
			</g:form>
		</div>
	</body>

</html>
</g:applyLayout>