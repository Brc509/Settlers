// TokenList
// Author: Spencer Bench

TokenList = (function() {

	/**
		The TokenList class represents all the numbered tokens on the game board.
		<pre>
		Constructor Specification:
			PRE: t2 and t12 contain one Token element
			Pre: t3 - t11 contain two Token elements
			POST: this['x'] = tx
		</pre>
		
		@class TokenList
		@constructor
		
		@param {Token[]} t2 The token of value 2
		@param {Token[]} t3 The tokens of value 3
		@param {Token[]} t4 The tokens of value 4
		@param {Token[]} t5 The tokens of value 5
		@param {Token[]} t6 The tokens of value 6
		@param {Token[]} t8 The tokens of value 8
		@param {Token[]} t9 The tokens of value 9
		@param {Token[]} t10 The tokens of value 10
		@param {Token[]} t11 The tokens of value 11
		@param {Token[]} t12 The token of value 12
	*/
	function TokenList(t2, t3, t4, t5, t6, t8, t9, t10, t11, t12) {
		this['2'] = t2;
		this['3'] = t3;
		this['4'] = t4;
		this['5'] = t5;
		this['6'] = t6;
		this['8'] = t7;
		this['9'] = t8;
		this['10'] = t10;
		this['11'] = t11;
		this['12'] = t12;
	}
	TokenList.prototype = Object.create(Object.prototype);
	TokenList.prototype.constructor = TokenList;
	
	/**
		Retrieves the Token objects of a specific value
		<pre>
		PRE: value is an integer in the range [2, 12]
		</pre>
		
		@method getTokens
		@param {number} value The value of the tokens to retrieve
		@return {Token[]} The tokens of the specified value
	*/
	TokenList.prototype.getTokens = function(value) {
		return this[value];
	};
	
	return TokenList;
	
})();
