/**
Map Class 
<pre>
</pre>
@class Map 
@constructor
@param {HexGrid} hexGrid
@param {TokenList} numbers
@param {Port[]} ports
@param {int} radius
@param {HexLocation} lastRobber
@param {HexLocation} robber
@param {JSON} mapData
*/
function Map(mapData) {
	this.mapData = mapData;
	this.setRadius(mapData.radius);
	this.setRobber(new HexLocation(mapData.robber.x, mapData.robber.y));
	this.setLastRobber(new HexLocation(mapData.lastRobber.x, mapData.lastRobber.y));
	this.setNumbers(mapData.numbers);
}

/**
SetNumbers method
<pre>
</pre>
@method setNumbers
@param {JSON} modelNumbers
*/
Map.prototype.setNumbers = function(modelNumbers) {

	var temp = new TokenList();
	
	$.each(modelNumbers, function(key, value) {
	
		for(var i=0;i<value.length;i++)
		{
			var newToken = new Token(new HexLocation(value[i].x,value[i].y));
			temp.setToken(key, newToken);
		}
	
	});
	
    this.numbers = temp;
}

/**
GetNumbers method
<pre>
</pre>
@method getNumbers
@return {TokenList} numbers
*/
Map.prototype.getNumbers = function() {
    return this.numbers;
}

/**
SetRadius method
<pre>
</pre>
@method setRadius
@param {int} radius
*/
Map.prototype.setRadius = function(radius) {
    this.radius = radius;
}

/**
GetRadius method
<pre>
</pre>
@method getRadius
@return {int} radius
*/
Map.prototype.getRadius = function() {
    return this.radius;
}

/**
SetRobber method
<pre>
</pre>
@method setRobber
@param {HexLocation} robber
*/
Map.prototype.setRobber = function(robber) {
    this.robber = robber;
}

/**
GetRobber method
<pre>
</pre>
@method getRobber
@return {HexLocation} robber
*/
Map.prototype.getRobber = function() {
    return this.robber;
}

/**
SetLastRobber method
<pre>
</pre>
@method setLastRobber
@param {HexLocation} lastRobber
*/
Map.prototype.setLastRobber = function(lastRobber) {
    this.lastRobber = lastRobber;
}

/**
GetLastRobber method
<pre>
</pre>
@method getLastRobber
@return {HexLocation} lastRobber
*/
Map.prototype.getLastRobber = function() {
    return this.lastRobber;
}