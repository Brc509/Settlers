// MessageLine
// Author: Spencer Bench

MessageLine = (function() {

	/**
		The MessageLine class represents a single message.
		<pre>
		Domain:
			message: The message, string
			source: The name of the player who authored the message, string
			
		Constructor Specification:
			PRE: msg is a non-empty string
			PRE: src is a non-empty string
			POST: this.message = msg
			POST: this.source = src
		</pre>
		
		@class MessageLine
		@constructor
		
		@param {string} msg The message
		@param {string} src The name of the player who authored the message
	*/
	function MessageLine(line) {
		this.message = line.message;
		this.source = line.source;
	}
	MessageLine.prototype = Object.create(Object.prototype);
	MessageLine.prototype.constructor = MessageLine;
	
	/**
		@method getMessage
	*/
	MessageLine.prototype.getMessage = function() {
		return this.message;
	};
	
	/**
		@method getSource
	*/
	MessageLine.prototype.getSource = function() {
		return this.source;
	};
	
	return MessageLine;
	
})();
