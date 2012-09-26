<%@ page import="com.charitychamp.Person" %>



<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'userId', 'error')} required">
	<label for="userId">
		<g:message code="person.userId.label" default="User Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="userId" required="" value="${personInstance?.userId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'firstName', 'error')} required">
	<label for="firstName">
		<g:message code="person.firstName.label" default="First Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="firstName" required="" value="${personInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'lastName', 'error')} required">
	<label for="lastName">
		<g:message code="person.lastName.label" default="Last Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lastName" required="" value="${personInstance?.lastName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'phoneNumber', 'error')} ">
	<label for="phoneNumber">
		<g:message code="person.phoneNumber.label" default="Phone Number" />
		
	</label>
	<g:textField name="phoneNumber" value="${personInstance?.phoneNumber}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'altPhoneNumber', 'error')} ">
	<label for="altPhoneNumber">
		<g:message code="person.altPhoneNumber.label" default="Alt Phone Number" />
		
	</label>
	<g:textField name="altPhoneNumber" value="${personInstance?.altPhoneNumber}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'email', 'error')} ">
	<label for="email">
		<g:message code="person.email.label" default="Email" />
		
	</label>
	<g:field type="email" name="email" value="${personInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="person.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${personInstance?.title}"/>
</div>

