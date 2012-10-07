
<%@ page import="com.charitychamp.Office" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'office.label', default: 'Office')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="manageChampLayout">	
				<g:render template="/shared/setupLinks" />
		
			<div class="manageChampRightPanel">
				<div class="nav" role="navigation">
					<ul>
					
						<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
						<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
					</ul>
				</div>
				<div id="show-office" class="content scaffold-show" role="main">
					
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
					
								
						<g:if test="${officeInstance?.business}">
						<li class="fieldcontain">
							<span id="business-label" class="property-label"><g:message code="office.business.label" default="Business" /></span>
							
								<span class="property-value" aria-labelledby="business-label"><g:link controller="business" action="show" id="${officeInstance?.business?.id}">${officeInstance?.business?.encodeAsHTML()}</g:link></span>
							
						</li>
						</g:if>
					
						<g:if test="${officeInstance?.charityCaptain}">
						<li class="fieldcontain">
							<span id="charityCaptain-label" class="property-label"><g:message code="office.charityCaptain.label" default="Charity Captain" /></span>
							
								<span class="property-value" aria-labelledby="charityCaptain-label"><g:link controller="person" action="show" id="${officeInstance?.charityCaptain?.id}">${officeInstance?.charityCaptain?.encodeAsHTML()}</g:link></span>
							
						</li>
						</g:if>
					
						<g:if test="${officeInstance?.dateCreated}">
						<li class="fieldcontain">
							<span id="dateCreated-label" class="property-label"><g:message code="office.dateCreated.label" default="Date Created" /></span>
							
								<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${officeInstance?.dateCreated}" /></span>
							
						</li>
						</g:if>
					
						<g:if test="${officeInstance?.lastUpdated}">
						<li class="fieldcontain">
							<span id="lastUpdated-label" class="property-label"><g:message code="office.lastUpdated.label" default="Last Updated" /></span>
							
								<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${officeInstance?.lastUpdated}" /></span>
							
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
							<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.office.delete.confirm.message', default: 'Are you sure?')}');" />
						</fieldset>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>
