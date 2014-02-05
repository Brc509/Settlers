/**
HexGrid Class 
<pre>
orientation = NE | N | NW | SW | S | SE
inputResource(optional) = Wood | Brick | Sheep | Wheat | Ore
</pre>
@class HexGrid 
@constructor
@param {Array<Hex[]>} hexes
@param {int[]} offsets
@param {int} radius
@param {int} x0
@param {int} y0
*/
function HexGrid(hexes, offsets, radius, x0, y0) {
	this.hexes = hexes;
	this.offsets = offsets;
	this.radius = radius;
	this.x0 = x0;
	this.y0 = y0;
}

/**
GetHexes method
<pre>
</pre>
@method getHexes
@return {Array<Hex[]>} hexes
*/
HexGrid.prototype.getHexes = function() {
    return this.hexes;
}

/**
GetOffsets method
<pre>
</pre>
@method getOffsets
@return {int[]} offsets
*/
HexGrid.prototype.getOffsets = function() {
    return this.offsets;
}

/**
GetRadius method
<pre>
</pre>
@method getRadius
@return {int} radius
*/
HexGrid.prototype.getRadius = function() {
    return this.radius;
}

/**
GetX0 method
<pre>
</pre>
@method getX0
@return {int} x0
*/
HexGrid.prototype.getX0 = function() {
    return this.x0;
}

/**
GetY0 method
<pre>
</pre>
@method getY0
@return {int} y0
*/
HexGrid.prototype.getY0 = function() {
    return this.y0;
}