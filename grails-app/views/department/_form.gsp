<%@ page import="com.charitychamp.Department" %>



<div class="fieldcontain ${hasErrors(bean: departmentInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="department.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${departmentInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: departmentInstance, field: 'numberOfEmployees', 'error')} required">
	<label for="numberOfEmployees">
		<g:message code="department.numberOfEmployees.label" default="Number Of Employees" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="numberOfEmployees" required="" value="${fieldValue(bean: departmentInstance, field: 'numberOfEmployees')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: departmentInstance, field: 'dateOfEmployeeCount', 'error')} ">
	<label for="dateOfEmployeeCount">
		<g:message code="department.dateOfEmployeeCount.label" default="Date Of Employee Count" />
		
	</label>
	<g:datePicker name="dateOfEmployeeCount" precision="day"  value="${departmentInstance?.dateOfEmployeeCount}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: departmentInstance, field: 'charityLieutenant', 'error')} required">
	<label for="charityLieutenant">
		<g:message code="department.charityLieutenant.label" default="Charity Lieutenant" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="charityLieutenant" name="charityLieutenant.id" from="${com.charitychamp.Person.list()}" optionKey="id" required="" value="${departmentInstance?.charityLieutenant?.id}" noSelection="['':'-Choose-']" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: departmentInstance, field: 'leader', 'error')} required">
	<label for="departmentHead">
		<g:message code="department.departmentHead.label" default="Department Head" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="departmentHead" name="leader.id" from="${com.charitychamp.Person.list()}" optionKey="id" required="" value="${departmentInstance?.leader?.id}" noSelection="['':'-Choose-']" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: departmentInstance, field: 'office', 'error')} required">
	<label for="office">
		<g:message code="department.office.label" default="Office" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="office" name="office.id" from="${com.charitychamp.Office.list()}" optionKey="id" required="" value="${departmentInstance?.office?.id}"  noSelection="['':'-Choose-']" class="many-to-one"/>
</div>

