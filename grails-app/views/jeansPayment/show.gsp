
<%@ page import="com.charitychamp.JeansPayment" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'jeansPayment.label', default: 'JeansPayment')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-jeansPayment" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-jeansPayment" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list jeansPayment">
			
				<g:if test="${jeansPaymentInstance?.employeeUserId}">
				<li class="fieldcontain">
					<span id="employeeUserId-label" class="property-label"><g:message code="jeansPayment.employeeUserId.label" default="Employee User Id" /></span>
					
						<span class="property-value" aria-labelledby="employeeUserId-label"><g:fieldValue bean="${jeansPaymentInstance}" field="employeeUserId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${jeansPaymentInstance?.payerFirstName}">
				<li class="fieldcontain">
					<span id="payerFirstName-label" class="property-label"><g:message code="jeansPayment.payerFirstName.label" default="Payer First Name" /></span>
					
						<span class="property-value" aria-labelledby="payerFirstName-label"><g:fieldValue bean="${jeansPaymentInstance}" field="payerFirstName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${jeansPaymentInstance?.payerLastName}">
				<li class="fieldcontain">
					<span id="payerLastName-label" class="property-label"><g:message code="jeansPayment.payerLastName.label" default="Payer Last Name" /></span>
					
						<span class="property-value" aria-labelledby="payerLastName-label"><g:fieldValue bean="${jeansPaymentInstance}" field="payerLastName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${jeansPaymentInstance?.payerPhone}">
				<li class="fieldcontain">
					<span id="payerPhone-label" class="property-label"><g:message code="jeansPayment.payerPhone.label" default="Payer Phone" /></span>
					
						<span class="property-value" aria-labelledby="payerPhone-label"><g:fieldValue bean="${jeansPaymentInstance}" field="payerPhone"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${jeansPaymentInstance?.payerEmail}">
				<li class="fieldcontain">
					<span id="payerEmail-label" class="property-label"><g:message code="jeansPayment.payerEmail.label" default="Payer Email" /></span>
					
						<span class="property-value" aria-labelledby="payerEmail-label"><g:fieldValue bean="${jeansPaymentInstance}" field="payerEmail"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${jeansPaymentInstance?.amtPaid}">
				<li class="fieldcontain">
					<span id="amtPaid-label" class="property-label"><g:message code="jeansPayment.amtPaid.label" default="Amt Paid" /></span>
					
						<span class="property-value" aria-labelledby="amtPaid-label"><g:fieldValue bean="${jeansPaymentInstance}" field="amtPaid"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${jeansPaymentInstance?.campaign}">
				<li class="fieldcontain">
					<span id="campaign-label" class="property-label"><g:message code="jeansPayment.campaign.label" default="Campaign" /></span>
					
						<span class="property-value" aria-labelledby="campaign-label"><g:link controller="campaign" action="show" id="${jeansPaymentInstance?.campaign?.id}">${jeansPaymentInstance?.campaign?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${jeansPaymentInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="jeansPayment.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${jeansPaymentInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${jeansPaymentInstance?.dateOfPayment}">
				<li class="fieldcontain">
					<span id="dateOfPayment-label" class="property-label"><g:message code="jeansPayment.dateOfPayment.label" default="Date Of Payment" /></span>
					
						<span class="property-value" aria-labelledby="dateOfPayment-label"><g:formatDate date="${jeansPaymentInstance?.dateOfPayment}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${jeansPaymentInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="jeansPayment.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${jeansPaymentInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${jeansPaymentInstance?.id}" />
					<g:link class="edit" action="edit" id="${jeansPaymentInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
