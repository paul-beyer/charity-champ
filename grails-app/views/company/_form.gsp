<%@ page import="com.charitychamp.Company" %>



<div class="fieldcontain ${hasErrors(bean: companyInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="company.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${companyInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: companyInstance, field: 'offices', 'error')} ">
	<label for="offices">
		<g:message code="company.offices.label" default="Offices" />
		
	</label>
	<g:select name="offices" from="${com.charitychamp.Office.list()}" multiple="multiple" optionKey="id" size="5" value="${companyInstance?.offices*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: companyInstance, field: 'globalSettings', 'error')} ">
	<label for="globalSettings">
		<g:message code="company.globalSettings.label" default="Global Settings" />
		
	</label>
	<g:select name="globalSettings" from="${com.charitychamp.GlobalNumericSetting.list()}" multiple="multiple" optionKey="id" size="5" value="${companyInstance?.globalSettings*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: companyInstance, field: 'ceo', 'error')} required">
	<label for="ceo">
		<g:message code="company.ceo.label" default="Ceo" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="ceo" name="ceo.id" from="${com.charitychamp.Person.list()}" optionKey="id" required="" value="${companyInstance?.ceo?.id}" class="many-to-one"/>
</div>

