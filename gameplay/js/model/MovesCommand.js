// MovesCommand.js
// Author: Spencer Bench

var catan = catan || {};
catan.models = catan.models || {};

catan.models.MovesCommand = (function() {

	var Command = catan.models.Command;

	MovesCommand.prototype.MODEL_UPDATE_FUNCTION	= catan.models.ClientModel.prototype.update;
	
	/**
		The MovesCommand class represents any command which modifies the state of the game and returns the game model.
		<pre>
		Domain:
			data: The object to send in the body of the request, object
			
		Constructor Specification:
			PRE: url is a valid server endpoint
		</pre>
		
		@class MovesCommand
		@extends Command
		@constructor
		
		@param {string} url The server endpoint that the command is sent to
		@param {object} data The data to send to the server
	*/
	function MovesCommand(clientModel) {
		// Call the Command constructor
		this.clientModel = clientModel;
		//Command.call(this, url, MODEL_UPDATE_FUNCTION);
		//this.data = data;
	}
	MovesCommand.prototype = Object.create(Command.prototype);
	MovesCommand.prototype.constructor = MovesCommand;
	
	/**
		Sends data as JSON to the server and updates the client model on response.
		
		@method execute
	*/
	MovesCommand.prototype.execute = function() {
		var myself = this;
		JQuery.post(this.url, this.data, function (data) {
			myself.clientModel.prototype.updateModel(false, data);
		})
		.fail (function () {
			alert('error');
		});
		// jQuery.post({
		// 	type: 'POST', this.url,
		// 	data: JSON.stringify(this.data)
		// })
		// .done(function(data) {
			
		// })
		// .fail(function(jqxhr) {
		// 	myself.clientModel.prototype.updateModel(true, jqxhr);
		// });
	};
	
	return MovesCommand;
	
})();
