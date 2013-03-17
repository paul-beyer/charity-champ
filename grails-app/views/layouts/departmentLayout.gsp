<%@ page import="com.charitychamp.Group" %>
<g:applyLayout name="main">
<html>
	<head>
		
		<g:set var="entityName" value="${message(code: 'company.label', default: 'Group')}" />
		<title>Department Overview</title>
	
	</head>

	<body>
			<div id="departmentBreadCrumbs" class="breadCrumbs">
   						<ul>
   							<li><g:img dir="images" file="chart_organisation.png" /></li>
   							<li><g:link class="breadCrumbButton" controller="company" action="overview" id="${companyId}">${companyName}</g:link></li>
   							<li><g:img dir="img" file="resultset_next.png" /></li>
   							<li><g:link class="breadCrumbButton" controller="business" action="overview" id="${businessId}">${businessName}</g:link></li>
   							<li><g:img dir="img" file="resultset_next.png" /></li>
   							<li><g:link class="breadCrumbButton" controller="office" action="overview" id="${officeId}">${officeName}</g:link></li>
   							<li><g:img dir="img" file="resultset_next.png" /></li>
   							<li><g:link class="breadCrumbButton" controller="department" action="overview" id="${departmentId}">${departmentName}</g:link></li>
   						   						
   						</ul>
   						
   						
   						
   					</div>
   					
        			<div id="departmentOverView" >
						
						
						<div class="overViewTitle">
							<br/>
							<h2>Team: ${departmentInstance?.office?.business?.teamNumber} <br/>Department: ${departmentInstance.name}</h2> <br/> <h3>${currentCampaign}</h3>
													
						</div>	
						
					</div>
	
			<g:layoutBody/>
	
	</body>
	
</html>
</g:applyLayout>