<%@ page import="com.charitychamp.Activity" %>



<div class="fieldcontain ${hasErrors(bean: activityInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="activity.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${activityInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: activityInstance, field: 'amountCollected', 'error')} required">
	<label for="amountCollected">
		<g:message code="activity.amountCollected.label" default="Amount Collected" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="text" name="amountCollected" required="" value="${fieldValue(bean: activityInstance, field: 'amountCollected')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: activityInstance, field: 'leaderName', 'error')} ">
	<label for="leaderName">
		<g:message code="activity.leaderName.label" default="Leader Name" />
		
	</label>
	<g:textField name="leaderName" value="${activityInstance?.leaderName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: activityInstance, field: 'comments', 'error')} ">
	<label for="comments">
		<g:message code="activity.comments.label" default="Comments" />
		
	</label>
	<g:textField name="comments" value="${activityInstance?.comments}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: activityInstance, field: 'depositDate', 'error')} required">
	<label for="depositDate">
		<g:message code="activity.depositDate.label" default="Deposit Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="depositDate" precision="day"  value="${activityInstance?.depositDate}"  />
</div>

