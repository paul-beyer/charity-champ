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
	<g:select id="leader" name="leader.id" from="${com.charitychamp.Person.list()}" optionKey="id" value="${groupInstance?.leader?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: groupInstance, field: 'activities', 'error')} ">
	<label for="activities">
		<g:message code="group.activities.label" default="Activities" />
		
	</label>
	<g:select name="activities" from="${com.charitychamp.Activity.list()}" multiple="multiple" optionKey="id" size="5" value="${groupInstance?.activities*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: groupInstance, field: 'volunteerShifts', 'error')} ">
	<label for="volunteerShifts">
		<g:message code="group.volunteerShifts.label" default="Volunteer Shifts" />
		
	</label>
	<g:select name="volunteerShifts" from="${com.charitychamp.VolunteerShift.list()}" multiple="multiple" optionKey="id" size="5" value="${groupInstance?.volunteerShifts*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: groupInstance, field: 'jeansPayments', 'error')} ">
	<label for="jeansPayments">
		<g:message code="group.jeansPayments.label" default="Jeans Payments" />
		
	</label>
	<g:select name="jeansPayments" from="${com.charitychamp.JeansPayment.list()}" multiple="multiple" optionKey="id" size="5" value="${groupInstance?.jeansPayments*.id}" class="many-to-many"/>
</div>

