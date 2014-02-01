// MessageList
// Author: Spencer Bench

MessageList = (function() {

	/**
		The MessageList class contains a list of player messages.
		<pre>
		Domain:
			length: The number of messages in the list, number
			lines: The array of messages
			
		Invariants:
			INVARIANT: length = the number of elements in lines
			
		Constructor Specification:
			POST: length = 0
			POST: lines is empty
		</pre>
		
		@class MessageList
		@constructor
	*/
	function MessageList() {
		this.length = 0;
		this.lines = {};
	}
	MessageList.prototype = Object.create(Object.prototype);
	MessageList.prototype.constructor = MessageList;
	
	/**
		Adds a message to the list
		<pre>
		PRE: message is a non-empty string
		PRE: source is a non-empty string
		POST: The message is added to the list
		POST: length is one more than it was before
		</pre>
		
		@method add
		@param {string} message The message
		@param {string} source The name of the player who authored the message
	*/
	MessageList.prototype.add = function(message, source) {
		this.lines[length] = new MessageLine(message, source);
		this.length++;
	};
	
	return MessageList;
	
})();
