/**
Settlement Class 
<pre>
</pre>
@class Settlement 
@constructor
@param {int} ownerID
*/
function Settlement(ownerID) {
    Building.call(this, "Settlement", 1, ownerID);
}

Settlement.prototype = Object.create(Building.prototype);
Settlement.prototype.constructor = Settlement;

/**
GetType method
<pre>
</pre>
@method getType
@return {String} type
*/
Settlement.getType = function() {
    return Building.prototype.getType.call();
}

/**
GetValue method
<pre>
</pre>
@method getValue
@return {int} value
*/
Settlement.getValue = function() {
    return Building.prototype.getValue.call();
}

/**
GetOwner method
<pre>
</pre>
@method getOwner
@return {int} ownerID
*/
Settlement.getOwner = function() {
    return Building.prototype.getOwner.call();
}