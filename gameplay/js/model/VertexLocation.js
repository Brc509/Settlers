
/**
The VertexLocation class represents the location of a certain vertex by its x and y coordinate on the map as well as its direction.

<pre>
  Domain:
    x: the x coordinate of the VertexLocation, Number.
    y: the y coordinate of the VertexLocation, Number.
    direction: the direction the vertexLocation is facing, String.
  Invariant:
    A VertexLocation will always have an x and y value as well as a direction.
  Constructor Specifications:
    PRE: !isNaN(xValue)
    PRE: !isNaN(yValue)
    PRE: direction must be a string with the value "NW"|"N"|"NE"|"E"|"SE"|"SW"
    POST: getX() == xValue
    POST: getY() == yValue
    POST: getDirection() == direction
  </pre>

  @class VertexLocation
  @constructor
  @param xValue {Number} The VertexValue's x coordinate
  @param yValue {Number} The VertexValue's y coordinate
  @param direction {String} The VertexValue's direction

*/

function VertexLocation(xValue, yValue, direction){
  this.x = xValue;
  this.y = yValue;
  this.direction = direction;
}

/**
  <pre>
  Gets the x coordinate of the vertex value
  </pre>
  @method getX
  @return {Number} the x coordinate of the vertexvalue
*/
VertexLocation.prototype.getX = function() {
  return this.x;
};

/**
<pre>
PRE: !isNaN(xValue)
</pre>

@method setX
@param {Number} xValue The VertexValues's new x coordinate
*/

VertexLocation.prototype.setX = function(xValue){
  this.x = xValue;
};

/**
  <pre>
  Gets the y coordinate of the vertex value
  </pre>
  @method getY
  @return {Number} the y coordinate of the vertexvalue
*/

VertexLocation.prototype.getY = function() {
  return this.y;
};

/**
<pre>
PRE: !isNaN(yValue)
</pre>

@method setY
@param {Number} The VertexValues's new y coordinate
*/

VertexLocation.prototype.setY = function(yValue){
  this.y = yValue;
};


/**
  <pre>
  Gets the direction of the vertex value
  </pre>
  @method getDirction
  @return {String} the dirction of of the VertexValue
*/
VertexLocation.prototype.getDirection = function() {
  return this.direction;
};

/**
<pre>
PRE: does the inpute equal one of these: "NW"|"N"|"NE"|"E"|"SE"|"SW"
</pre>

@method setDirection
@param {String} The VertexValues's new direction
*/

VertexLocation.prototype.setDirection = function(newDirection){
  this.direction = newDirection;
};
