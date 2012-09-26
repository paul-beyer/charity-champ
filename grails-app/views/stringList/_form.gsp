<%@ page import="com.charitychamp.StringList" %>



<div class="fieldcontain ${hasErrors(bean: stringListInstance, field: 'listName', 'error')} required">
	<label for="listName">
		<g:message code="stringList.listName.label" default="List Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="listName" required="" value="${stringListInstance?.listName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: stringListInstance, field: 'value', 'error')} required">
	<label for="value">
		<g:message code="stringList.value.label" default="Value" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="value" required="" value="${stringListInstance?.value}"/>
</div>

