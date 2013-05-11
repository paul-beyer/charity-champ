<%@ page import="com.charitychamp.JeansPayment" %>
<g:applyLayout name="groupLayout">
<html>
	<head>
	
		<title>Jean Payments</title>
	</head>
	<body>
		
		<div id="list-jeanPayments" class="content" role="main">
			<span class="detailReportHeading">Jean Payments</span>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
		
			<g:link class="breadCrumbButton manageDonations" controller="group" action="addJeanPayment" id="${groupInstance.id}" >Add New Jean Payment</g:link><br/><br/>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="jeansPayment.employeeUserId.label" default="Employee User Id" /></th>
						
						<th><g:message code="jeansPayment.payerFirstName.label" default="Payer First Name" /></th>
					
						<th><g:message code="jeansPayment.payerLastName.label" default="Payer Last Name" /></th>
					
						<th><g:message code="jeansPayment.payerPhone.label" default="Payer Phone" /></th>
					
						<th><g:message code="jeansPayment.payerEmail.label" default="Payer Email" /></th>
					
						<th><g:message code="jeansPayment.amtPaid.label" default="Amt Paid" /></th>
					
						<th><g:message code="activity.donation.date.label" default="Date Paid" /></th>
						
						<th><g:message code="activity.number.of.meals.label" default="Number Of Meals" /></th>
						
									
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${jeanPaymentsList}" status="i" var="jeansPaymentInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="editJeanPayments" id="${jeansPaymentInstance.id}"  params="[groupId : groupInstance.id]" >${fieldValue(bean: jeansPaymentInstance, field: "employeeUserId")}</g:link></td>
											
						<td>${fieldValue(bean: jeansPaymentInstance, field: "payerFirstName")}</td>
					
						<td>${fieldValue(bean: jeansPaymentInstance, field: "payerLastName")}</td>
					
						<td>${fieldValue(bean: jeansPaymentInstance, field: "payerPhone")}</td>
					
						<td>${fieldValue(bean: jeansPaymentInstance, field: "payerEmail")}</td>
						
						<td><g:formatNumber number="${jeansPaymentInstance?.amountCollected}" type="currency" currencyCode="USD" /></td>
												
						<td><g:formatDate format="yyyy-MM-dd" date="${jeansPaymentInstance?.donationDate}" /></td>
						
						<td>${fieldValue(bean: jeansPaymentInstance, field: "numberOfMeals")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			
		</div>
			
	</body>
	
</html>
</g:applyLayout>
