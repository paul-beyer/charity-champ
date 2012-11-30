<%@ page import="com.charitychamp.Company" %>



<div class="fieldcontain ${hasErrors(bean: companyInstance, field: 'name', 'error')} required form-field">
	<label for="name">
		<g:message code="company.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${companyInstance?.name}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: companyInstance, field: 'ceo', 'error')} required form-field">
	<label for="ceo">
		<g:message code="company.ceo.label" default="Ceo" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="ceo" name="leader.id" from="${com.charitychamp.Person.list()}" optionKey="id" required="" value="${companyInstance?.leader?.id}"  noSelection="['':'-Choose-']" class="many-to-one"/>
</div>

