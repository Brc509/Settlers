var catan = catan || {};
catan.models = catan.models || {};

catan.models.HexLocation = (function HexLocationNameSpace () {

	var HexLocation = (function () {

		/**
		HexLocation Class 
		<pre>
		</pre>
		@class HexLocation 
		@constructor
		@param {int} x
		@param {int} y
		*/
		function HexLocation(x, y) {
		    this.x = x;
			this.y = y;
		}

		/**
		GetX method
		<pre>
		</pre>
		@method getX
		@return {int} x
		*/
		HexLocation.prototype.getX = function() {
		    return this.x;
		}

		/**
		GetY method
		<pre>
		</pre>
		@method getY
		@return {int} y
		*/
		HexLocation.prototype.getY = function() {
		    return this.y;
		}

		return HexLocation;
	})();


	return HexLocation;
})();