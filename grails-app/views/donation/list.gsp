
<%@ page import="com.charitychamp.Donation" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'donation.label', default: 'Donation')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-donation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-donation" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="amount" title="${message(code: 'donation.amount.label', default: 'Amount')}" />
					
						<g:sortableColumn property="purpose" title="${message(code: 'donation.purpose.label', default: 'Purpose')}" />
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'donation.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'donation.lastUpdated.label', default: 'Last Updated')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${donationInstanceList}" status="i" var="donationInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${donationInstance.id}">${fieldValue(bean: donationInstance, field: "amount")}</g:link></td>
					
						<td>${fieldValue(bean: donationInstance, field: "purpose")}</td>
					
						<td><g:formatDate date="${donationInstance.dateCreated}" /></td>
					
						<td><g:formatDate date="${donationInstance.lastUpdated}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${donationInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
