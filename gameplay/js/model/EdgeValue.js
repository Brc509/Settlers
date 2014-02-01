
/**
The EdgeValue class represents an EdgeValue associated with a particular edge

<pre>
  Domain:
    ownerID: The owner ID associated with the specific EdgeValue
  Invariant:
    The EdgeValue will always have an ownerID
  Constructor Specifications:
    PRE: the ownerID must be a legitimate player ID ID

</pre>

@class EdgeValue
@constructor
@param value {int} the player ID associated with the EdgeValue


*/

  function EdgeValue(ownerID){
    this.ownerID = ownerID;
  }

/**
  <pre>
  Must be a valid Player ID
  </pre>
  @method setID
  @param {int} the player ID associated with the particular EdgeValue
*/
  EdgeValue.prototype.setOwnerID = function (id) {
    this.ownerID = id;
  }

/**
  <pre>
  returns the ownerID for a particular EdgeValue
  </pre>
  @method getOwnerID
  @return {int} returs the ownerID associated with a particular EdgeValue
*/
  EdgeValue.prototype.getOwnerID = function {
    return this.ownerID;
  }

