<%@ page import="com.charitychamp.Campaign" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'campaign.label', default: 'Campaign')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="manageChampLayout">	
				<g:render template="/shared/configureLinks" />
		
			<div class="manageChampRightPanel">
				<div class="nav" role="navigation">
					<ul>
						
						<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
					</ul>
				</div>
				<div id="create-campaign" class="content scaffold-create" role="main">
					
					<g:if test="${flash.message}">
					<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<g:hasErrors bean="${campaignInstance}">
					<ul class="errors" role="alert">
						<g:eachError bean="${campaignInstance}" var="error">
						
						<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
					</g:hasErrors>
					
					<g:form action="save" >
						<fieldset class="form">
							<g:render template="form"/>
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
