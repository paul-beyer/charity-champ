<%@ page import="com.charitychamp.Group" %>
<g:applyLayout name="main">
<html>
	<head>
		
		<g:set var="entityName" value="${message(code: 'company.label', default: 'Group')}" />
		<title>Group Overview</title>
	
	</head>

	<body>
			<div id="groupBreadCrumbs" class="breadCrumbs">
   						<ul>
   							<li><g:img dir="images" file="chart_organisation.png" /></li>
   							<li><g:link class="breadCrumbButton" controller="company" action="show" id="${companyId}">${companyName}</g:link></li>
   							<li><g:img dir="img" file="resultset_next.png" /></li>
   							<li><g:link class="breadCrumbButton" controller="business" action="show" id="${businessId}">${businessName}</g:link></li>
   							<li><g:img dir="img" file="resultset_next.png" /></li>
   							<li><g:link class="breadCrumbButton" controller="office" action="show" id="${officeId}">${officeName}</g:link></li>
   							<li><g:img dir="img" file="resultset_next.png" /></li>
   							<li><g:link class="breadCrumbButton" controller="department" action="overview" id="${departmentId}">${departmentName}</g:link></li>
   							<li><g:img dir="img" file="resultset_next.png" /></li>
   							<li><g:link class="breadCrumbButton" controller="group" action="overview" id="${groupInstance.id}">${groupInstance.name}</g:link></li>
   						
   						</ul>
   						
   						
   						
   					</div>
   					
        			<div id="groupOverView" >
						
						
						<div class="overViewTitle">
							<br/>
							<h2>${groupInstance.name} - ${currentCampaign}</h2>
													
						</div>	
						
					</div>
	
			<g:layoutBody/>
	
	</body>
	
</html>
</g:applyLayout>