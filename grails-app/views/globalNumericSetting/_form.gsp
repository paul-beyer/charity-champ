<%@ page import="com.charitychamp.GlobalNumericSetting" %>



<div class="fieldcontain ${hasErrors(bean: globalNumericSettingInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="globalNumericSetting.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${globalNumericSettingInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: globalNumericSettingInstance, field: 'value', 'error')} required">
	<label for="value">
		<g:message code="globalNumericSetting.value.label" default="Value" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="value" value="${fieldValue(bean: globalNumericSettingInstance, field: 'value')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: globalNumericSettingInstance, field: 'effectiveDate', 'error')} required">
	<label for="effectiveDate">
		<g:message code="globalNumericSetting.effectiveDate.label" default="Effective Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="effectiveDate" precision="day"  value="${globalNumericSettingInstance?.effectiveDate}"  />
</div>

