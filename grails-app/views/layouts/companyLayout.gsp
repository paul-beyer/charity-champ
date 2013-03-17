<%@ page import="com.charitychamp.Company" %>
<g:applyLayout name="main">
<html>
	<head>
		
		<g:set var="entityName" value="${message(code: 'company.label', default: 'Company')}" />
		<title>Company Overview</title>
	
	</head>

	<body>
			  					
        			<div id="companyOverView" >
						
						
						<div class="overViewTitle">
							<br/>
							<h2>${companyInstance.name}</h2> <br/> <h3>${currentCampaign}</h3>
													
						</div>	
						
					</div>
	
			<g:layoutBody/>
	
	</body>
	
</html>
</g:applyLayout>