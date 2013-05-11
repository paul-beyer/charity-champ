<%@ page import="com.charitychamp.Activity" %>
<g:applyLayout name="groupLayout">
<html>
	<head>
	
		<title>Activities</title>
	</head>
	<body>
		<div id="list-activity" class="content" role="main">
			<span class="detailReportHeading">Activities</span>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
		
			<g:link class="breadCrumbButton manageDonations" controller="group" action="addActivity" id="${groupInstance.id}" >Add New Activity</g:link><br/><br/>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="activity.name.label" default="Name" /></th>	
						
						<th><g:message code="activity.amountCollected.label" default="Amount Collected" /></th>			
											
						<th><g:message code="activity.amountSpent.label" default="Amount Spent" /></th>			
						
						<th><g:message code="activity.leaderName.label" default="In Charge" /></th>			
					
						<th><g:message code="activity.comments.label" default="Comments" /></th>			
					
						<th><g:message code="activity.donation.date.label" default="Deposit Date" /></th>
					
						<th><g:message code="activity.number.of.meals.label" default="Number Of Meals" /></th>
						
										
					</tr>
				</thead>
				<tbody>
				<g:each in="${activityInstanceList}" status="i" var="activityInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="editActivity" id="${activityInstance.id}"  params="[groupId : groupInstance.id]" >${fieldValue(bean: activityInstance, field: "name")}</g:link></td>
						
						<td><g:formatNumber number="${activityInstance?.amountCollected}" type="currency" currencyCode="USD" /></td>
						
						<td><g:formatNumber number="${activityInstance?.amountSpent}" type="currency" currencyCode="USD" /></td>
					
						<td>${fieldValue(bean: activityInstance, field: "leaderName")}</td>
					
						<td>${fieldValue(bean: activityInstance, field: "comments")}</td>
				
						<td><g:formatDate format="yyyy-MM-dd" date="${activityInstance?.donationDate}" /></td>
						
						<td>${fieldValue(bean: activityInstance, field: "numberOfMeals")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			
		</div>
			
	</body>
	
</html>
</g:applyLayout>
