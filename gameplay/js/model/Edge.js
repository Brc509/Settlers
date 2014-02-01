
/**
The Edge class represents an Edge on one of the hexes on the board

<pre>
  Domain:
    value: The value of the edge, EdgeValue
  Invariant:
    The Edge will always have a value
  Constructor Specifications:
    PRE: the value must be a legitimate EdgeValue (ownerID)

</pre>

@class Edge
@constructor
@param value {EdgeValue} the value associated with the Edge (ownerID)


*/

  function Edge(edgeValue){
    this.value = value
  }

/**
  <pre>
  Must be a valid EdgeValue (ownerID)
  </pre>
  @method setValue
  @param {EdgeValue} the value associated with the particular edge
*/
  Edge.prototype.setEdgeValue = function (edgeValue) {
    this.value = edgeValue;
  }

/**
  <pre>
  returns the edgvalue for a particular edge
  </pre>
  @method getEdgeValue
  @return {EdgeValue} returs the value associated with a particular edge
*/
  Edge.prototype.getEdgeValue = function {
    return this.value;
  }

