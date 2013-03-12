<%@page import="com.charitychamp.ActivitySummary"%>
<%@page import="com.charitychamp.Department" %>
<g:applyLayout name="departmentLayout">
<html>
	<head>
		
	</head>
   			<body>
   					
						<div class="groupLeaders">
							<h3>Your Operation Feed Leaders are:</h3> <br/>
							<span class="leader">Leader - </span>${departmentInstance?.office?.business?.charityLeader} (${departmentInstance?.office?.business?.charityLeader?.userId}) - ${departmentInstance?.office?.business?.charityLeader?.email}
							<br/>
									
							<span class="leader">Captain - </span> ${departmentInstance?.office?.charityCaptain} (${departmentInstance?.office?.charityCaptain?.userId}) - ${departmentInstance?.office?.charityCaptain?.email}
							<br/>
							
							<span class="leader">Lieutenant - </span>${departmentInstance?.charityLieutenant} (${departmentInstance?.charityLieutenant?.userId}) - ${departmentInstance?.charityLieutenant?.email}
						
						</div>
						
						
					
					
					<br/>
					<div id="overViewCongrats">
						<span class="congratsText">${totalMealsEarned} MEALS!</span>
					</div>
					Total Money = ${totalMoneyEarned}
					<div>Total Meals ${totalMealsEarned }</div>
					Team Number = ${departmentInstance?.office?.business?.teamNumber}
					<div>Money Goal = ${moneyGoal}
					</div>
					<div>Meal Goal = ${mealGoal}</div>
					<div>Money Percentage = ${percentageMoneyGoal}</div>
					<div>Meal Percentage = ${percentageMealGoal }</div>
					<div id="groupSummary">
								<h3>Summary</h3>
								<table>
									<thead class="overViewTableHeader">
										<tr>
										
											<td>Group</td>
											
											<td>Amount</td>
										
											<td>Number of Meals</td>
										
											
										</tr>
									</thead>
									<tbody>
										<g:each in="${groupTotals}" status="i" var="groupTotal">
											<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
												
												<td><g:link controller="group" action="overview" id="${groupTotal.orgUnitId}">${fieldValue(bean: groupTotal, field: "name")}</g:link></td>
											
												<td><g:formatNumber number="${groupTotal?.amount}" type="currency" currencyCode="USD" /></td>
											
												<td>${fieldValue(bean: groupTotal, field: "mealCount")}</td>
											
												
											</tr>
										</g:each>
										<tr class="overviewTotals" >
											<td>Total</td>
											<td><g:formatNumber number="${totalMoneyEarned}" type="currency" currencyCode="USD" /></td>
											<td>${totalMealsEarned}</td>
										</tr>
									</tbody>
								
								</table>
								
							
							</div>
											
					
   		</body>
</html>
</g:applyLayout>