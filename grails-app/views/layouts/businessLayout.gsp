<%@ page import="com.charitychamp.Business" %>
<g:applyLayout name="main">
<html>
	<head>
		
		<g:set var="entityName" value="${message(code: 'business.label', default: 'Business')}" />
		<title>Business Overview</title>
	
	</head>

	<body>
			<div id="businessBreadCrumbs" class="breadCrumbs">
   						<ul>
   							<li><g:img dir="images" file="chart_organisation.png" /></li>
   							<li><g:link class="breadCrumbButton" controller="company" action="overview" id="${companyId}">${companyName}</g:link></li>
   							<li><g:img dir="img" file="resultset_next.png" /></li>
   							<li><g:link class="breadCrumbButton" controller="business" action="overview" id="${businessId}">${businessName}</g:link></li>
   							   							   						   						
   						</ul>
   						
   						
   						
   					</div>
   					
        			<div id="officeOverView" >
						
						
						<div class="overViewTitle">
							<br/>
							<h2>Team: ${businessInstance?.teamNumber} <br/> Business: ${businessInstance.name}</h2> <br/> <h3>${currentCampaign}</h3>
													
						</div>	
						
					</div>
	
			<g:layoutBody/>
	
	</body>
	
</html>
</g:applyLayout>