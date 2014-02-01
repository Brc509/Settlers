
/**
The Vertex class represents one of the Vertexes associated with a particular Hex
<pre>
  Domain:
    value: The value of the vertex, VertexValue
  Invariant:
    The Vertex will always have a value
  Constructor Specifications:
    PRE: the value must be a legitimate VertexValue (ownerID, and worth)

</pre>

@class Vertex
@constructor
@param value {VertexValue} the value associated with the Vertex


*/

  function Vertex(vertexValue){
    this.value = vertexValue
  }

/**
  <pre>
  Must be a valid VertexValue
  </pre>
  @method setValue
  @param {VertexValue} the value associated with the particular vertex
*/
  Vertex.prototype.setVertexValue = function (vValue) {
    this.value = vValue;
  }

/**
  <pre>
  returns the value for a particular Vertex
  </pre>
  @method getVertexValue
  @return {VertexValue} returs the value associated with a particular Vertex
*/
  Vertex.prototype.getVertexValue = function {
    return this.value;
  }

