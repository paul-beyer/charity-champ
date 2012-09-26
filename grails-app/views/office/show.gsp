
<%@ page import="com.charitychamp.Office" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'office.label', default: 'Office')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-office" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-office" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list office">
			
				<g:if test="${officeInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="office.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${officeInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${officeInstance?.businesses}">
				<li class="fieldcontain">
					<span id="businesses-label" class="property-label"><g:message code="office.businesses.label" default="Businesses" /></span>
					
						<g:each in="${officeInstance.businesses}" var="b">
						<span class="property-value" aria-labelledby="businesses-label"><g:link controller="business" action="show" id="${b.id}">${b?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${officeInstance?.teamNumber}">
				<li class="fieldcontain">
					<span id="teamNumber-label" class="property-label"><g:message code="office.teamNumber.label" default="Team Number" /></span>
					
						<span class="property-value" aria-labelledby="teamNumber-label"><g:fieldValue bean="${officeInstance}" field="teamNumber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${officeInstance?.charityLeader}">
				<li class="fieldcontain">
					<span id="charityLeader-label" class="property-label"><g:message code="office.charityLeader.label" default="Charity Leader" /></span>
					
						<span class="property-value" aria-labelledby="charityLeader-label"><g:link controller="person" action="show" id="${officeInstance?.charityLeader?.id}">${officeInstance?.charityLeader?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${officeInstance?.officer}">
				<li class="fieldcontain">
					<span id="officer-label" class="property-label"><g:message code="office.officer.label" default="Officer" /></span>
					
						<span class="property-value" aria-labelledby="officer-label"><g:link controller="person" action="show" id="${officeInstance?.officer?.id}">${officeInstance?.officer?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${officeInstance?.id}" />
					<g:link class="edit" action="edit" id="${officeInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
