/**
Edge Class 
<pre>
direction = NE | N | NW |SW | S | SE
</pre>
@class Edge 
@constructor
@param {String} direction
@param {Road} road
*/
function Edge(direction, road) {
	this.direction = direction;
	this.road = road;	
}

/**
GetDirection method
<pre>
</pre>
@method getDirection
@return {String} direction
*/
Edge.prototype.getDirection = function() {
    return this.direction;
}

/**
GetRoad method
<pre>
</pre>
@method getRoad
@return {Road} road
*/
Edge.prototype.getRoad = function() {
    return this.road;
}

/**
SetRoad method
<pre>
</pre>
@method setRoad
@param {Road} road
*/
Edge.prototype.setRoad = function(road) {
    this.road = road;
}