if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}

$(function () {
	
	$(".money").maskMoney();
	
	
	$("#addActivity").click(function() {
//		  alert("Handler for .click() called.");
		  $("#create-activity-dialog").dialog('open');
		  
		});
	
	 $("#create-activity-dialog").dialog({
		  autoOpen: false,
          width: 550,
          modal: true,
          resizable: true
       
      });
	
	
});

