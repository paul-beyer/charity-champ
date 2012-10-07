<%@ page import="com.charitychamp.Group" %>



<div class="fieldcontain ${hasErrors(bean: groupInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="group.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${groupInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: groupInstance, field: 'leader', 'error')} ">
	<label for="leader">
		<g:message code="group.leader.label" default="Leader" />
		
	</label>
	<g:select id="leader" name="leader.id" from="${com.charitychamp.Person.list()}" optionKey="id" value="${groupInstance?.leader?.id}" class="many-to-one" noSelection="['':'-Choose-']"/>
</div>


<div class="fieldcontain ${hasErrors(bean: groupInstance, field: 'department', 'error')} required">
	<label for="department">
		<g:message code="group.department.label" default="Department" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="department" name="department.id" from="${com.charitychamp.Department.list()}" optionKey="id" required="" value="${groupInstance?.department?.id}" noSelection="['':'-Choose-']" class="many-to-one"/>
</div>

