// Token
// Author: Spencer Bench

Token = (function() {

	/**
		The Token class represents a numbered token.
		<pre>
		Domain:
			location: The location of the token on the game board, HexLocation
			
		Constructor Specification:
			PRE: loc is a HexLocation object
			POST: this.location = loc
		</pre>
		
		@class Token
		@constructor
		
		@param {HexLocation} loc The location of the token on the game board
	*/
	function Token(loc) {
		this.location = loc;
	}
	Token.prototype = Object.create(Object.prototype);
	Token.prototype.constructor = Token;
	
	/**
		@method getLocation
	*/
	Token.prototype.getLocation = function() {
		return this.location;
	};
	
	return Token;
	
})();
