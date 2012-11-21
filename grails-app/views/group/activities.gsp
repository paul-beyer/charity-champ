<%@ page import="com.charitychamp.Activity" %>
<g:applyLayout name="groupLayout">
<html>
	<head>
	
		<title>Activities</title>
	</head>
	<body>
		
		<div id="list-activity" class="content scaffold-list" role="main">
			<h2>Activities</h2><br/>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<button class="minorButtons" id="addActivity" type="button">Add New Activity</button><br/>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'activity.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="amountCollected" title="${message(code: 'activity.amountCollected.label', default: 'Amount Collected')}" />
					
						<g:sortableColumn property="leaderName" title="${message(code: 'activity.leaderName.label', default: 'In Charge')}" />
					
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
		<div id="create-activity-dialog" title="Create New Activity">
			<h1>Create Activity</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${activityInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${activityInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="saveActivity" >
				<fieldset class="form">
					<g:render template="activityForm"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
		<div id="saveApplicationDialog" title="Application Area Selection Alert">
	     	<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
	        <p>You must select an Application Area</p>
	
	</div>
	
	</body>
	
</html>
</g:applyLayout>
