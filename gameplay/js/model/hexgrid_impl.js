//STUDENT-EDITABLE-BEGIN   
var catan = catan || {};
catan.models = catan.models || {};

/**
	This module contains the map
	
	@module catan.models
	@namespace models
*/

catan.models.Map = (function mapNameSpace() {

	var hexgrid = catan.models.hexgrid;
	
	var HexLocation = hexgrid.HexLocation;
	var EdgeLocation = hexgrid.EdgeLocation;
	var VertexLocation = hexgrid.VertexLocation;
	
	var Map = (function Map_Class() {
	
		function Map(radius) {
			this.hexGrid = hexgrid.HexGrid.getRegular(radius, Hex);
			this.ports = initPorts();
		}
		
		function initPorts() {
			var ports = {};
			for (n = 0; n < 9; n++) {
				ports[n] = new Port();
			}
			return ports;
		}
		
		Map.prototype.update = function(mapModel) {
		
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
			
			var modelPorts = mapModel.ports;
			for (portNum in modelPorts) {
				var modelPort = modelPorts[portNum];
				this.ports[portNum].set(modelPort);
			}
			
			// UPDATE THE ROBBER
			
			var mapRobber = mapModel.robber;
			if (mapRobber) {
				this.robber = new HexLocation(mapRobber.x, mapRobber.y);
			}
		};
		
		Map.prototype.getHexes = function() {
			return this.hexGrid.hexes;
		};
		
		Map.prototype.getHexGrid = function() {
			return this.hexGrid;
		};
		
		Map.prototype.getTokens = function() {
			return this.tokens;
		};
		
		Map.prototype.getPorts = function() {
			return this.ports;
		};
		
		Map.prototype.getRobber = function() {
			return this.robber;
		};
		
		return Map;
		
	}());
	
	/**
	This class represents a Hex. You may add any methods that you need (e.g., to get the resource/hex type, etc.)
	
	In order to work with the hexgrid, this class must extend hexgrid.BasicHex (already done in the code). You also need to implement
	a Vertex and Edge classes (stubs are provided in this file).	Look at their documentation to see what needs to be done there.
	
	The hexgrid will be passed an instance of this class to use as a model, and will pull the constructor from that instance. 
	(The core.forceInherit sets the constructor, in case you are curious how that works)
	
	@constructor
	@param {HexLocation} location The HexLocation of this hex
	@extends hexgrid.BasicHex
	
	@class Hex
	*/
	var Hex = (function Hex_Class() {
	
		core.forceClassInherit(Hex, hexgrid.BasicHex);
		
		function Hex(location) {
			hexgrid.BasicHex.call(this,location,Edge,Vertex);
		}
		
		// Set the hex to match a hex from the model JSON
		Hex.prototype.set = function(modelHex) {
		
			// Set the hex type
			if (modelHex.isLand) {
				this.isLand = true;
				if (modelHex.landtype) {
					this.type = modelHex.landtype.toLowerCase();
				} else {
					this.type = 'desert';
				}
			} else {
				this.isLand = false;
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
		
		Hex.prototype.getIsLand = function() {
			return this.isLand;
		};
		
		Hex.prototype.getType = function() {
			return this.type;
		};
		
		return Hex;
		
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
	@class Edge
	*/
	var Edge = (function Edge_Class() {
	
		core.forceClassInherit(Edge, hexgrid.BaseContainer);
		
		function Edge() {
			this.ownerID = -1;
		}
		
		// Set the edge to match an edge from the model JSON
		Edge.prototype.set = function(modelEdge) {
			this.ownerID = modelEdge.value.ownerID;
		};
		
		Edge.prototype.getOwnerID = function() {
			return this.ownerID;
		};
		
		Edge.prototype.isOccupied = function() {
			if (this.ownerID == -1) {
				return false;
			} else {
				return true;
			}
		};
		
		return Edge;
		
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
	@class Vertex
	*/
	var Vertex = (function Vertex_Class() {
	
		core.forceClassInherit(Vertex, hexgrid.BaseContainer);
		
		function Vertex() {
			this.worth = 0;
			this.ownerID = -1;
		}
		
		// Set the vertex to match a vertex from the model JSON
		Vertex.prototype.set = function(modelVertex) {
			this.worth = modelVertex.value.worth;
			this.ownerID = modelVertex.value.ownerID;
		};
		
		Vertex.prototype.getWorth = function() {
			return this.worth;
		};
		
		Vertex.prototype.getOwnerID = function() {
			return this.ownerID;
		};
		
		Vertex.prototype.isOccupied = function() { 
			if (this.ownerID == -1) {
				return false;
			} else {
				return true;
			}
		};
		
		return Vertex;
		
	}());
	
	var Port = (function Port_Class() {
	
		function Port() {
		}
		
		Port.prototype.set = function(modelPort) {
		
			// Set the location
			var loc = modelPort.location;
			this.location = new EdgeLocation(loc.x, loc.y, modelPort.orientation);
			
			// Set the valid vertexes
			var vv0 = modelPort.validVertex1;
			var vv1 = modelPort.validVertex2;
			this.validVertexes = {};
			this.validVertexes[0] = new VertexLocation(vv0.x, vv0.y, vv0.direction);
			this.validVertexes[1] = new VertexLocation(vv1.x, vv1.y, vv1.direction);
			
			// Set the ratio
			this.ratio = modelPort.ratio;
			
			// Set the input resource
			this.inputResource = modelPort.inputResource
			if (this.inputResource) {
				this.inputResource = this.inputResource.toLowerCase();
			}
		};
		
		Port.prototype.getLocation = function() {
			return this.location;
		};
		
		Port.prototype.getValidVertexes = function() {
			return this.validVertexes;
		};
		
		Port.prototype.getRatio = function() {
			return this.ratio;
		};
		
		Port.prototype.getInputResource = function() {
			return this.inputResource;
		};
		
		return Port;
		
	}());
	
	return Map;
	
}());
