<%@ page import="com.charitychamp.StringList" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'stringList.label', default: 'StringList')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="manageChampLayout">	
				<g:render template="/shared/configureLinks" />
		
		<div class="manageChampRightPanel">
				<div class="nav" role="navigation">
					<ul>
						
					<li><g:link class="create" action="create"><g:message code="default.new.label" args="['Activity Type']" /></g:link></li>
			</ul>
		</div>
		<div id="list-stringList" class="content scaffold-list" role="main">
			
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="listName" title="${message(code: 'stringList.listName.label', default: 'Name')}" />
					
						<g:sortableColumn property="value" title="${message(code: 'stringList.value.label', default: 'Value')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${stringListInstanceList}" status="i" var="stringListInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="edit" id="${stringListInstance.id}">${fieldValue(bean: stringListInstance, field: "listName")}</g:link></td>
					
						<td>${fieldValue(bean: stringListInstance, field: "value")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${stringListInstanceTotal}" />
			</div>
		</div>
			</div>
		</div>
	</body>
</html>
