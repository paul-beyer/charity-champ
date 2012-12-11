<%@ page import="com.charitychamp.GlobalNumericSetting" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="manageChampLayout">	
				<g:render template="/shared/configureLinks" />
		
			<div class="manageChampRightPanel">
				<div class="nav" role="navigation">
					<ul>
						
						<li><g:link class="list" action="mofbShiftValue"><g:message code="default.list.label" args="['MOFB Shift Type']" /></g:link></li>
					</ul>
				</div>
				<div id="create-globalNumericSetting" class="content scaffold-create" role="main">
				
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
					<g:form action="saveMofbShift" >
						<fieldset class="form">
							<g:render template="mofbShiftForm"/>
						</fieldset>
						<fieldset class="buttons">
							<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
						</fieldset>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>
