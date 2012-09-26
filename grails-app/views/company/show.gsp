
<%@ page import="com.charitychamp.Company" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'company.label', default: 'Company')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-company" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-company" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list company">
			
				<g:if test="${companyInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="company.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${companyInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${companyInstance?.offices}">
				<li class="fieldcontain">
					<span id="offices-label" class="property-label"><g:message code="company.offices.label" default="Offices" /></span>
					
						<g:each in="${companyInstance.offices}" var="o">
						<span class="property-value" aria-labelledby="offices-label"><g:link controller="office" action="show" id="${o.id}">${o?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${companyInstance?.globalSettings}">
				<li class="fieldcontain">
					<span id="globalSettings-label" class="property-label"><g:message code="company.globalSettings.label" default="Global Settings" /></span>
					
						<g:each in="${companyInstance.globalSettings}" var="g">
						<span class="property-value" aria-labelledby="globalSettings-label"><g:link controller="globalNumericSetting" action="show" id="${g.id}">${g?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${companyInstance?.ceo}">
				<li class="fieldcontain">
					<span id="ceo-label" class="property-label"><g:message code="company.ceo.label" default="Ceo" /></span>
					
						<span class="property-value" aria-labelledby="ceo-label"><g:link controller="person" action="show" id="${companyInstance?.ceo?.id}">${companyInstance?.ceo?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${companyInstance?.id}" />
					<g:link class="edit" action="edit" id="${companyInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
