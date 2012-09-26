
<%@ page import="com.charitychamp.GlobalNumericSetting" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-globalNumericSetting" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-globalNumericSetting" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
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
					
						<td><g:link action="show" id="${globalNumericSettingInstance.id}">${fieldValue(bean: globalNumericSettingInstance, field: "name")}</g:link></td>
					
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
	</body>
</html>
