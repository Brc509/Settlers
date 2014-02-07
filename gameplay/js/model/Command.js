// Command.js
// Author: Spencer Bench

var catan = catan || {};
catan.models = catan.models || {};

catan.models.Command = (function() {

	/**
		The Command class represents any command sent from the client to the server.
		<pre>
		Domain:
			url: The server endpoint that the command is sent to, string
			callback: The callback function executed when the server responds, function
			
		Constructor Specification:
			PRE: url is a valid server endpoint
			PRE: callback is a function
		</pre>
		
		@class Command
		@constructor
		
		@param {string} url The server endpoint that the command is sent to
		@param {function} callback The callback function executed when the server responds
	*/
	function Command(url, callback) {
		this.url = url;
		this.callback = callback;
	}
	Command.prototype.constructor = Command;
	
	return Command;
	
})();
