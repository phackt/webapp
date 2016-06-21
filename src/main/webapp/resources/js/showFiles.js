var fileIndex = 1;

$(document)
	.ready(
		function() {
			//add more file components if Add is clicked
			$('#addFile')
				.click(	
					function() {										
						$('#filesTable').append('<tr><td>' +
							'<label for="filesUploaded[' + fileIndex + '].description">Description:</label>' +
							'</td></tr>' +
							'<tr><td>' +
							'<textarea name="filesUploaded[' + fileIndex + '].description" rows="5" cols="30"></textarea>' +
							'</td></tr>' +
							'<tr><td>' +
							'<input type="file" name="filesUploaded[' + fileIndex + '].file" />' +
							'</td></tr>'
							);
						
							fileIndex++; 
						});				
					});