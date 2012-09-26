
<%@ page import="com.charitychamp.Group" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'group.label', default: 'Group')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-group" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-group" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list group">
			
				<g:if test="${groupInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="group.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${groupInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${groupInstance?.leader}">
				<li class="fieldcontain">
					<span id="leader-label" class="property-label"><g:message code="group.leader.label" default="Leader" /></span>
					
						<span class="property-value" aria-labelledby="leader-label"><g:link controller="person" action="show" id="${groupInstance?.leader?.id}">${groupInstance?.leader?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${groupInstance?.activities}">
				<li class="fieldcontain">
					<span id="activities-label" class="property-label"><g:message code="group.activities.label" default="Activities" /></span>
					
						<g:each in="${groupInstance.activities}" var="a">
						<span class="property-value" aria-labelledby="activities-label"><g:link controller="activity" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${groupInstance?.volunteerShifts}">
				<li class="fieldcontain">
					<span id="volunteerShifts-label" class="property-label"><g:message code="group.volunteerShifts.label" default="Volunteer Shifts" /></span>
					
						<g:each in="${groupInstance.volunteerShifts}" var="v">
						<span class="property-value" aria-labelledby="volunteerShifts-label"><g:link controller="volunteerShift" action="show" id="${v.id}">${v?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${groupInstance?.jeansPayments}">
				<li class="fieldcontain">
					<span id="jeansPayments-label" class="property-label"><g:message code="group.jeansPayments.label" default="Jeans Payments" /></span>
					
						<g:each in="${groupInstance.jeansPayments}" var="j">
						<span class="property-value" aria-labelledby="jeansPayments-label"><g:link controller="jeansPayment" action="show" id="${j.id}">${j?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${groupInstance?.id}" />
					<g:link class="edit" action="edit" id="${groupInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
