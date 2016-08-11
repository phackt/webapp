var fileIndex = 1;

//add more file components if Add is clicked
$("#addFile")
	.click(	
		function() {										
			$("#filesTable").append(
					"<tr><td>" +
					"	<div class=\"form-group\">" +
					"		<textarea name=\"filesUploaded[" + fileIndex + "].description\" class=\"form-control\" rows=\"3\" placeholder=\"Description...\"></textarea>" +
					"	</div>" +
					"</td>" +
					"<td>" +
					"	<div class=\"form-group\">" +
					"		<input type=\"file\" name=\"filesUploaded[" + fileIndex + "].file\" >" +
					"		<br/>" +
					"		<input class=\"btn btn-default removeFile\" type=\"button\" value=\"Remove file\">" +
					"	</div> " +
					"</td></tr>"
				);	
			
			fileIndex++;
		});		

//Delegate events - elements not existing when js is loading
$(document)
	.on("click",".removeFile",function() {										
			$(this).closest("tr").remove();
			fileIndex--;
		});				


