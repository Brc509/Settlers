var catan = catan || {};
catan.models = catan.models || {};

catan.models.VertexLocation = (function VertexLocationNameSpace () {

	var VertexLocation = (function VertexLocationClass() {

		/**
		VertexLocation Class 
		<pre>
		direction = NE | N | NW |SW | S | SE
		</pre>
		@class VertexLocation 
		@constructor
		@param {String} direction
		@param {int} x
		@param {int} y
		*/
		function VertexLocation(direction, x, y) {
			this.direction = direction;
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
		VertexLocation.prototype.getX = function() {
		    return this.x;
		}

		/**
		GetY method
		<pre>
		</pre>
		@method getY
		@return {int} y
		*/
		VertexLocation.prototype.getY = function() {
		    return this.y;
		}

		/**
		GetDirection method
		<pre>
		</pre>
		@method getDirection
		@return {String} direction
		*/
		VertexLocation.prototype.getDirection = function() {
		    return this.direction;
		}


		return VertexLocation;
	})();


	return VertexLocation;
})();