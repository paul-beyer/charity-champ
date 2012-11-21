
<%@ page import="com.charitychamp.VolunteerShift" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'volunteerShift.label', default: 'VolunteerShift')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-volunteerShift" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-volunteerShift" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list volunteerShift">
			
				<g:if test="${volunteerShiftInstance?.numberOfVolunteers}">
				<li class="fieldcontain">
					<span id="numberOfVolunteers-label" class="property-label"><g:message code="volunteerShift.numberOfVolunteers.label" default="Number Of Volunteers" /></span>
					
						<span class="property-value" aria-labelledby="numberOfVolunteers-label"><g:fieldValue bean="${volunteerShiftInstance}" field="numberOfVolunteers"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${volunteerShiftInstance?.comments}">
				<li class="fieldcontain">
					<span id="comments-label" class="property-label"><g:message code="volunteerShift.comments.label" default="Comments" /></span>
					
						<span class="property-value" aria-labelledby="comments-label"><g:fieldValue bean="${volunteerShiftInstance}" field="comments"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${volunteerShiftInstance?.leader}">
				<li class="fieldcontain">
					<span id="leader-label" class="property-label"><g:message code="volunteerShift.leader.label" default="Leader" /></span>
					
						<span class="property-value" aria-labelledby="leader-label"><g:link controller="person" action="show" id="${volunteerShiftInstance?.leader?.id}">${volunteerShiftInstance?.leader?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${volunteerShiftInstance?.mealFactor}">
				<li class="fieldcontain">
					<span id="mealFactor-label" class="property-label"><g:message code="volunteerShift.mealFactor.label" default="Meal Factor" /></span>
					
						<span class="property-value" aria-labelledby="mealFactor-label"><g:fieldValue bean="${volunteerShiftInstance}" field="mealFactor"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${volunteerShiftInstance?.campaign}">
				<li class="fieldcontain">
					<span id="campaign-label" class="property-label"><g:message code="volunteerShift.campaign.label" default="Campaign" /></span>
					
						<span class="property-value" aria-labelledby="campaign-label"><g:link controller="campaign" action="show" id="${volunteerShiftInstance?.campaign?.id}">${volunteerShiftInstance?.campaign?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${volunteerShiftInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="volunteerShift.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${volunteerShiftInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${volunteerShiftInstance?.dateOfShift}">
				<li class="fieldcontain">
					<span id="dateOfShift-label" class="property-label"><g:message code="volunteerShift.dateOfShift.label" default="Date Of Shift" /></span>
					
						<span class="property-value" aria-labelledby="dateOfShift-label"><g:formatDate date="${volunteerShiftInstance?.dateOfShift}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${volunteerShiftInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="volunteerShift.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${volunteerShiftInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${volunteerShiftInstance?.id}" />
					<g:link class="edit" action="edit" id="${volunteerShiftInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
