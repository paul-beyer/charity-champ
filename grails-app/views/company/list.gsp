
<%@ page import="com.charitychamp.Company" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'company.label', default: 'Company')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="manageChampLayout">	
				<g:render template="/shared/setupLinks" />
		
		<div class="manageChampRightPanel">
		
				
				<div class="nav" role="navigation">
					<ul>
						
						<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
					</ul>
				</div>
				<div id="list-company" class="content scaffold-list" role="main">
					<br/>
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<table>
						<thead>
							<tr>
							
								<g:sortableColumn property="name" title="${message(code: 'company.name.label', default: 'Name')}" />
							
								<th><g:message code="company.ceo.label" default="Ceo" /></th>
							
							</tr>
						</thead>
						<tbody>
						<g:each in="${companyInstanceList}" status="i" var="companyInstance">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							
								<td><g:link action="show" id="${companyInstance.id}">${fieldValue(bean: companyInstance, field: "name")}</g:link></td>
							
								<td>${fieldValue(bean: companyInstance, field: "leader")}</td>
							
							</tr>
						</g:each>
						</tbody>
					</table>
					<div class="pagination">
						<g:paginate total="${companyInstanceTotal}" />
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
