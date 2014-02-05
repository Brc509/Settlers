/**
Port Class 
<pre>
orientation = NE | N | NW | SW | S | SE
inputResource(optional) = Wood | Brick | Sheep | Wheat | Ore
</pre>
@class Port 
@constructor
@param {String} inputResource
@param {String} orientation
@param {HexLocation} location
@param {int} ratio
@param {Vertex} validVertex1
@param {Vertex} validVertex2
*/
function Port(orientation, location, ratio, validVertex1, validVertex2) {
	this.orientation = orientation;
	this.location = location;
	this.ratio = ratio;
	this.validVertex1 = validVertex1;
	this.validVertex2 = validVertex2;
}

/**
GetRatio method
<pre>
</pre>
@method getRatio
@return {int} ratio
*/
Port.prototype.getRatio = function() {
    return this.ratio;
}

/**
GetValidVertex1 method
<pre>
</pre>
@method getValidVertex1
@return {Vertex} validVertex1
*/
Port.prototype.getValidVertex1 = function() {
    return this.validVertex1;
}

/**
GetValidVertex2 method
<pre>
</pre>
@method getValidVertex2
@return {Vertex} validVertex2
*/
Port.prototype.getValidVertex2 = function() {
    return this.validVertex2;
}

/**
GetInputResource method
<pre>
Input Resource may not exist because it's optional
</pre>
@method getInputResource
@return {String} inputResource
*/
Port.prototype.getInputResource = function() {
    return this.inputResource;
}

/**
SetInputResource method
<pre>
This is an optional setting
</pre>
@method setInputResource
@param {String} inputResource
*/
Port.prototype.setInputResource = function(inputResource) {
    this.inputResource = inputResource;
}