$(function () {
	
	
	d = new dTree('d');
	d1 = new dTree('d');
	d.add(0,-1,'Organizational Structure',  '/charity-champ');
	
	$.ajax({
		url: "/charity-champ/organizationalTree",
		type: "GET",
		cache: false,
		success: function(result) {
				
			$.each(result, function(val, option) {
				var id = option.id;
				var pid = option.pid;
				var name = option.name;
				var link = option.link;
				d.add(id, pid, name, link);
			});
			
			document.getElementById('leftNavPane').innerHTML = d;
				
			d.openAll();
		
		
		},
		statusCode: {
			401: function() {					
				alert("401 error occurred.")
			}
		}
			
	});	
	

	
});

