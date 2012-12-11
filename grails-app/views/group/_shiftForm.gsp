<%@ page import="com.charitychamp.VolunteerShift" %>



<div class="fieldcontain ${hasErrors(bean: volunteerShiftInstance, field: 'numberOfVolunteers', 'error')} required">
	<label for="numberOfVolunteers">
		<g:message code="volunteerShift.numberOfVolunteers.label" default="Number Of Volunteers" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="numberOfVolunteers" min="1" required="" value="${fieldValue(bean: volunteerShiftInstance, field: 'numberOfVolunteers')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: volunteerShiftInstance, field: 'comments', 'error')} ">
	<label for="comments">
		<g:message code="volunteerShift.comments.label" default="Comments" />
		
	</label>
	<g:textField name="comments" value="${volunteerShiftInstance?.comments}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: volunteerShiftInstance, field: 'leaderName', 'error')} ">
	<label for="leaderName">
		<g:message code="volunteerShift.leaderName.label" default="Leader Name" />
		
	</label>
	<g:textField name="leaderName" value="${volunteerShiftInstance?.leaderName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: volunteerShiftInstance, field: 'mealFactor', 'error')} required">
	<label for="mealFactor">
		<g:message code="volunteerShift.mealFactor.label" default="Meal Factor" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="mealFactor" name="mealFactor" from="${mealFactorList}" optionKey="id" value="${volunteerShiftInstance?.mealFactor?.id}" class="many-to-one" noSelection="['null': '- select -']"/>
	
</div>

<div class="fieldcontain ${hasErrors(bean: volunteerShiftInstance, field: 'dateOfShift', 'error')} required">
	<label for="dateOfShift">
		<g:message code="volunteerShift.dateOfShift.label" default="Date Of Shift" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateOfShift" precision="day"  value="${volunteerShiftInstance?.dateOfShift}"  />
</div>

