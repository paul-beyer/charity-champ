
<%@ page import="com.charitychamp.Activity" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-activity" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-activity" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'activity.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="amountCollected" title="${message(code: 'activity.amountCollected.label', default: 'Amount Collected')}" />
						
						<g:sortableColumn property="amountSpent" title="${message(code: 'activity.amountSpent.label', default: 'Amount Spent')}" />
					
						<g:sortableColumn property="leaderName" title="${message(code: 'activity.leaderName.label', default: 'Leader Name')}" />
					
						<g:sortableColumn property="comments" title="${message(code: 'activity.comments.label', default: 'Comments')}" />
					
						<th><g:message code="activity.campaign.label" default="Campaign" /></th>
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'activity.dateCreated.label', default: 'Date Created')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${activityInstanceList}" status="i" var="activityInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${activityInstance.id}">${fieldValue(bean: activityInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: activityInstance, field: "amountCollected")}</td>
						
						<td>${fieldValue(bean: activityInstance, field: "amountSpent")}</td>
					
						<td>${fieldValue(bean: activityInstance, field: "leaderName")}</td>
					
						<td>${fieldValue(bean: activityInstance, field: "comments")}</td>
					
						<td>${fieldValue(bean: activityInstance, field: "campaign")}</td>
					
						<td><g:formatDate date="${activityInstance.dateCreated}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${activityInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
