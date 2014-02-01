/**
The Hex Grid Class 
<pre>
Domain:
            hexes: 2D array of Hexes
            offsets: array of offset values
			radius: radius of Hex Grid
			xO: x origin coordinate
			yO: y origin coordinate
            
        Invariants:
			INVARIANT: contains 19 Hexes
         
        Constructor Specification:
            PRE: Hexes are valid
            PRE: contains 19 Hexes
            POST: isLand() == isLand
            POST: getLocation() == location
			POST: getEdges() == edges
			POST: getVertexes() == vertexes
</pre>
@class Hex 
@constructor
@param {:hexes} (array<Hex>[][])2D array of Hex.
@param {:offsets} (array<int>[])Array of int offset values.
@param {:radius} (int)radius value.
@param {:xO} (int)x origin coordinate.
@param {:yO} (int)y origin coordinate.
*/
function HexGrid(hexes, offsets, radius, xO, yO) {
    this.hexes = hexes;
    this.offsets = offsets;
	this.radius = radius;
	this.xO = xO;
	this.yO = yO;
}

/**
<pre>
None
</pre>
@method getHexes
@return {array<Hex>[][]} 2D Hex array.
*/
HexGrid.prototype.getHexes = function() {
    return this.hexes;
}

/**
<pre>
None
</pre>
@method getOffsets
@return {array<int>[]}  array of offsets.
*/
HexGrid.prototype.getOffsets = function() {
    return this.offsets;
}

/**
<pre>
None
</pre>
@method getRadius
@return {int} radius.
*/
HexGrid.prototype.getRadius = function() {
    return this.radius;
}

/**
<pre>
None
</pre>
@method getXO
@return {int} x origin coord.
*/
HexGrid.prototype.getXO = function() {
    return this.xO;
}

/**
<pre>
None
</pre>
@method getYO
@return {int} y origin coord.
*/
HexGrid.prototype.getYO = function() {
    return this.yO;
}