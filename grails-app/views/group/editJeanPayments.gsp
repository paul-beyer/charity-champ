<%@ page import="com.charitychamp.JeansPayment" %>
<g:applyLayout name="groupLayout">
<html>
	<head>
		<title>Edit Jean Payment</title>
	
	</head>
	<body>
	
	
		<div id="edit-jeanPayment" class="content scaffold-create" role="main">
			<g:if test="${flash.message}">
					<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${jeansPaymentInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${jeansPaymentInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form method="post" >
				<g:hiddenField name="id" value="${groupInstance.id}" />
				<g:hiddenField name="jeanPaymentId" value="${jeansPaymentInstance.id}" />
				<g:hiddenField name="jeanPaymentVersion" value="${jeansPaymentInstance?.version}" />
				<fieldset class="form">
					<g:render template="jeanPayment"/>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="updateJeanPayment" value="${message(code: 'default.button.update.label', default: 'Update')}" />
					<g:actionSubmit class="delete" action="deleteJeanPayment" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					<g:actionSubmit class="" action="jeanPayments" value="${message(code: 'default.button.canel.label', default: 'Cancel')}" />
				</fieldset>
			</g:form>
		</div>
	</body>

</html>
</g:applyLayout>