<%@ page import="com.charitychamp.GlobalNumericSetting" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'globalNumericSetting.label', default: 'GlobalNumericSetting')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="manageChampLayout">	
				<g:render template="/shared/configureLinks" />
		
		<div class="manageChampRightPanel">

				<div id="edit-globalNumericSetting" class="content scaffold-edit" role="main">
					<h2>Edit Meals a Dollar Buys</h2>
					<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
					</g:if>
					<g:hasErrors bean="${globalNumericSettingInstance}">
					<ul class="errors" role="alert">
						<g:eachError bean="${globalNumericSettingInstance}" var="error">
						<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
					</g:hasErrors>
					<g:form method="post" >
						<g:hiddenField name="id" value="${globalNumericSettingInstance?.id}" />
						<g:hiddenField name="version" value="${globalNumericSettingInstance?.version}" />
						<fieldset class="form">
							<g:render template="form"/>
						</fieldset>
						<fieldset class="buttons">
						<%-- 	<g:actionSubmit class="save" action="updateMealsDollarBuys" value="${message(code: 'default.button.update.label', default: 'Update')}" />  --%>
							<g:actionSubmit class="delete" action="deleteMealsDollarBuys" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
							<g:actionSubmit class="cancel" action="mealsADollarBuys" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" />
						</fieldset>
					</g:form>
				</div>
			</div>
		</div>
	</body>
</html>
