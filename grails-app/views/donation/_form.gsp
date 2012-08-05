<%@ page import="com.charitychamp.Donation" %>



<div class="fieldcontain ${hasErrors(bean: donationInstance, field: 'amount', 'error')} required">
	<label for="amount">
		<g:message code="donation.amount.label" default="Amount" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="amount" value="${fieldValue(bean: donationInstance, field: 'amount')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: donationInstance, field: 'purpose', 'error')} required">
	<label for="purpose">
		<g:message code="donation.purpose.label" default="Purpose" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="purpose" required="" value="${donationInstance?.purpose}"/>
</div>

