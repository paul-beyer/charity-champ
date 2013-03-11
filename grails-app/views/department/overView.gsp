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
											
				
   		</body>
</html>
</g:applyLayout>