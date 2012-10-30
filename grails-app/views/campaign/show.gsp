
<%@ page import="com.charitychamp.Campaign" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'campaign.label', default: 'Campaign')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
			<div class="manageChampLayout">	
				<g:render template="/shared/configureLinks" />
		
			<div class="manageChampRightPanel">
				<div class="nav" role="navigation">
					<ul>
						
						<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
						<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
					</ul>
				</div>
				<div id="show-campaign" class="content scaffold-show" role="main">
					
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<ol class="property-list campaign">
					
						<g:if test="${campaignInstance?.name}">
						<li class="fieldcontain">
							<span id="name-label" class="property-label"><g:message code="campaign.name.label" default="Name" /></span>
							
								<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${campaignInstance}" field="name"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${campaignInstance?.endDate}">
						<li class="fieldcontain">
							<span id="endDate-label" class="property-label"><g:message code="campaign.endDate.label" default="End Date" /></span>
							
								<span class="property-value" aria-labelledby="endDate-label"><g:formatDate date="${campaignInstance?.endDate}" /></span>
							
						</li>
						</g:if>
					
						<g:if test="${campaignInstance?.startDate}">
						<li class="fieldcontain">
							<span id="startDate-label" class="property-label"><g:message code="campaign.startDate.label" default="Start Date" /></span>
							
								<span class="property-value" aria-labelledby="startDate-label"><g:formatDate date="${campaignInstance?.startDate}" /></span>
							
						</li>
						</g:if>
					
					</ol>
					<g:form>
						<fieldset class="buttons">
							<g:hiddenField name="id" value="${campaignInstance?.id}" />
							<g:link class="edit" action="edit" id="${campaignInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
							<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
						</fieldset>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>
