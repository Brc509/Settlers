/**
The Vertex Value Class 
<pre>
Domain:
            ownerID: unique id number of the owner
            worth: the value of the vertex
            
        Invariants:
            INVARIANT: ownerID exists
			INVARIANT: worth exists
         
        Constructor Specification:
            PRE: ownerID is a player's ID
            PRE: worth is a valid value
            POST: getOwnerID() == ownerID
            POST: getWorth() == worth
</pre>
@class VertexValue 
@constructor
@param {:ownerID} (int)The ID of the owner.
@param {:worth} (int)The worth of the vertex.
*/
function VertexValue(ownerID, worth) {
    this.ownerID = ownerID;
    this.worth = worth;
}

/**
<pre>
An owner ID needs to be defined
</pre>
@method getOwnerID
@return {int} The owner ID.
*/
VertexValue.prototype.getOwnerID = function() {
    return this.ownerID;
}

/**
<pre>
A vertex value needs to have a value
</pre>
@method getWorth
@return {int} The value of the vertex.
*/
VertexValue.prototype.getWorth = function() {
    return this.worth;
}