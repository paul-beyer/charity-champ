
<%@ page import="com.charitychamp.GlobalNumericSetting" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="manageChampLayout">	
				<g:render template="/shared/configureLinks" />
		
		<div class="manageChampRightPanel">
				<div class="nav" role="navigation">
					<ul>
						
						<li><g:link class="create" action="createEmployeeGoal"><g:message code="default.new.label" args="['Amt Per Employee Goal']" /></g:link></li>
					</ul>
				</div>
				<div id="list-globalNumericSetting" class="content scaffold-list" role="main">
					
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<table>
						<thead>
							<tr>
							
								<g:sortableColumn property="name" title="${message(code: 'globalNumericSetting.name.label', default: 'Name')}" />
							
								<g:sortableColumn property="value" title="${message(code: 'globalNumericSetting.value.label', default: 'Value')}" />
							
								<g:sortableColumn property="effectiveDate" title="${message(code: 'globalNumericSetting.effectiveDate.label', default: 'Effective Date')}" />
							
							</tr>
						</thead>
						<tbody>
						<g:each in="${globalNumericSettingInstanceList}" status="i" var="globalNumericSettingInstance">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							
								<td><g:link action="showEmployeeGoal" id="${globalNumericSettingInstance.id}">${fieldValue(bean: globalNumericSettingInstance, field: "name")}</g:link></td>
							
								<td>${fieldValue(bean: globalNumericSettingInstance, field: "value")}</td>
							
								<td><g:formatDate date="${globalNumericSettingInstance.effectiveDate}" /></td>
							
							</tr>
						</g:each>
						</tbody>
					</table>
					<div class="pagination">
						<g:paginate total="${globalNumericSettingInstanceTotal}" />
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
