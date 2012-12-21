<%@ page import="com.charitychamp.VolunteerShift" %>
<g:applyLayout name="groupLayout">
<html>
	<head>
		<title>Edit Food Bank Shift</title>
	
	</head>
	<body>
	
	
		<div id="edit-foodBank-shift" class="content scaffold-create" role="main">
		
			<g:hasErrors bean="${volunteerShiftInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${volunteerShiftInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${groupInstance.id}" />
				<g:hiddenField name="shiftId" value="${volunteerShiftInstance.id}" />
				<g:hiddenField name="shiftVersion" value="${volunteerShiftInstance?.version}" />
				<fieldset class="form">
					<g:render template="shiftForm"/>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="updateFoodBankShift" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="delete" action="deleteFoodBankShift" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					<g:actionSubmit class="" action="foodBankShifts" value="${message(code: 'default.button.canel.label', default: 'Cancel')}" />
				</fieldset>
			</g:form>
		</div>
	</body>

</html>
</g:applyLayout>