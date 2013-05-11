<%@ page import="com.charitychamp.Group" %>
<g:applyLayout name="groupLayout">
<html>
	<head>
		
	</head>
   			<body>
   					
					<div>
							<div>
								<span class="detailReportHeading">Detail Report</span>
							</div><br/>
							<div>
								<table id="reportTable" class="tableData">
									<thead>
										<tr>
											<th id="activityDate">Date</th>
											<th id="activityName">Activity</th>
											<th id="activityTotalIncome">Income</th>
											<th id="activityTotalExpenses">Expense</th>
											<th id="activityProfit">Profit</th>
										</tr>
									</thead>
									<tbody>
										<g:each var="activity" in="${activityList}">
											<tr>
												<td><g:formatDate format="yyyy-MM-dd" date="${activity.donationDate}" /></td>
												<td><g:fieldValue bean="${activity}" field="name"/></td>
												<td><g:formatNumber number="${activity.amountCollected}" type="currency" currencyCode="USD" /></td>
												<td><g:formatNumber number="${activity.amountSpent}" type="currency" currencyCode="USD" /></td>
												<td><g:formatNumber number="${activity.profit}" type="currency" currencyCode="USD" /></td>
																		
											</tr>
										</g:each>
										<g:each var="jeansPayment" in="${jeanPaymentsList}">
											<tr>
												<td><g:formatDate format="yyyy-MM-dd" date="${jeansPayment.donationDate}" /></td>
												<td>Dress Down</td>
												<td><g:formatNumber number="${jeansPayment.amountCollected}" type="currency" currencyCode="USD" /></td>
												<td></td>
												<td><g:formatNumber number="${jeansPayment.profit	}" type="currency" currencyCode="USD" /></td>
												
											</tr>
										</g:each>
										<tr>
											<th>Totals:</th>
											<th></th>
											<th id="activityTotalIncome"><g:formatNumber number="${totalIncome}" type="currency" currencyCode="USD" /></th>
											<th id="activityTotalExpenses"><g:formatNumber number="${totalExpense}" type="currency" currencyCode="USD" /></th>
											<th id="activityProfit"><g:formatNumber number="${totalProfit}" type="currency" currencyCode="USD" /></th>
										</tr>
									</tbody>
								</table>
								
							
							</div>
							<div class="clear"></div>
						</div>	
											
					<br/>
					
				
   		</body>
</html>
</g:applyLayout>