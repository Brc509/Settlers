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
			// TODO Decompose the robber into a separate class
		}
		
		Map.prototype.update = function(mapModel) {
		
			// UPDATE THE HEXGRID
			var modelHexGrid = mapModel.hexGrid;
			var localHexGrid = this.hexGrid;
			var modelHexes = modelHexGrid.hexes;
			var localHexes = localHexGrid.hexes;
			
			// Update the hexes line by line
			for (line in modelHexes) {
			
				var modelLine = modelHexes[line];
				var localLine = localHexes[line];
				
				// Update each hex in the line
				for (hex in modelLine) {
				
					var modelHex = modelLine[hex];
					
					// Create the new hex
					var localHex = new CatanHex(modelHex.location);
					
					// Update its land type
					if (modelHex.isLand) {
						if (modelHex.landtype) {
							localHex.setType(modelHex.landtype.toLowerCase());
						} else {
							localHex.setType('desert');
						}
					} else {
						localHex.setType('water');
					}
					
					var modelVertexes = modelHex.vertexes;
					var localVertexes = localHex.vertexes;
					
					// // Update the vertexes
					// for (vertex in modelVertexes) {
					
						// var modelVertex = modelVertexes[vertex];
						// var localVertex = localVertexes[vertex];
						
						// // Update the worth
						// localVertex.worth = modelVertex.value.worth;
						
						// // Update the ownerID
						// localVertex.ownerID = modelVertex.value.ownerID;
					// }
					
					// var modelEdges = modelHex.edges;
					// var localEdges = localHex.edges;
					
					// // Update the edges
					// for (edge in modelEdges) {
					
						// var modelEdge = modelEdges[edge];
						// var localEdge = localEdges[edge];
						
						// // Update the ownerID
						// localEdge.ownerID = modelEdge.value.ownerID;
					// }
					
					// Assign the new hex to the model
					localLine[hex] = localHex;
				}
			}
			
			// // Update the offsets
			// localHexGrid.offsets = modelHexGrid.offsets;
			
			// // Update the radius
			// localHexGrid.radius = modelHexGrid.radius;
			
			// // Update the origin
			// localHexGrid.x0 = modelHexGrid.x0;
			// localHexGrid.y0 = modelHexGrid.y0;
			
			// TODO UPDATE THE NUMBERS
			
			// TODO UPDATE THE PORTS
			
			// UPDATE THE ROBBER (IF DEFINED)
			if (mapModel.robber) {
				var mapRobber = mapModel.robber;
				this.robberLocation = new catan.models.hexgrid.HexLocation(mapRobber.x, mapRobber.y);
			}
		};
		
		Map.prototype.getHexes = function() {
			return this.hexGrid.hexes;
		};
		
		Map.prototype.getRobberLocation = function() {
			return this.robberLocation;
		};
		
		return Map;
		
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
		
		function CatanEdge(){
			this.ownerID = -1;
		}
		
		CatanEdge.prototype.setOwner = function(ownerID){
			this.ownerID = ownerID;
		}
		
		CatanEdge.prototype.getOwner = function(){
			return this.ownerID;
		}
		
		// once you override this, put in some documentation
		CatanEdge.prototype.isOccupied = function(){
			if (this.ownerID == -1) {
				return false;
			} else {
				return true;
			}
		}
		
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
		
		function CatanVertex(){
			this.worth = 0;
			this.ownerID = -1;
		}
		
		CatanVertex.prototype.setWorth = function(worth){
			this.worth = worth;
		}
		
		CatanVertex.prototype.getWorth = function(){
			return this.worth;
		}
		
		CatanVertex.prototype.setOwner = function(ownerID){
			this.ownerID = ownerID;
		}
		
		CatanVertex.prototype.getOwner = function(){
			return this.ownerID;
		}
		
		// once you override this, put in some documentation
		CatanVertex.prototype.isOccupied = function(){ 
			if (this.ownerID == -1) {
				return false;
			} else {
				return true;
			}
		}
		
		return CatanVertex;
		
	}());
	
	/**
	This class represents a Hex. You may add any methods that you need (e.g., to get the resource/hex type, etc.)
	
	In order to work with the hexgrid, this class must extend hexgrid.BasicHex (already done in the code). You also need to implement
	a CatanVertex and CatanEdge classes (stubs are provided in this file).	Look at their documentation to see what needs to be done there.
	
	The hexgrid will be passed an instance of this class to use as a model, and will pull the constructor from that instance. 
	(The core.forceInherit sets the constructor, in case you are curious how that works)
	
	@constructor
	@param {hexgrid.HexLocation} location - the location of this hex. It's used to generate locations for the vertexes and edges.
	@extends hexgrid.BasicHex
	
	@class CatanHex
	*/
	var CatanHex = (function CatanHex_Class(){
	
		core.forceClassInherit(CatanHex, hexgrid.BasicHex);
		
		function CatanHex(location, type){		  
			hexgrid.BasicHex.call(this,location,CatanEdge,CatanVertex);
			this.type = type;
		}
		
		CatanHex.prototype.getType = function() {
			return this.type;
		};
		
		CatanHex.prototype.setType = function(type) {
			this.type = type;
		};
		
		return CatanHex;
		
	}());
	
	return Map;
	
}());
