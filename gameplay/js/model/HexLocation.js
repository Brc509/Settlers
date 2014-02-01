/**
The Hex Location Class 
<pre>
Domain:
            x: the x coordinate of Hex tile
            y: the y coordinate of Hex tile
            
        Invariants:
            INVARIANT: x exists
			INVARIANT: y exists
         
        Constructor Specification:
            PRE: x is valid x coordinate
            PRE: y is valid y coordinate
            POST: getX() == x
            POST: getY() == y
</pre>
@class HexLocation 
@constructor
@param {:x} (int)The x coordinate of the Hex tile.
@param {:y} (int)The y coordinate of the Hex tile.
*/
function HexLocation(x, y) {
    this.x = x;
    this.y = y;
}

/**
<pre>
x value must be valid
</pre>
@method getX
@return {int} The x coord.
*/
HexLocation.prototype.getX = function() {
    return this.x;
}

/**
<pre>
y value must be valid
</pre>
@method getY
@return {int} The y coord.
*/
HexLocation.prototype.getY = function() {
    return this.y;
}