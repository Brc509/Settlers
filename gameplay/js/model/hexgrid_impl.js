//STUDENT-EDITABLE-BEGIN   
var catan = catan || {};
catan.models = catan.models || {};

/**
	This module contains the map
	
	@module catan.models
	@namespace models
*/

catan.models.Map = (function mapNameSpace(){

	var hexgrid = catan.models.hexgrid;
	
	var Map = (function Map_Class(){
	
		function Map(radius)
		{
			this.hexGrid = hexgrid.HexGrid.getRegular(radius, CatanHex);
		}
		
		Map.prototype.update = function(mapModel) {
		
			var HexLocation = catan.models.hexgrid.HexLocation;
		
			// UPDATE THE HEXGRID
			
			var modelHexes = mapModel.hexGrid.hexes;
			var localHexes = this.hexGrid.hexes;
			// Update the hexes line by line
			for (lineNum in modelHexes) {
			
				var modelLine = modelHexes[lineNum];
				var localLine = localHexes[lineNum];
				// Update each hex in the line
				for (hexNum in modelLine) {
					var modelHex = modelLine[hexNum];
					localLine[hexNum].set(modelHex);
				}
			}
			
			// UPDATE THE TOKENS
			
			this.tokens = {};
			var nums = [2, 3, 4, 5, 6, 8, 9, 10, 11, 12];
			// Update the token location(s) corresponding to each valid number
			for (num in nums) {
				var n = nums[num];
				// Clear existing token location(s) for this number
				this.tokens[n] = {};
				var localNTokens = this.tokens[n];
				var modelNTokens = mapModel.numbers[n];
				// Recreate the token location(s) for this number
				for (k in modelNTokens) {
					var tokenLoc = modelNTokens[k];
					localNTokens[k] = new HexLocation(tokenLoc.x, tokenLoc.y);
				}
			}
			
			// UPDATE THE PORTS
			// TODO
			
			// UPDATE THE ROBBER
			
			var mapRobber = mapModel.robber;
			if (mapRobber) {
				this.robber = new HexLocation(mapRobber.x, mapRobber.y);
			}
		};
		
		Map.prototype.getHexes = function() {
			return this.hexGrid.hexes;
		};
		
		Map.prototype.getTokens = function() {
			return this.tokens;
		};
		
		Map.prototype.getRobber = function() {
			return this.robber;
		};
		
		return Map;
		
	}());
	
	/**
	This class represents a Hex. You may add any methods that you need (e.g., to get the resource/hex type, etc.)
	
	In order to work with the hexgrid, this class must extend hexgrid.BasicHex (already done in the code). You also need to implement
	a CatanVertex and CatanEdge classes (stubs are provided in this file).	Look at their documentation to see what needs to be done there.
	
	The hexgrid will be passed an instance of this class to use as a model, and will pull the constructor from that instance. 
	(The core.forceInherit sets the constructor, in case you are curious how that works)
	
	@constructor
	@param {HexLocation} location The HexLocation of this hex
	@extends hexgrid.BasicHex
	
	@class CatanHex
	*/
	var CatanHex = (function CatanHex_Class(){
	
		core.forceClassInherit(CatanHex, hexgrid.BasicHex);
		
		function CatanHex(location) {
			hexgrid.BasicHex.call(this,location,CatanEdge,CatanVertex);
		}
		
		// Set the hex to match a hex from the model JSON
		CatanHex.prototype.set = function(modelHex) {
		
			// Set the hex type
			if (modelHex.isLand) {
				if (modelHex.landtype) {
					this.type = modelHex.landtype.toLowerCase();
				} else {
					this.type = 'desert';
				}
			} else {
				this.type = 'water';
			}
			
			// Set the edges
			var edges = {};
			for (n = 0; n < 6; n++) {
				this.edges[n].set(modelHex.edges[n]);
			}
			
			// Set the vertexes
			var vertexes = {};
			for (n = 0; n < 6; n++) {
				this.vertexes[n].set(modelHex.vertexes[n]);
			}
		};
		
		CatanHex.prototype.getType = function() {
			return this.type;
		};
		
		return CatanHex;
		
	}());
	
	/**
	This class represents an edge. It inherits from BaseContainer.
	The data in this class (that you get from the JSON model) is independent of the hexgrid, except for the location.
	Therefore, we leave it up to you to decide how to implement it.
	It must however implement one function that the hexgrid looks for: 'isOccupied' - look at its documentation.
	From the JSON, this object will have two properties: location, and ownerID.
	Besides the 'isOccupied' method, you may add any other methods that you need.
	@constructor
	@extends hexgrid.BaseContainer
	@class CatanEdge
	*/
	var CatanEdge = (function CatanEdge_Class(){
	
		core.forceClassInherit(CatanEdge, hexgrid.BaseContainer);
		
		function CatanEdge() {
			this.ownerID = -1;
		}
		
		// Set the edge to match an edge from the model JSON
		CatanEdge.prototype.set = function(modelEdge) {
			this.ownerID = modelEdge.value.ownerID;
		};
		
		CatanEdge.prototype.getOwnerID = function(){
			return this.ownerID;
		};
		
		CatanEdge.prototype.isOccupied = function(){
			if (this.ownerID == -1) {
				return false;
			} else {
				return true;
			}
		};
		
		return CatanEdge;
		
	}());
	
	/**
	This class represents a vertex. It inherits from BaseContainer.
	The data in this class (that you get from the JSON model) is independent of the hexgrid, except for the location.
	Therefore, we leave it up to you to decide how to implement it.
	It must however implement one function that the hexgrid looks for: 'isOccupied' - look at its documentation.
	From the JSON, this object will have three properties: location, ownerID and worth.
	Besides the 'isOccupied' method, you may add any other methods that you need.
	@constructor
	@extends hexgrid.BaseContainer
	@class CatanVertex
	*/
	var CatanVertex = (function CatanVertex_Class(){
	
		core.forceClassInherit(CatanVertex, hexgrid.BaseContainer);
		
		function CatanVertex() {
			this.worth = 0;
			this.ownerID = -1;
		}
		
		// Set the vertex to match a vertex from the model JSON
		CatanVertex.prototype.set = function(modelVertex) {
			this.worth = modelVertex.value.worth;
			this.ownerID = modelVertex.value.ownerID;
		};
		
		CatanVertex.prototype.getWorth = function() {
			return this.worth;
		};
		
		CatanVertex.prototype.getOwnerID = function() {
			return this.ownerID;
		};
		
		CatanVertex.prototype.isOccupied = function() { 
			if (this.ownerID == -1) {
				return false;
			} else {
				return true;
			}
		};
		
		return CatanVertex;
		
	}());
	
	return Map;
	
}());
