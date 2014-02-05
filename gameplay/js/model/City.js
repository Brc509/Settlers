/**
City Class 
<pre>
</pre>
@class City 
@constructor
@param {int} ownerID
*/
function City(ownerID) {
    Building.call(this, "City", 2, ownerID);
}

City.prototype = Object.create(Building.prototype);
City.prototype.constructor = City;

/**
GetType method
<pre>
</pre>
@method getType
@return {String} type
*/
City.getType = function() {
    return Building.prototype.getType.call();
}

/**
GetValue method
<pre>
</pre>
@method getValue
@return {int} value
*/
City.getValue = function() {
    return Building.prototype.getValue.call();
}

/**
GetOwner method
<pre>
</pre>
@method getOwner
@return {int} ownerID
*/
City.getOwner = function() {
    return Building.prototype.getOwner.call();
}