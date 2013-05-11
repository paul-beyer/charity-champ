<%@page import="com.charitychamp.Business" %>
<g:applyLayout name="businessLayout">
<html>
	<head>
		
	</head>
   			<body>
   					<div id="businessHeader">
	   					<div class="groupLeaders">
							<h3>Your Operation Feed Leaders are:</h3> <br/>
							<span class="leader">Leader: </span>${businessInstance?.charityLeader} (${businessInstance?.charityLeader?.userId}) - ${businessInstance?.charityLeader?.email}
							<br/>
									
														
						</div>
						<div class="clear"></div>	
						<div class="variantSummary">
							<g:img dir="images" file="group.png" /> ${businessInstance?.name} has <g:formatNumber number="${employeeCount}" type="number" maxFractionDigits="2" /> employees.<br/><br/>
							<g:img dir="images" file="target.png" /> The goal amount per employee is <span class="goldAmount"><g:formatNumber number="${goalPerEmployee}" type="currency" currencyCode="USD" /></span><br/><br/>
							<g:if test="${moneyVariant > 0}">
     								<g:img dir="images" file="award_star_gold.png" /> Congrats! You've exceeded your goal by <span class="goldAmount"><g:formatNumber number="${moneyVariant}" type="currency" currencyCode="USD" /></span>
							</g:if>
							<g:elseif test="${moneyVariant < 0}">
     								<g:img dir="images" file="exclamation.png" /> You need to raise <span class="redAmount"><g:formatNumber number="${moneyVariant}" type="currency" currencyCode="USD" /></span> to meet your goal.
							</g:elseif>
							<g:elseif test="${moneyVariant == 0}">
     								<g:img dir="images" file="award_star_gold.png" /> Congrats! You've achieved your goal of <span class="goldAmount"><g:formatNumber number="${moneyVariant}" type="currency" currencyCode="USD" />.</span>
							</g:elseif>
							<br/><br/>
							<g:if test="${mealVariant > 0}">
     								<g:img dir="images" file="award_star_gold.png" /> Congrats! You've exceeded meal goal by <span class="goldAmount"><g:formatNumber number="${mealVariant}" type="number" maxFractionDigits="2" /></span> meals.
							</g:if>
							<g:elseif test="${mealVariant < 0}">
     								<g:img dir="images" file="exclamation.png" /> Keep going, you're <span class="redAmount"><g:formatNumber number="${mealVariant}" type="number" maxFractionDigits="2" /></span> meals short.
							</g:elseif>
							<g:elseif test="${mealVariant == 0}">
     								<g:img dir="images" file="award_star_gold.png" /> Congrats! You've achieved your goal of <span class="goldAmount"><g:formatNumber number="${mealVariant}" type="number" maxFractionDigits="2" /></span> meals.
							</g:elseif>
				
						</div>
						<div class="clear"></div>		
   					</div>
					
				<br/>
					<div id="businessContentArea">	
						
						<div class="summaryOverview">
							<table>
								<tbody>
									<tr>
										<td class="summaryTableLabel">
											<span class="summaryLabel">${businessInstance?.name}'s Meal Goal:</span>
										</td>
										<td class="summaryTableValue">
											<span class="summaryValue">${mealGoal}</span>
										</td>
									</tr>
									<tr>
										<td class="summaryTableLabel">
											<span class="summaryLabel">${businessInstance?.name}'s Money Goal:</span>
										</td>
										<td class="summaryTableValue">
											<span class="summaryValue"><g:formatNumber number="${moneyGoal}" type="currency" currencyCode="USD" /></span>
										</td>
									</tr>
									<tr>
										<td class="summaryTableLabel">
											<span class="summaryLabel">Meals earned:</span>
										</td>
										<td class="summaryTableValue">
											<span class="summaryValue">${totalMealsEarned}</span>
										</td>
									</tr>
									<tr>
										<td class="summaryTableLabel">
											<span class="summaryLabel">Money earned:</span>
										</td>
										<td class="summaryTableValue">
											<span class="summaryValue"><g:formatNumber number="${totalMoneyEarned}" type="currency" currencyCode="USD" /></span>
										</td>
									</tr>
									<tr>
										<td class="summaryTableLabel">
											<span class="summaryLabel">Meal Percent:</span>
										</td>
										<td class="summaryTableValue">
											<span class="summaryValue">${percentageMealGoal}%</span>
										</td>
									</tr>
									<tr>
										<td class="summaryTableLabel">
											<span class="summaryLabel">Money Percent:</span>
										</td>
										<td class="summaryTableValue">
											<span class="summaryValue">${percentageMoneyGoal}%</span>
										</td>
									</tr>
								
								</tbody>
							
							</table>
						</div>
					
						<div class="orgSummary">
								
								<table>
									<thead class="overViewTableHeader">
										<tr>
										
											<td>Office</td>
											
											<td>Amount</td>
										
											<td>Number of Meals</td>
										
											
										</tr>
									</thead>
									<tbody>
										<g:each in="${officeTotals}" status="i" var="officeTotal">
											<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
												
												<td><g:link controller="office" action="overview" id="${officeTotal.orgUnitId}">${fieldValue(bean: officeTotal, field: "name")}</g:link></td>
											
												<td><g:formatNumber number="${officeTotal?.amount}" type="currency" currencyCode="USD" /></td>
											
												<td>${fieldValue(bean: officeTotal, field: "mealCount")}</td>
											
												
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
							<div class="clear"></div>				
					</div>
   		</body>
</html>
</g:applyLayout>