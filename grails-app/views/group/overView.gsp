<%@ page import="com.charitychamp.Group" %>
<!doctype html>
<html>
	<head>
	  	<title><g:message code="title.homepage"/></title>
		<meta name="layout" content="main">
		
	</head>
   			<body>
   					<div id="breadCrumbs">
   						<ul>
   							<li><g:img dir="images" file="chart_organisation.png" /></li>
   							<li><g:link controller="company" action="show" id="${companyId}">${companyName}</g:link></li>
   							<li><g:img dir="img" file="resultset_next.png" /></li>
   							<li><g:link controller="business" action="show" id="${businessId}">${businessName}</g:link></li>
   							<li><g:img dir="img" file="resultset_next.png" /></li>
   							<li><g:link controller="office" action="show" id="${officeId}">${officeName}</g:link></li>
   							<li><g:img dir="img" file="resultset_next.png" /></li>
   							<li><g:link controller="department" action="show" id="${departmentId}">${departmentName}</g:link></li>
   						
   						</ul>
   						
   						
   						
   					</div>
        			<div id="groupOverView" >
						
						<g:if test="${flash.message}">
							<div class="message" role="status">${flash.message}</div>
						</g:if>
						<g:hasErrors bean="${groupInstance}">
						<ul class="errors" role="alert">
							<g:eachError bean="${groupInstance}" var="error">
								<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
							</g:eachError>
						</ul>
						</g:hasErrors>
						<div class="overViewTitle">
							<h1>${groupInstance.name}</h1>
							<h2>${currentCampaign}</h2>
						</div>					
					</div>
   		</body>
</html>