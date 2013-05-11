<%@page import="com.charitychamp.Company" %>
<g:applyLayout name="companyLayout">
<html>
	<head>
		
	</head>
   			<body>
   				<div id="switchCompany">
   					<g:form action="load" >
   					<g:select name="companyId"
          					from="${com.charitychamp.Company.list()}"
							optionKey="id" />
						
							<g:submitButton name="switch" class="minorButtons" value="${message(code: 'default.button.switch.label', default: 'Switch Company')}" />
					
					</g:form>
   					</div>
   					<div id="companyHeader">
	   					
						<div class="variantSummary">
							<g:img dir="images" file="group.png" /> ${companyInstance?.name} has <g:formatNumber number="${employeeCount}" type="number" maxFractionDigits="2" /> employees.<br/><br/>
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
											<span class="summaryLabel">${companyInstance?.name}'s Meal Goal:</span>
										</td>
										<td class="summaryTableValue">
											<span class="summaryValue">${mealGoal}</span>
										</td>
									</tr>
									<tr>
										<td class="summaryTableLabel">
											<span class="summaryLabel">${companyInstance?.name}'s Money Goal:</span>
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
										
											<td>Business</td>
											
											<td>Amount</td>
										
											<td>Number of Meals</td>
										
											
										</tr>
									</thead>
									<tbody>
										<g:each in="${businessTotals}" status="i" var="businessTotal">
											<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
												
												<td><g:link controller="business" action="overview" id="${businessTotal.orgUnitId}">${fieldValue(bean: businessTotal, field: "name")}</g:link></td>
											
												<td><g:formatNumber number="${businessTotal?.amount}" type="currency" currencyCode="USD" /></td>
											
												<td>${fieldValue(bean: businessTotal, field: "mealCount")}</td>
											
												
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