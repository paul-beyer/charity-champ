<%@ page import="com.charitychamp.JeansPayment" %>



<div class="fieldcontain ${hasErrors(bean: jeansPaymentInstance, field: 'employeeUserId', 'error')} required">
	<label for="employeeUserId">
		<g:message code="jeansPayment.employeeUserId.label" default="Employee User Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="employeeUserId" required="" value="${jeansPaymentInstance?.employeeUserId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: jeansPaymentInstance, field: 'payerFirstName', 'error')} required">
	<label for="payerFirstName">
		<g:message code="jeansPayment.payerFirstName.label" default="Payer First Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="payerFirstName" required="" value="${jeansPaymentInstance?.payerFirstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: jeansPaymentInstance, field: 'payerLastName', 'error')} required">
	<label for="payerLastName">
		<g:message code="jeansPayment.payerLastName.label" default="Payer Last Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="payerLastName" required="" value="${jeansPaymentInstance?.payerLastName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: jeansPaymentInstance, field: 'payerPhone', 'error')} ">
	<label for="payerPhone">
		<g:message code="jeansPayment.payerPhone.label" default="Payer Phone" />
		
	</label>
	<g:textField name="payerPhone" value="${jeansPaymentInstance?.payerPhone}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: jeansPaymentInstance, field: 'payerEmail', 'error')} ">
	<label for="payerEmail">
		<g:message code="jeansPayment.payerEmail.label" default="Payer Email" />
		
	</label>
	<g:field type="email" name="payerEmail" value="${jeansPaymentInstance?.payerEmail}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: jeansPaymentInstance, field: 'amountCollected', 'error')} required">
	<label for="amtPaid">
		<g:message code="jeansPayment.amtPaid.label" default="Amt Paid" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="amtPaid" min="1" required="" value="${fieldValue(bean: jeansPaymentInstance, field: 'amountCollected')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: jeansPaymentInstance, field: 'campaign', 'error')} required">
	<label for="campaign">
		<g:message code="jeansPayment.campaign.label" default="Campaign" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="campaign" name="campaign.id" from="${com.charitychamp.Campaign.list()}" optionKey="id" required="" value="${jeansPaymentInstance?.campaign?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: jeansPaymentInstance, field: 'dateOfPayment', 'error')} required">
	<label for="dateOfPayment">
		<g:message code="jeansPayment.dateOfPayment.label" default="Date Of Payment" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateOfPayment" precision="day"  value="${jeansPaymentInstance?.dateOfPayment}"  />
</div>

