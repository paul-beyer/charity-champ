<%@ page import="com.charitychamp.Business" %>



<div class="fieldcontain ${hasErrors(bean: businessInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="business.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${businessInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: businessInstance, field: 'teamNumber', 'error')} required">
	<label for="teamNumber">
		<g:message code="business.teamNumber.label" default="Team Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="teamNumber" required="" value="${businessInstance?.teamNumber}"/>
</div>



<div class="fieldcontain ${hasErrors(bean: businessInstance, field: 'charityLeader', 'error')} required">
	<label for="charityLeader">
		<g:message code="business.charityLeader.label" default="Charity Leader" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="charityLeader" name="charityLeader.id" from="${com.charitychamp.Person.list()}" optionKey="id" required="" value="${businessInstance?.charityLeader?.id}" noSelection="['':'-Choose-']" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: businessInstance, field: 'company', 'error')} required">
	<label for="company">
		<g:message code="business.company.label" default="Company" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="company" name="company.id" from="${com.charitychamp.Company.list()}" optionKey="id" required="" value="${businessInstance?.company?.id}" noSelection="['':'-Choose-']" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: businessInstance, field: 'executive', 'error')} required">
	<label for="executive">
		<g:message code="business.executive.label" default="Executive" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="executive" name="executive.id" from="${com.charitychamp.Person.list()}" optionKey="id" required="" value="${businessInstance?.executive?.id}" noSelection="['':'-Choose-']" class="many-to-one"/>
</div>

