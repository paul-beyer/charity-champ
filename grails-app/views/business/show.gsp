
<%@ page import="com.charitychamp.Business" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'business.label', default: 'Business')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-business" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-business" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list business">
			
				<g:if test="${businessInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="business.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${businessInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${businessInstance?.departments}">
				<li class="fieldcontain">
					<span id="departments-label" class="property-label"><g:message code="business.departments.label" default="Departments" /></span>
					
						<g:each in="${businessInstance.departments}" var="d">
						<span class="property-value" aria-labelledby="departments-label"><g:link controller="department" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${businessInstance?.charityCaptain}">
				<li class="fieldcontain">
					<span id="charityCaptain-label" class="property-label"><g:message code="business.charityCaptain.label" default="Charity Captain" /></span>
					
						<span class="property-value" aria-labelledby="charityCaptain-label"><g:link controller="person" action="show" id="${businessInstance?.charityCaptain?.id}">${businessInstance?.charityCaptain?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${businessInstance?.executive}">
				<li class="fieldcontain">
					<span id="executive-label" class="property-label"><g:message code="business.executive.label" default="Executive" /></span>
					
						<span class="property-value" aria-labelledby="executive-label"><g:link controller="person" action="show" id="${businessInstance?.executive?.id}">${businessInstance?.executive?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${businessInstance?.id}" />
					<g:link class="edit" action="edit" id="${businessInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
