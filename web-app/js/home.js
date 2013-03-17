$(function () {
	
//	d = new dTree('d');
//	d1 = new dTree('d');
//	d.add(0,-1,'Organizational Structure',  '/charity-champ');
//	
//	$.ajax({
//		url: "/charity-champ/organizationalTree",
//		type: "GET",
//		cache: false,
//		success: function(result) {
//				
//			$.each(result, function(val, option) {
//				var id = option.id;
//				var pid = option.pid;
//				var name = option.name;
//				var link = option.link;
//				d.add(id, pid, name, link);
//			});
//			
//			document.getElementById('leftNavPane').innerHTML = d;
//				
//			d.openAll();
//		
//		
//		},
//		statusCode: {
//			401: function() {					
//				alert("401 error occurred.")
//			}
//		}
//			
//	});	
	
	$.ajax({
		url: "/charity-champ/organizationalTree",
		type: "GET",
		cache: false,
		success: function(result) {
					
			var items = '';
			items += '<li><a href="#">Org</a>';
			if(result.companies.length > 0){
				items += '<ul>'; // companies
				$.each(result.companies, function(val, option) {
				
					items += '<li><a href="' + option.url + '">' + option.name + '</a>';
					if(option.businesses.length > 0){
						items += '<ul>'; //start businesses
						
						$.each(option.businesses, function(val, option){
							items += '<li><a href="' + option.url + '">' + option.name + '</a>'; //append businesses
							if(option.offices.length > 0){
								items += '<ul>'; // start offices
								
								$.each(option.offices, function(val, option){
									items += '<li><a href="' + option.url + '">' + option.name + '</a>'; // append offices
									if(option.departments.length > 0){
										items += '<ul>'; //departments
										
										$.each(option.departments, function(val, option){
											items += '<li><a href="' + option.url + '">' + option.name + '</a>'; // append departments
											
											if(option.groups.length > 0){
												items += '<ul>';//groups
												$.each(option.groups, function(val, option){
													items += '<li><a href="' + option.url + '">' + option.name + '</a></li>'; // append groups
												});
												items += '</ul>'; //groups
												items += '</li>'; //department
											}else{
												items += '</li>';
											}
											
										});
										items += '</ul>'
										items += '</li>'
									}else{
										items += '</li>';
										
									}
								});
								items += '</ul>';
								items += '</li>';
							}else{
								items += '</li>';
							}
							
						});
						items += '</ul>';
						items += '</li>';
					}else{
						
						items += '</li>';
					}
					
					
					
					
				});
				items += '</ul>';
				items += '</li>';
				
			} else {
				items += '</li>'; // close the main Business unit if no companies
			}
			
			items += '<li><a href=' + '"/charity-champ/admin/admin">Manage</a></li>';
		
			 $('#mainNav').append( items);
			 $(".jq-menu").jqsimplemenu();
				
		},
		
		statusCode: {
			401: function() {					
				alert("401 error occurred.");
			},
			500: function(val, option) {					
				var items = '';
				items += '<li><a href=' + '"/charity-champ/admin/admin">Manage</a></li>';
				
				 $('#mainNav').append( items);
			}
		}
		
	});	
	
	
	
});

