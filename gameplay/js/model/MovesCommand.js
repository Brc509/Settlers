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
	function MovesCommand() {
	}
	MovesCommand.prototype = Object.create(Command.prototype);
	MovesCommand.prototype.constructor = MovesCommand;
	
	/**
		Sends data as JSON to the server and updates the client model on response.
		
		@method execute
	*/
	MovesCommand.prototype.execute = function(callback) {
		var myself = this;
		jQuery.post(this.url, JSON.stringify(this.data), function (data) {
			callback(false, data);
		})
		.fail (function () {
			alert('error');
		});
	};
	
	return MovesCommand;
	
})();
