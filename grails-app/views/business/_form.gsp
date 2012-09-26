<%@ page import="com.charitychamp.Business" %>



<div class="fieldcontain ${hasErrors(bean: businessInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="business.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${businessInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: businessInstance, field: 'departments', 'error')} ">
	<label for="departments">
		<g:message code="business.departments.label" default="Departments" />
		
	</label>
	<g:select name="departments" from="${com.charitychamp.Department.list()}" multiple="multiple" optionKey="id" size="5" value="${businessInstance?.departments*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: businessInstance, field: 'charityCaptain', 'error')} required">
	<label for="charityCaptain">
		<g:message code="business.charityCaptain.label" default="Charity Captain" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="charityCaptain" name="charityCaptain.id" from="${com.charitychamp.Person.list()}" optionKey="id" required="" value="${businessInstance?.charityCaptain?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: businessInstance, field: 'executive', 'error')} required">
	<label for="executive">
		<g:message code="business.executive.label" default="Executive" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="executive" name="executive.id" from="${com.charitychamp.Person.list()}" optionKey="id" required="" value="${businessInstance?.executive?.id}" class="many-to-one"/>
</div>

