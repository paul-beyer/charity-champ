<%@ page import="com.charitychamp.VolunteerShift" %>



<div class="fieldcontain ${hasErrors(bean: volunteerShiftInstance, field: 'numberOfVolunteers', 'error')} required">
	<label for="numberOfVolunteers">
		<g:message code="volunteerShift.numberOfVolunteers.label" default="Number Of Volunteers" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="numberOfVolunteers" type="number" min="1" value="${volunteerShiftInstance.numberOfVolunteers}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: volunteerShiftInstance, field: 'comments', 'error')} ">
	<label for="comments">
		<g:message code="volunteerShift.comments.label" default="Comments" />
		
	</label>
	<g:textField name="comments" value="${volunteerShiftInstance?.comments}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: volunteerShiftInstance, field: 'leader', 'error')} ">
	<label for="leader">
		<g:message code="volunteerShift.leader.label" default="Leader" />
		
	</label>
	<g:select id="leader" name="leader.id" from="${com.charitychamp.Person.list()}" optionKey="id" value="${volunteerShiftInstance?.leader?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: volunteerShiftInstance, field: 'mealFactor', 'error')} required">
	<label for="mealFactor">
		<g:message code="volunteerShift.mealFactor.label" default="Meal Factor" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="mealFactor" value="${fieldValue(bean: volunteerShiftInstance, field: 'mealFactor')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: volunteerShiftInstance, field: 'dateOfShift', 'error')} required">
	<label for="dateOfShift">
		<g:message code="volunteerShift.dateOfShift.label" default="Date Of Shift" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateOfShift" precision="day"  value="${volunteerShiftInstance?.dateOfShift}"  />
</div>

