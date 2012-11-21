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
	<g:field type="number" name="mealFactor" min="1" required="" value="${fieldValue(bean: volunteerShiftInstance, field: 'mealFactor')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: volunteerShiftInstance, field: 'campaign', 'error')} required">
	<label for="campaign">
		<g:message code="volunteerShift.campaign.label" default="Campaign" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="campaign" name="campaign.id" from="${com.charitychamp.Campaign.list()}" optionKey="id" required="" value="${volunteerShiftInstance?.campaign?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: volunteerShiftInstance, field: 'dateOfShift', 'error')} required">
	<label for="dateOfShift">
		<g:message code="volunteerShift.dateOfShift.label" default="Date Of Shift" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateOfShift" precision="day"  value="${volunteerShiftInstance?.dateOfShift}"  />
</div>

