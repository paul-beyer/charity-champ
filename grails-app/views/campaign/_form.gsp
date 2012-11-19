<%@ page import="com.charitychamp.Campaign" %>



<div class="fieldcontain ${hasErrors(bean: campaignInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="campaign.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" value="${campaignInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: campaignInstance, field: 'startDate', 'error')} required">
	<label for="startDate">
		<g:message code="campaign.startDate.label" default="Start Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="startDate" precision="day"  value="${campaignInstance?.startDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: campaignInstance, field: 'endDate', 'error')} required">
	<label for="endDate">
		<g:message code="campaign.endDate.label" default="End Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="endDate" precision="day"  value="${campaignInstance?.endDate}"  />
</div>



