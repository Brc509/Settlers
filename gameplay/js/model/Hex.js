/**
Hex Class 
<pre>
</pre>
@class Hex 
@constructor
@param {boolean} isLand
@param {HexLocation} location
@param {Edge[]} edges
@param {Vertex[]} vertexes
*/
function Hex(isLand, location) {
	this.isLand = isLand;
	this.location = location;
}

/**
GetEdges method
<pre>
</pre>
@method getEdges
@return {Edge[]} edges
*/
Hex.prototype.getEdges = function() {
    return this.edges;
}

/**
GetLocation method
<pre>
</pre>
@method getLocation
@return {HexLocation} location
*/
Hex.prototype.getLocation = function() {
    return this.location;
}

/**
GetVertexes method
<pre>
</pre>
@method getVertexes
@return {Vertex[]} vertexes
*/
Hex.prototype.getVertexes = function() {
    return this.vertexes;
}

/**
IsLand method
<pre>
</pre>
@method isLand
@return {boolean} isLand
*/
Hex.prototype.isLand = function() {
    return this.isLand;
}

/**
SetEdge method
<pre>
</pre>
@method setEdge
@param {Edge} edge
*/
Hex.prototype.setEdge = function(edge) {
    this.edges.push(edge);
}

/**
SetVertex method
<pre>
</pre>
@method setVertex
@param {Vertex} vertex
*/
Hex.prototype.setVertex = function(vertex) {
    this.vertexes.push(vertex);
}