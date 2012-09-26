<%@ page import="com.charitychamp.Office" %>



<div class="fieldcontain ${hasErrors(bean: officeInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="office.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${officeInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: officeInstance, field: 'businesses', 'error')} ">
	<label for="businesses">
		<g:message code="office.businesses.label" default="Businesses" />
		
	</label>
	<g:select name="businesses" from="${com.charitychamp.Business.list()}" multiple="multiple" optionKey="id" size="5" value="${officeInstance?.businesses*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: officeInstance, field: 'teamNumber', 'error')} required">
	<label for="teamNumber">
		<g:message code="office.teamNumber.label" default="Team Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="teamNumber" required="" value="${officeInstance?.teamNumber}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: officeInstance, field: 'charityLeader', 'error')} required">
	<label for="charityLeader">
		<g:message code="office.charityLeader.label" default="Charity Leader" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="charityLeader" name="charityLeader.id" from="${com.charitychamp.Person.list()}" optionKey="id" required="" value="${officeInstance?.charityLeader?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: officeInstance, field: 'officer', 'error')} required">
	<label for="officer">
		<g:message code="office.officer.label" default="Officer" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="officer" name="officer.id" from="${com.charitychamp.Person.list()}" optionKey="id" required="" value="${officeInstance?.officer?.id}" class="many-to-one"/>
</div>

