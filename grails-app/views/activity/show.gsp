
<%@ page import="com.charitychamp.Activity" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-activity" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-activity" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list activity">
			
				<g:if test="${activityInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="activity.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${activityInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${activityInstance?.amountCollected}">
				<li class="fieldcontain">
					<span id="amountCollected-label" class="property-label"><g:message code="activity.amountCollected.label" default="Amount Collected" /></span>
					
						<span class="property-value" aria-labelledby="amountCollected-label"><g:fieldValue bean="${activityInstance}" field="amountCollected"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${activityInstance?.leaderName}">
				<li class="fieldcontain">
					<span id="leaderName-label" class="property-label"><g:message code="activity.leaderName.label" default="Leader Name" /></span>
					
						<span class="property-value" aria-labelledby="leaderName-label"><g:fieldValue bean="${activityInstance}" field="leaderName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${activityInstance?.comments}">
				<li class="fieldcontain">
					<span id="comments-label" class="property-label"><g:message code="activity.comments.label" default="Comments" /></span>
					
						<span class="property-value" aria-labelledby="comments-label"><g:fieldValue bean="${activityInstance}" field="comments"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${activityInstance?.depositDate}">
				<li class="fieldcontain">
					<span id="depositDate-label" class="property-label"><g:message code="activity.depositDate.label" default="Deposit Date" /></span>
					
						<span class="property-value" aria-labelledby="depositDate-label"><g:formatDate date="${activityInstance?.depositDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${activityInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="activity.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${activityInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${activityInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="activity.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${activityInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${activityInstance?.id}" />
					<g:link class="edit" action="edit" id="${activityInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
