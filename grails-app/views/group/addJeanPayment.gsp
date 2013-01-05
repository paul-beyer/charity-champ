<%@ page import="com.charitychamp.JeansPayment" %>
<g:applyLayout name="groupLayout">
<html>
	<head>
		<title>Add Jean Payment</title>
	
	</head>
	<body>
	
	
		<div id="create-jean-payment" class="content scaffold-create" role="main">
		
			<g:hasErrors bean="${jeansPaymentInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${jeansPaymentInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="saveJeanPayment" >
				<g:hiddenField name="groupId" value="${groupInstance.id}" />
				<fieldset class="form">
					<g:render template="jeanPayment"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</body>

</html>
</g:applyLayout>