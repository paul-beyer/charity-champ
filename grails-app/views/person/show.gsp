
<%@ page import="com.charitychamp.Person" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
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
				<div id="show-person" class="content scaffold-show" role="main">
					
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<ol class="property-list person">
					
						<g:if test="${personInstance?.userId}">
						<li class="fieldcontain">
							<span id="userId-label" class="property-label"><g:message code="person.userId.label" default="User Id" /></span>
							
								<span class="property-value" aria-labelledby="userId-label"><g:fieldValue bean="${personInstance}" field="userId"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${personInstance?.firstName}">
						<li class="fieldcontain">
							<span id="firstName-label" class="property-label"><g:message code="person.firstName.label" default="First Name" /></span>
							
								<span class="property-value" aria-labelledby="firstName-label"><g:fieldValue bean="${personInstance}" field="firstName"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${personInstance?.lastName}">
						<li class="fieldcontain">
							<span id="lastName-label" class="property-label"><g:message code="person.lastName.label" default="Last Name" /></span>
							
								<span class="property-value" aria-labelledby="lastName-label"><g:fieldValue bean="${personInstance}" field="lastName"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${personInstance?.phoneNumber}">
						<li class="fieldcontain">
							<span id="phoneNumber-label" class="property-label"><g:message code="person.phoneNumber.label" default="Phone Number" /></span>
							
								<span class="property-value" aria-labelledby="phoneNumber-label"><g:fieldValue bean="${personInstance}" field="phoneNumber"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${personInstance?.altPhoneNumber}">
						<li class="fieldcontain">
							<span id="altPhoneNumber-label" class="property-label"><g:message code="person.altPhoneNumber.label" default="Alt Phone Number" /></span>
							
								<span class="property-value" aria-labelledby="altPhoneNumber-label"><g:fieldValue bean="${personInstance}" field="altPhoneNumber"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${personInstance?.email}">
						<li class="fieldcontain">
							<span id="email-label" class="property-label"><g:message code="person.email.label" default="Email" /></span>
							
								<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${personInstance}" field="email"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${personInstance?.personTitle}">
						<li class="fieldcontain">
							<span id="title-label" class="property-label"><g:message code="person.personTitle.label" default="Title" /></span>
							
								<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${personInstance}" field="personTitle"/></span>
							
						</li>
						</g:if>
					
					</ol>
					<g:form>
						<fieldset class="buttons">
							<g:hiddenField name="id" value="${personInstance?.id}" />
							<g:link class="edit" action="edit" id="${personInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
							<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
						</fieldset>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>
