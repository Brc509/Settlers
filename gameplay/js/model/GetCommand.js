// GetCommand.js
// Author: Spencer Bench

var catan = catan || {};
catan.models = catan.models || {};

catan.models.GetCommand = (function GetCommandNamespace() {

	var GetCommand = (function GetCommandClass(){
	
		// Define API endpoint constants
		GetCommand.prototype.GAME_MODEL_URL	= '/game/model';
		
		/**
			The GetCommand class represents any command which GETs from the server.
			<pre>			
			Constructor Specification:
				PRE: url is a valid server endpoint
			</pre>
			
			@class GetCommand
			@extends Command
			@constructor
			
			@param {string} url The server endpoint to GET
		*/
		function GetCommand(url) {
			// Call the Command constructor
			catan.models.Command.call(this, url, null);
		}
		GetCommand.prototype = Object.create(catan.models.Command.prototype);
		GetCommand.prototype.constructor = GetCommand;
		
		/**
			GETs from the server and executes a callback function.
			
			@method execute
			@param {function} callback The callback function executed when the request succeeds OR fails
		*/
		GetCommand.prototype.execute = function(callback) {
			jQuery.ajax({
				url: this.url
			})
			.done(function(data) {
				callback(false, data);
			})
			.fail(function(jqxhr) {
				callback(true, jqxhr);
			});
		};
		
		return GetCommand;
		
	})();
	
	return GetCommand;
	
})();
