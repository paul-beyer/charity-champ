<%@ page import="com.charitychamp.Activity" %>
<!doctype html>
<html>
	<head>
		<title>Add Activity</title>
	
	</head>
	<body>
	
	
		<div id="create-activity-dialog" class="content scaffold-create" role="main">
			<h1>Create Activity</h1>
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
			<g:form action="saveActivity" >
				<fieldset class="form">
					<g:render template="activityForm"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
	<div id="saveApplicationDialog" title="Application Area Selection Alert">
	     	<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
	        <p>You must select an Application Area</p>
	
	</div>
</html>
