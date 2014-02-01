
/**
The Port class represents an instance of one of the 9 possible ports on the map.


<pre>
  Domain:
    inputResource: the resource the port trades for, String.
    location: location of the port, HexLocation.
    orientatoin: the orientation the port is facing, String.
    ratio: the ratio for trade in of the port, Number.
    validVertex1: the first vertex of where the port is located, VertexLocation
    validVertex2: the second vertex of where the port is located, VertexLocation.
  Invariant:
    The port will always contain some type of resource.
  Constructor Specifications:
    PRE: inputResource is an optional parameter
    PRE: if an inputResource is included it must == |"Wood"|"Brick"|"Sheep"|"Wheat"|"Ore"|
    PRE: orientation must == |"NW"|"N"|"NE"|"E"|"SE"|"SW"|
    POST: getInputResource() === inputResource
</pre>

@class Port
@constructor
@param inputResource {String} the resource the port trades for, if it's ommitted the port can contain any resource.
@param location {HexLocation} the location of the port, in terms of what hex it is on
@param orientation {String} the orientation of the port, which edge the port is in.
@param ratio {int} The ratio for trade in (ie if this is 2, then it's a 2:1 port, etc)
@param validVertex1 {VertexLocation} the port must contain two valid vertex locations to specify where the port is located, this is the first.
@Param validVertex2 {VertextLocation} the port must contain two valid vertex locations to specify where the port is located, this is the second.

*/

  function Port(inputResource, location, orientation, ration, validVertex1, validVertex2){
    this.inputResource = inputResource;
    this.location = location;
    this.orientation = orientation;
    this.validVertex1 = validVertex1;
    this.validVertex2 = validVertex2;
  }

/**
  <pre>
  if an input source is to be set if must equal |"Wood"|"Brick"|"Sheep"|"Wheat"|"Ore"|
  or else it can be an empty string ("") and the port will then trade for any resource.
  </pre>
  @method setInputResource
  @param {String} the type of resource the port trades for
*/
  Port.prototype.setInputResource = function (iResource) {
    this.inputResource = iResource;
  }

/**
  <pre>
  returns the type of resource the port trades for
  </pre>
  @method getInputResource
  @return {String} the type of resource the port trades for
*/
  Port.prototype.getInputResource = function {
    return this.inputResource;
  }

