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
function Map(playerID) {
	this.playerID = playerID;
}

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
function update(mapData) {
	this.mapData = mapData;
	this.setRadius(mapData.radius);
	this.setRobber(new HexLocation(mapData.robber.x, mapData.robber.y));
	this.setLastRobber(new HexLocation(mapData.lastRobber.x, mapData.lastRobber.y));
	this.setNumbers(mapData.numbers);
	this.map = this.setMap(mapData);
}


/**
GetMap method
<pre>
</pre>
@method getMap
@return {catan.models.Map} map
*/
Map.prototype.getMap = function() {
    return this.map;
}

/**
SetMap method
<pre>
</pre>
@method setMap
@param {JSON} mapData
*/
Map.prototype.setMap = function(mapData) {

	// creating variables to access methods in catan.models namespace
	var mapModel = catan.models.Map;
	var catanEdge = catan.models.Map.CatanEdge;
	var catanVertex = catan.models.Map.CatanVertex;
	
	// initialized map object
	var map = new mapModel(mapData.radius);
	
	// hex data from game model JSON
	var hexes = mapData.hexGrid.hexes;
	
	// initialized hexes stored in hexgrid
	var gridHexes = map.hexGrid.hexes;
	
	// setting the hexes in the hexGrid
	for(var i=0;i < hexes.length; i++)
	{
		var column = hexes[i];
		var gridColumn = gridHexes[i];
		
		// setting map hexes with game model map hex data
		for(var j=0; j < column.length;j++)
		{
			// setting 6 vertices
			for(var k=0;k<6;k++)
			{
				var vertex = gridColumn[j].getVertex(k);
				vertex.setWorth(column[j].vertexes[k].value.worth);
				//vertex['worth']=column[j].vertexes[k].worth;
				
				vertex.setOwner(column[j].vertexes[k].value.ownerID)
				//vertex['ownerID']=column[j].vertexes[k].ownerID;
			}
			
			// setting 6 edges
			for(var k=0;k<6;k++)
			{
				var edge = gridColumn[j].getEdge(k);
				edge.setOwner(column[j].edges[k].value.ownerID);
			}
		}
	}
	
	return map;

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