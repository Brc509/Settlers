/**
Building Class 
<pre>
</pre>
@class Building 
@constructor
@param {String} type
@param {int} value
@param {int} ownerID
*/
function Building(type, value, ownerID) {
    this.type = type;
	this.value = value;
	this.ownerID = ownerID;
}

/**
GetType method
<pre>
</pre>
@method getType
@return {String} type
*/
Building.prototype.getType = function() {
    return this.type;
}

/**
GetValue method
<pre>
</pre>
@method getValue
@return {int} value
*/
Building.prototype.getValue = function() {
    return this.value;
}

/**
GetOwner method
<pre>
</pre>
@method getOwner
@return {int} ownerID
*/
Building.prototype.getOwner = function() {
    return this.ownerID;
}