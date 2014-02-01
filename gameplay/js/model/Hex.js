/**
The Hex Class 
<pre>
Domain:
            isLand: unique id number of the owner
            location: the value of the vertex
			edges: All the edges next to Hex.
			vertexes: All the vertexes next to Hex.
            
        Invariants:
            INVARIANT: Hex is valid
			INVARIANT: contains 6 edges and 6 vertexes
         
        Constructor Specification:
            PRE: Hex is valid
            PRE: contains 6 edges and 6 vertexes
            POST: isLand() == isLand
            POST: getLocation() == location
			POST: getEdges() == edges
			POST: getVertexes() == vertexes
</pre>
@class Hex 
@constructor
@param {:isLand} (boolean)Whether Hex is on land.
@param {:location} (HexLocation)The location object of Hex.
@param {:edges} (array<Edge>)All the edges next to Hex.
@param {:vertexes} (array<Vertex>)All the vertexes next to Hex.
*/
function Hex(isLand, location, edges, vertexes) {
    this.isLand = isLand;
    this.location = location;
	this.edges = edges;
	this.vertexes = vertexes;
}

/**
<pre>
None
</pre>
@method isLand
@return {boolean} Whether Hex is land or not.
*/
Hex.prototype.isLand = function() {
    return this.isLand;
}

/**
<pre>
location must be valid HexLocation
</pre>
@method getLocation
@return {HexLocation} The HexLocation object of Hex.
*/
Hex.prototype.getLocation = function() {
    return this.location;
}

/**
<pre>
edges must be located on Hex
</pre>
@method getEdges
@return {array<Edge>} All the edges next to Hex.
*/
Hex.prototype.getEdges = function() {
    return this.edges;
}

/**
<pre>
vertexes must be located on Hex
</pre>
@method getVertexes
@return {array<Vertex>} All the vertexes next to Hex.
*/
Hex.prototype.getVertexes = function() {
    return this.vertexes;
}