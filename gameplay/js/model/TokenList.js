var catan = catan || {};
catan.models = catan.models || {};

catan.models.TokenList = (function TokenListNameSpace () {

	var TokenList = (function () {


		/**
		TokenList Class 
		<pre>
		Token values = 2 | 3 | 4 | 5 | 6 | 8 | 9 | 10 | 11 | 12
		</pre>
		@class TokenList 
		@constructor
		@param {Array<Token[]>} tokenList
		*/
		function TokenList() {
			this.tokenList = { 
							  2 : new Array(),
							  3 : new Array(),
							  4 : new Array(),
							  5 : new Array(),
							  6 : new Array(),
							  8 : new Array(),
							  9 : new Array(),
							  10 : new Array(),
							  11 : new Array(),
							  12 : new Array(),
							}
		}

		/**
		GetTokens method
		<pre>
		</pre>
		@method getTokens
		@return {int} index
		*/
		TokenList.prototype.getTokens = function(index) {
			return this.tokenList[index];
		}

		/**
		SetToken method
		<pre>
		</pre>
		@method setToken
		@param {int} index
		@param {Token} token
		*/
		TokenList.prototype.setToken = function(index, token) {
			this.tokenList[index].push(token);
		}
		

		return TokenList;
	})();


	return TokenList;
})();