<%@ page import="com.charitychamp.GlobalNumericSetting" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="manageChampLayout">	
				<g:render template="/shared/configureLinks" />
		
		<div class="manageChampRightPanel">

				<div id="edit-globalNumericSetting" class="content scaffold-edit" role="main">
		
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<g:hasErrors bean="${globalNumericSettingInstance}">
					<ul class="errors" role="alert">
						<g:eachError bean="${globalNumericSettingInstance}" var="error">
						<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
					</g:hasErrors>
					
				</div>
			</div>
		</div>
	</body>
</html>
