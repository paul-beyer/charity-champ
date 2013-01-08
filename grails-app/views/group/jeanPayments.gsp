<%@ page import="com.charitychamp.JeansPayment" %>
<g:applyLayout name="groupLayout">
<html>
	<head>
	
		<title>Jean Payments</title>
	</head>
	<body>
		
		<div id="list-jeanPayments" class="content" role="main">
			<h2>Jean Payments</h2><br/>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
		
			<g:link class="breadCrumbButton" controller="group" action="addJeanPayment" id="${groupInstance.id}" >Add New Jean Payment</g:link><br/><br/>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="employeeUserId" title="${message(code: 'jeansPayment.employeeUserId.label', default: 'Employee User Id')}" />
					
						<g:sortableColumn property="payerFirstName" title="${message(code: 'jeansPayment.payerFirstName.label', default: 'Payer First Name')}" />
					
						<g:sortableColumn property="payerLastName" title="${message(code: 'jeansPayment.payerLastName.label', default: 'Payer Last Name')}" />
					
						<g:sortableColumn property="payerPhone" title="${message(code: 'jeansPayment.payerPhone.label', default: 'Payer Phone')}" />
					
						<g:sortableColumn property="payerEmail" title="${message(code: 'jeansPayment.payerEmail.label', default: 'Payer Email')}" />
					
						<g:sortableColumn property="amtPaid" title="${message(code: 'jeansPayment.amtPaid.label', default: 'Amt Paid')}" />
						
						<g:sortableColumn property="donationDate" title="${message(code: 'activity.donation.date.label', default: 'Date Paid')}" />
					
						
					
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
					
						<td>${fieldValue(bean: jeansPaymentInstance, field: "amtPaid")}</td>
						
						<td><g:formatDate format="yyyy-MM-dd" date="${jeansPaymentInstance?.donationDate}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${jeanPaymentsListTotal}" />
			</div>
		</div>
			
	</body>
	
</html>
</g:applyLayout>
