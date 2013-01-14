
<%@ page import="com.charitychamp.GlobalNumericSetting" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="manageChampLayout">	
				<g:render template="/shared/configureLinks" />
		
		<div class="manageChampRightPanel">
		
				<div id="show-globalNumericSetting" class="content scaffold-show" role="main">
					<h2>MOFB Shift Value</h2>
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<ol class="property-list globalNumericSetting">
					
						<g:if test="${globalNumericSettingInstance?.name}">
						<li class="fieldcontain">
							<span id="name-label" class="property-label"><g:message code="globalNumericSetting.name.label" default="Name" /></span>
							
								<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${globalNumericSettingInstance}" field="name"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${globalNumericSettingInstance?.value}">
						<li class="fieldcontain">
							<span id="value-label" class="property-label"><g:message code="globalNumericSetting.value.label" default="Value" /></span>
							
								<span class="property-value" aria-labelledby="value-label"><g:fieldValue bean="${globalNumericSettingInstance}" field="value"/></span>
							
						</li>
						</g:if>
					
						<g:if test="${globalNumericSettingInstance?.effectiveDate}">
						<li class="fieldcontain">
							<span id="effectiveDate-label" class="property-label"><g:message code="globalNumericSetting.effectiveDate.label" default="Effective Date" /></span>
							
								<span class="property-value" aria-labelledby="effectiveDate-label"><g:formatDate date="${globalNumericSettingInstance?.effectiveDate}" /></span>
							
						</li>
						</g:if>
					
					</ol>
					<g:form>
						<fieldset class="buttons">
							<g:hiddenField name="id" value="${globalNumericSettingInstance?.id}" />
							<g:link class="edit" action="editMofbShiftValue" id="${globalNumericSettingInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link> 
							<g:actionSubmit class="delete" action="deleteMofbShiftValue" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
							<g:actionSubmit class="cancel" action="mofbShiftValue" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" />
						</fieldset>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>
