//STUDENT-EDITABLE-BEGIN
/**
	This this contains interfaces used by the map and robber views
	@module catan.map
	@namespace map
*/

var catan = catan || {};
catan.map = catan.map || {};

catan.map.Controller = (function catan_controller_namespace() {

	var MapController = (function main_controller_class() {
    
	    var EdgeLoc = catan.map.View.EdgeLoc;
		var VertexLoc = catan.map.View.VertexLoc;
		var PortLoc = catan.map.View.PortLoc;
		
		var HexLocation = catan.models.hexgrid.HexLocation;
		var VertexLocation = catan.models.hexgrid.VertexLocation;
		var EdgeLocation= catan.models.hexgrid.EdgeLocation;
		var VertexDirection = catan.models.hexgrid.VertexDirection;
		var EdgeDirection= catan.models.hexgrid.EdgeDirection;
		
		// Lookup tables
		var edLookup = ["NW","N","NE","SE","S","SW"]; 			// From hexgrid.js
		var EdgeDirection = core.numberEnumeration(edLookup);	// From hexgrid.js
		var vdLookup = ["W","NW","NE","E","SE","SW"]; 			// From hexgrid.js
		var VertexDirection = core.numberEnumeration(vdLookup);	// From hexgrid.js
		
 		core.forceClassInherit(MapController,catan.core.BaseController);
        
		core.defineProperty(MapController.prototype,"robView");
		core.defineProperty(MapController.prototype,"modalView");
        
        /**
		 * @class MapController
		 * @constructor
		 * @param {MapView} view - The initialized map view
		 * @param {MapOverlay} modalView - The overlay to use for placing items on the board.
		 * @param {ClientModel} model - The client model
		 * @param {RobberOverlay} robView - The robber overlay to be used when the robber is being placed.  This is undefined for the setup round.
		 */
		function MapController(view, modalView, model, robView){
			catan.core.BaseController.call(this,view,model);
			this.setModalView(modalView);
			this.setRobView(robView);
		}
        
        /**
		 This method is called by the Rob View when a player to rob is selected via a button click.
		 @param {Integer} orderID The index (0-3) of the player who is to be robbed
		 @method robPlayer
		*/
		MapController.prototype.robPlayer = function(orderID){
			this.getClientModel().clientProxy.robPlayer(orderID, this.robberSpot, this.getClientModel().updateModel);
			this.getRobView().closeModal();
			this.getClientModel().isModalUp = false;
		}
        
        /**
		 * Starts the robber movement on the map. The map should pop out and the player should be able
         * move the robber.  This is called when the user plays a "solider" development card.
		 * @method doSoldierAction
		 * @return void
		**/		
		MapController.prototype.doSoldierAction = function(){
			var playerIndex = this.getClientModel().playerIndex;
			var color = this.colorLookupPlayerIndex[playerIndex];
			this.getModalView().showModal('robber');
			this.getClientModel().isModalUp = true;
			this.getView().startDrop('robber', color);
		}
        
		/**
		 * Pops the map out and prompts the player to place two roads.
         * This is called when the user plays a "road building" progress development card.
		 * @method startDoubleRoadBuilding
		 * @return void
		**/	
		MapController.prototype.startDoubleRoadBuilding = function(){
			this.roadBuilding = true;
			this.roadBuildingNumBuilt = 1;
			this.startMove('road', true, false);
		}
		
        
        /**
		 * Pops the map out and prompts the player to place the appropriate piece
         * @param {String} pieceType - "road", "settlement", or "city
         * @param {boolean} free - Set to true in road building and the initial setup
         * @param {boolean} disconnected - Whether or not the piece can be disconnected. Set to true only in initial setup
		 * @method startMove
		 * @return void
		**/	
		MapController.prototype.startMove = function (pieceType,free,disconnected){
			pieceType = pieceType.toLowerCase();
			this.free = free;
			this.disconnected = disconnected;
			var playerIndex = this.getClientModel().playerIndex;
			var color = this.colorLookupPlayerIndex[playerIndex];
			this.getModalView().showModal(pieceType);
			this.getClientModel().isModalUp = true;
			this.getView().startDrop(pieceType.toLowerCase(), color);
		};
        
		/**
		 * This method is called from the modal view when the cancel button is pressed. 
		 * It should allow the user to continue gameplay without having to place a piece. 
		 * @method cancelMove
		 * @return void
		 * */
		MapController.prototype.cancelMove = function(){
			this.getView().cancelDrop();
			this.getClientModel().isModalUp = false;
		}

		/**
		 This method is called whenever the user is trying to place a piece on the map. 
         It is called by the view for each "mouse move" event.  
         The returned value tells the view whether or not to allow the piece to be "dropped" at the current location.

		 @param {MapLocation} loc The location being considered for piece placement
		 @param {String} type The type of piece the player is trying to place ("robber","road","settlement","city")
		 @method onDrag
		 @return {boolean} Whether or not the given piece can be placed at the current location.
		*/
		MapController.prototype.onDrag = function (loc, type) {
			type = type.type.toLowerCase();
			if (type == 'robber') {
				loc = new HexLocation(loc.x, loc.y);
			}
			// Do new calculations iff the location has changed
			if (!this.lastDragLoc || !loc.equals(this.lastDragLoc)) {
				//console.log('MapController.onDrag(): New location');
				this.lastDragLoc = loc;
				this.isDragLocValid = false;
				// Continue iff the location is part of a valid hex
				if (this.getClientModel().map.getHexGrid().getHex(loc)) {
					switch (type) {
						case 'city':
							this.isDragLocValid = isValidCityLoc.call(this, loc);
							break;
						case 'road':
							this.isDragLocValid = isValidRoadLoc.call(this, loc);
							break;
						case 'robber':
							this.isDragLocValid = isValidRobberLoc.call(this, loc);
							break;
						case 'settlement':
							this.isDragLocValid = isValidSettlementLoc.call(this, loc);
							break;
						default:
							throw Error('MapController.onDrag(): Invalid placeable type specified.');
					}
				}
			}
			return this.isDragLocValid;
		};
		
		var isValidCityLoc = function(loc) {
			var isValid = false;
			var vertexLoc = new VertexLocation(new HexLocation(loc.x, loc.y), VertexDirection[loc.dir]);
			var hex = this.getClientModel().map.getHexGrid().getHex(vertexLoc);
			if (hex) {
				var vertex = hex.getVertex(vertexLoc.direction);
				// City is valid iff the player has a settlement at the location
				if (vertex.getOwnerID() == this.getClientModel().playerIndex && vertex.getWorth() == 1) {
					isValid = true;
				}
			}
			return isValid;
		};
		
		var isValidRoadLoc = function(loc) {
			var isValid = false;
			var edgeLoc = new EdgeLocation(new HexLocation(loc.x, loc.y), EdgeDirection[loc.dir]);
			var equivGroup = edgeLoc.getEquivalenceGroup();
			var hg = this.getClientModel().map.getHexGrid();
			var hex1 = hg.getHex(equivGroup[0]);
			var hex2 = hg.getHex(equivGroup[1]);
			// Continue iff target edge borders a land hex
			if (hex1 && hex2 && (hex1.getIsLand() || hex2.getIsLand())) {
				var edge = hg.getHex(edgeLoc).getEdge(edgeLoc.direction);
				// Consider only unoccupied edges
				if (!edge.isOccupied()) {
					// If in setup phase, only allow player to place road directly adjacent to previously placed settlement
					if (this.disconnected) {
						for (n in this.validEdgeLocsForNextRoad) {
							var validEdgeLoc = this.validEdgeLocsForNextRoad[n];
							if (edgeLocsEquivalent(edgeLoc, validEdgeLoc)) {
								isValid = true;
								break;
							}
						}
					// Otherwise, allow player to place road according to normal rules
					} else if (!collidesWithRoadBuilding.call(this, edgeLoc)) {
						var connectedEdgeLocs = edgeLoc.getConnectedEdges();
						// Consider all adjacent edges
						for (n in connectedEdgeLocs) {
							var connectedEdgeLoc = connectedEdgeLocs[n];
							// Disregard the reflexive case
							if (!edgeLocsEquivalent(connectedEdgeLoc, edgeLoc)) {
								// If in the middle of road building, consider the first road as if it were part of the model
								if (this.roadBuildingLoc1) {
									if (edgeLocsEquivalent(connectedEdgeLoc, this.roadBuildingLoc1)) {
										isValid = true;
										break;
									}
								}
								// Otherwise, check the owner of the adjacent edge
								var connectedEdge = hg.getHex(connectedEdgeLoc).getEdge(connectedEdgeLoc.direction);
								if (connectedEdge.getOwnerID() == this.getClientModel().playerIndex) {
									isValid = true;
									break;
								}
							}
						}
					}
				}
			}
			return isValid;
		};
		
		var edgeLocsEqual = function(edgeLoc1, edgeLoc2) {
			return edgeLoc1.equals(edgeLoc2) && edgeLoc1.direction == edgeLoc2.direction;
		};
		
		var edgeLocsEquivalent = function(edgeLoc1, edgeLoc2) {
			var equivalent = false;
			var equivGroup = edgeLoc2.getEquivalenceGroup();
			for (n in equivGroup) {
				if (edgeLocsEqual(edgeLoc1, equivGroup[n])) {
					equivalent = true;
					break;
				}
			}
			return equivalent;
		};
		
		var collidesWithRoadBuilding = function(edgeLoc) {
			return this.roadBuildingLoc1 && edgeLocsEquivalent(edgeLoc, this.roadBuildingLoc1);
		};
		
		var isValidRobberLoc = function(loc) {
			var isValid = false;
			var map = this.getClientModel().map;
			var hex = map.getHexGrid().getHex(loc);
			var hexLoc = new HexLocation(loc.x, loc.y);
			// Robber is valid iff the target hex is land and not the robber's current location
			if (hex.getIsLand() && !hexLoc.equals(map.getRobber())) {
				isValid = true;
			}
			return isValid;
		};
		
		var isValidSettlementLoc = function(loc) {
			var isValid = false;
			var vertexLoc = new VertexLocation(new HexLocation(loc.x, loc.y), VertexDirection[loc.dir]);
			var hg = this.getClientModel().map.getHexGrid();
			var vertex = hg.getHex(vertexLoc).getVertex(vertexLoc.direction);
			// Continue iff the target vertex is unoccupied
			if (!vertex.isOccupied()) {
				var bordersLand = false;
				var properlySpaced = true;
				var equivGroup = vertexLoc.getEquivalenceGroup();
				// Check adjacent hexes/vertices for land bordering and proper spacing
				for (n in equivGroup) {
					var vertexLocToCheck = equivGroup[n].rotateAboutHexCW();
					var hexToCheck = hg.getHex(vertexLocToCheck);
					if (hexToCheck) {
						if (hexToCheck.getIsLand()) {
							bordersLand = true;
						}
						var vertexToCheck = hexToCheck.getVertex(vertexLocToCheck.direction);
						if (vertexToCheck.isOccupied()) {
							properlySpaced = false;
							break;
						}
					}
				}
				if (bordersLand && properlySpaced) {
					// If in setup phase, allow player to place settlement without connection to own roads
					if (this.disconnected) {
						isValid = true;
					// Otherwise, enforce road adjacency
					} else {
						var connectedEdgeLocs = vertexLoc.getConnectedEdges();
						for (n in connectedEdgeLocs) {
							var connectedEdgeLoc = connectedEdgeLocs[n];
							var connectedEdge = hg.getHex(connectedEdgeLoc).getEdge(connectedEdgeLoc.direction);
							if (connectedEdge.getOwnerID() == this.getClientModel().playerIndex) {
								isValid = true;
								break;
							}
						}
					}
				}
			}
			return isValid;
		};

		/**
		 This method is called when the user clicks the mouse to place a piece.
         This method should close the modal and possibly trigger the Rob View.

		 @param {MapLocation} loc The location where the piece is being placed
		 @param {String} type The type of piece being placed ("robber","road","settlement","city")
		 @method onDrop
		*/
		MapController.prototype.onDrop = function (loc, type) {
			this.getModalView().closeModal();
			this.getClientModel().isModalUp = false;
			switch (type.type.toLowerCase()) {
				case 'city':
					// Place the city
					var vertexLoc = new VertexLocation(new HexLocation(loc.x, loc.y), VertexDirection[loc.dir]);
					this.getClientModel().clientProxy.buildCity(vertexLoc, this.free, this.getClientModel().updateModel);
					break;
				case 'road':
					if (this.roadBuilding) {
						// If the second road was just placed, send the road building command to the server
						if (this.roadBuildingNumBuilt == 2) {
							this.roadBuilding = false;
							var roadBuildingLoc2 = new EdgeLocation(new HexLocation(loc.x, loc.y), EdgeDirection[loc.dir]);
							this.getClientModel().clientProxy.roadBuilding(this.roadBuildingLoc1, roadBuildingLoc2, this.getClientModel().updateModel);
						// If the first road was just placed, continue to the second
						} else {
							this.roadBuildingLoc1 = new EdgeLocation(new HexLocation(loc.x, loc.y), EdgeDirection[loc.dir]);
							this.roadBuildingNumBuilt++;
							setTimeout(function() {this.startMove('road', true, false)}.bind(this), 0); // TODO Change this to this.getView().startDrop()?
						}
					} else {
						// Place the road
						var edgeLoc = new EdgeLocation(new HexLocation(loc.x, loc.y), EdgeDirection[loc.dir]);
						this.getClientModel().clientProxy.buildRoad(edgeLoc, this.free, this.getClientModel().updateModel);
					}
					break;
				case 'robber':
					// TODO Build an array of target players and use this.getRobView().setPlayerInfo(Object[]); use [] if there are no victims. Players with no cards are not shown.
					var cm = this.getClientModel();
					var me = cm.clientPlayer;
					var victims = {};
					var hexLoc = new HexLocation(loc.x, loc.y);
					this.robberSpot = hexLoc;
					var hex = cm.map.getHexGrid().getHex(hexLoc);
					var vertexes = hex.getVertexes();
					for (n in vertexes) {
						var ownerID = vertexes[n].ownerID;
						if (ownerID != -1 && ownerID != me.orderNumber) {
							var numCards = cm.players[ownerID].getResourceCardCount();
							victims[ownerID] = true;
						}
					}
					var playerInfo = [];
					var victimIndex = 0;
					for (victimID in victims) {
						var victim = cm.players[victimID];
						var pInfo = {};
						pInfo.cards = victim.getResourceCardCount();
						pInfo.color = this.colorLookupPlayerIndex[victimID];
						pInfo.name = victim.name;
						pInfo.playerNum = victimID;
						playerInfo[victimIndex] = pInfo;
						victimIndex++;
					}
					this.getRobView().setPlayerInfo(playerInfo);
					this.getRobView().showModal();
					this.getClientModel().isModalUp = true;
					break;
				case 'settlement':
					var vertexLoc = new VertexLocation(new HexLocation(loc.x, loc.y), VertexDirection[loc.dir]);
					// If in setup phase, determine valid locations for next road
					if (this.disconnected) {
						this.validEdgeLocsForNextRoad = vertexLoc.getConnectedEdges();
					}
					// Place the settlement
					this.getClientModel().clientProxy.buildSettlement(vertexLoc, this.free, this.getClientModel().updateModel);
					break;
				default:
					throw Error('MapController.onDrop(): Invalid placeable type specified.');
			}
		};
		
		/**
		Updates the view when the model is changed
		
		@method update
		@param clientModel the ClientModel that has been updated
		*/
		MapController.prototype.update = function(clientModel) {
		
			var view = this.getView();
			var map = clientModel.map;
			
			// Create a color lookup table based on a player indexes and player IDs
			this.colorLookupPlayerIndex = {};
			this.colorLookupPlayerID = {};
			for (playerIndex in clientModel.players) {
				var player = clientModel.players[playerIndex];
				this.colorLookupPlayerIndex[playerIndex] = player.color;
				this.colorLookupPlayerID[player.playerID] = player.color;
			}
			
			// Update the hexes
			var hexes = map.getHexes();
			for (lineNum in hexes) {
				var line = hexes[lineNum];
				for (hexNum in line) {
					var hex = line[hexNum];
					var loc = hex.getLocation();
					var type = hex.getType();
					//console.log('MapController.update(): Hex: (' + loc.x + ',' + loc.y + ') ' + type);
					view.addHex(loc, type, true);
				}
			}
			
			// Update the tokens
			var tokens = map.getTokens();
			var nums = [2, 3, 4, 5, 6, 8, 9, 10, 11, 12];
			for (numNum in nums) {
				var n = nums[numNum];
				var nTokens = tokens[n];
				for (k in nTokens) {
					var tokenLoc = nTokens[k];
					//console.log('MapController.update(): Token: (' + tokenLoc.x + ',' + tokenLoc.y + ') ' + n);
					view.addNumber(tokenLoc, n, true);
				}
			}
			
			// Update the ports
			var ports = map.getPorts();
			for (portNum in ports) {
				var port = ports[portNum];
				var loc = port.getLocation();
				var portLoc = new catan.map.View.PortLoc(loc.x, loc.y, EdgeDirection[loc.direction]);
				var type = port.getInputResource();
				if (!type) {
					type = 'three';
				}
				//console.log('MapController.update(): Port: (' + portLoc.x + ',' + portLoc.y + ',' + portLoc.rotation + ') ' + type);
				view.addPort(portLoc, type, true);
			}
			
			// Update the robber
			var robber = map.getRobber();
			//console.log('MapController.update(): Robber: (' + robber.x + ',' + robber.y + ')');
			view.placeRobber(robber, true);
			
			// Update the settlements, cities, and roads (iterate through all hexes)
			// TODO Use hexgrid.getEdges() and hexgrid.getVertexes() instead of a brute-force iteration through all hexes?
			for (lineNum in hexes) {
				var line = hexes[lineNum];
				for (hexNum in line) {
					var hex = line[hexNum];
					// Update the roads (edges)
					var edges = hex.getEdges();
					for (edgeNum in edges) {
						var edge = edges[edgeNum];
						// Pursue only occupied edges
						if (edge.isOccupied()) {
							var loc = edge.getLocation();
							var dir = edLookup[loc.direction];
							if (dir === "SW" || dir === "S" ||dir === "SE") { // Assertion from EdgeLoc(x, y, dir) in MapView.js
								var edgeLoc = new catan.map.View.EdgeLoc(loc.x, loc.y, dir);
								var color = this.colorLookupPlayerIndex[edge.getOwnerID()];
								//console.log('MapController.update(): Road: (' + edgeLoc.x + ',' + edgeLoc.y + ',' + edgeLoc.dir + ') ' + edge.getOwnerID() + ' ' + color);
								view.placeRoad(edgeLoc, color, true);
							}
						}
					}
					// Update the settlements and cities (vertexes)
					var vertexes = hex.getVertexes();
					for (vertexNum in vertexes) {
						var vertex = vertexes[vertexNum];
						// Pursue only occupied vertexes
						if (vertex.isOccupied()) {
							var loc = vertex.getLocation();
							var dir = vdLookup[loc.direction];
							if (dir === "W" || dir === "E") { // Assertion from VertexLoc(x, y, dir) in MapView.js
								var vertexLoc = new catan.map.View.VertexLoc(loc.x, loc.y, dir);
								var color = this.colorLookupPlayerIndex[vertex.getOwnerID()];
								// If the vertex worth is 1, it's a settlement
								if (vertex.getWorth() == 1) {
									//console.log('MapController.update(): Settlement: (' + vertexLoc.x + ',' + vertexLoc.y + ',' + vertexLoc.dir + ') ' + vertex.getOwnerID() + ' ' + color);
									view.placeSettlement(vertexLoc, color, true);
								// If the vertex worth is 2, it's a city
								} else if (vertex.getWorth() == 2) {
									//console.log('MapController.update(): City: (' + vertexLoc.x + ',' + vertexLoc.y + ',' + vertexLoc.dir + ') ' + vertex.getOwnerID() + ' ' + color);
									view.placeCity(vertexLoc, color, true);
								}
							}
						}
					}
				}
			}
			view.drawPieces();
		};
        
		return MapController;
	} ());

	return MapController;

} ());

