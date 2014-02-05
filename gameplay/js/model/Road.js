/**
Road Class 
<pre>
</pre>
@class Road 
@constructor
@param {int} ownerID
*/
function Road(ownerID) {
	this.ownerID = ownerID;
}

/**
GetOwner method
<pre>
</pre>
@method getOwner
@return {int} ownerID
*/
Road.prototype.getOwner = function() {
    return this.ownerID;
}