<%@ page import="com.charitychamp.Activity" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'activity.label', default: 'Activity')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
	<!-- BEGIN CONFIGURATION LINKS -->	
			<div class="manageChampLayout">	
				
				<div class="manageChampRightPanel" >
					<div id="adminInstruction" class="instruction">
						<h2>
							Changing campaign changes it for all views.
						</h2>
						<br/>
						<g:form action="saveChangeCampaign" >
							<fieldset class="form">
								<g:select id="adminChangeCampaign" name="overrideCampaign.id" from="${com.charitychamp.Campaign.list()}" optionKey="id" required="" value="${currentCampaignId}" class="many-to-one"/>
							</fieldset>
							<fieldset class="buttons">
								<g:submitButton name="change" class="save" value="${message(code: 'default.button.set	.label', default: 'Set')}" />
							</fieldset>
						</g:form>
						
											
					</div>
							
				</div>
			</div>
<!-- END CONFIGURATION LINKS -->	
</body>
</html>

	