<%@ page import="com.charitychamp.Activity" %>
<g:applyLayout name="groupLayout">
<html>
	<head>
		<title>Add Activity</title>
	
	</head>
	<body>
	
	
		<div id="create-activity" class="content scaffold-create" role="main">
		
			<g:hasErrors bean="${activityInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${activityInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="saveActivity" >
				<g:hiddenField name="groupId" value="${groupInstance.id}" />
				<fieldset class="form">
					<g:render template="activityForm"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</body>

</html>
</g:applyLayout>