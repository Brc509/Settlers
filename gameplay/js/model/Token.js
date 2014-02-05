/**
Token Class 
<pre>
</pre>
@class Token 
@constructor
@param {HexLocation} location
*/
function Token(location) {
    this.location = location;
}

/**
GetLocation method
<pre>
</pre>
@method getLocation
@return {HexLocation} location
*/
Token.prototype.getLocation = function() {
    return this.location;
}

/**
SetLocation method
<pre>
</pre>
@method setLocation
@param {HexLocation} location
*/
Token.prototype.setLocation = function(location) {
    this.location = location;
}