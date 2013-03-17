<%@ page import="com.charitychamp.Office" %>



<div class="fieldcontain ${hasErrors(bean: officeInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="office.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${officeInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: officeInstance, field: 'business', 'error')} required">
	<label for="business">
		<g:message code="office.business.label" default="Business" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="business" name="business.id" from="${com.charitychamp.Business.list()}" optionKey="id" required="" value="${officeInstance?.business?.id}" noSelection="['':'-Choose-']" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: officeInstance, field: 'charityCaptain', 'error')} required">
	<label for="charityCaptain">
		<g:message code="office.charityCaptain.label" default="Charity Captain" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="charityCaptain" name="charityCaptain.id" from="${com.charitychamp.Person.list()}" optionKey="id" required="" value="${officeInstance?.charityCaptain?.id}" noSelection="['':'-Choose-']" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: officeInstance, field: 'leader', 'error')} required">
	<label for="officer">
		<g:message code="office.officer.label" default="Officer" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="officer" name="leader.id" from="${com.charitychamp.Person.list()}" optionKey="id" required="" value="${officeInstance?.leader?.id}" noSelection="['':'-Choose-']" class="many-to-one"/>
</div>

