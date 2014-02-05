/**
Vertex Class 
<pre>
</pre>
@class Vertex 
@constructor
@param {Building} building
*/
function Vertex(building) {
    this.building = building;
}

/**
GetBuilding method
<pre>
</pre>
@method getBuilding
@return {Building} building
*/
Vertex.prototype.getBuilding = function() {
    return this.building;
}

/**
SetBuilding method
<pre>
</pre>
@method setBuilding
@param {Building} building
*/
Vertex.prototype.setBuilding = function(building) {
    this.building = building;
}