<%@page import="com.charitychamp.ActivitySummary"%>
<%@ page import="com.charitychamp.Group" %>
<g:applyLayout name="groupLayout">
<html>
	<head>
		
	</head>
   			<body>
   					
						<div class="groupLevelOpFeedLeaders">
							<h3>Your Operation Feed Leaders are:</h3> <br/>
							<span class="leader">Leader - </span>${groupInstance.department?.office?.business?.charityLeader} (${groupInstance.department?.office?.business?.charityLeader?.userId}) - ${groupInstance.department?.office?.business?.charityLeader?.email}
							<br/>
									
							<span class="leader">Captain - </span> ${groupInstance.department?.office?.charityCaptain} (${groupInstance.department?.office?.charityCaptain?.userId}) - ${groupInstance.department?.office?.charityCaptain?.email}
							<br/>
							
							<span class="leader">Lieutenant - </span>${groupInstance.department?.charityLieutenant} (${groupInstance.department?.charityLieutenant?.userId}) - ${groupInstance.department?.charityLieutenant?.email}
						
						</div>
						
						<div id="groupContentArea">
							<div id="overViewCongrats">
								<span class="congratsText">${totalMeals} MEALS!</span>
							</div>
							<div id="groupActivityList">
								<h3>Summary</h3>
								<table>
									<thead class="overViewTableHeader">
										<tr>
										
											<td>Activity</td>
											
											<td>Amount</td>
										
											<td>Number of Meals</td>
										
											
										</tr>
									</thead>
									<tbody>
										<g:each in="${activitySummaries}" status="i" var="activitySummary">
											<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
												
												<td>${fieldValue(bean: activitySummary, field: "name")}</td>
											
												<td><g:formatNumber number="${activitySummary?.amount}" type="currency" currencyCode="USD" /></td>
											
												<td>${fieldValue(bean: activitySummary, field: "mealCount")}</td>
											
												
											</tr>
										</g:each>
										<tr class="overviewTotals" >
											<td>Total</td>
											<td><g:formatNumber number="${totalMoney}" type="currency" currencyCode="USD" /></td>
											<td>${totalMeals}</td>
										</tr>
									</tbody>
								
								</table>
								
							
							</div>
							
							
							<div class="clear"></div>
						</div>	
					
					
					<br/>
					<div id="groupOverViewActions" class="opFeedManagement">
						<g:link class="linkButton" controller="group" action="activities" id="${groupInstance.id}"  params="[campaignId : groupInstanceId]" >Activities</g:link>
						<g:link class="linkButton" controller="group" action="foodBankShifts" id="${groupInstance.id}"  params="[campaignId : groupInstanceId]" >Food Bank Shifts</g:link>
						<g:link class="linkButton" controller="group" action="jeanPayments" id="${groupInstance.id}"  params="[campaignId : groupInstanceId]" >Jeans</g:link>
					</div>
						
				
   		</body>
</html>
</g:applyLayout>