// Command.js
// Author: Spencer Bench

var catan = catan || {};
catan.models = catan.models || {};

catan.models.Command = (function CommandNamespace() {

	var Command = (function CommandClass(){

		/**
			The Command class represents any command sent from the client to the server.
			<pre>
			Domain:
				url: The server endpoint that the command is sent to, string
				callback: The callback function executed when the request succeeds OR fails, function
				
			Constructor Specification:
				PRE: url is a valid server endpoint
			</pre>
			
			@class Command
			@constructor
			
			@param {string} url The server endpoint that the command is sent to
			@param {function} callback The callback function executed when the request succeeds OR fails
		*/
		function Command(url, callback) {
			this.url = url;
			this.callback = callback;
		}
		Command.prototype.constructor = Command;
		
		return Command;
		
	})();

	return Command;
	
})();
