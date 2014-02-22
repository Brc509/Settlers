//STUDENT-EDITABLE-BEGIN
/**
	This this contains interfaces used by the map and robber views
	@module catan.map
	@namespace map
*/

var catan = catan || {};
catan.map = catan.map || {};

catan.map.Controller = (function catan_controller_namespace() {
	
    var EdgeLoc = catan.map.View.EdgeLoc;
	var VertexLoc = catan.map.View.VertexLoc;
	var PortLoc = catan.map.View.PortLoc;
    
	var HexLocation = catan.models.hexgrid.HexLocation;
	var VertexLocation = catan.models.hexgrid.VertexLocation;
	var EdgeLocation= catan.models.hexgrid.EdgeLocation;
	var VertexDirection = catan.models.hexgrid.VertexDirection;
	var EdgeDirection= catan.models.hexgrid.EdgeDirection;   

	var MapController = (function main_controller_class() {
    
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
		}
        
        /**
		 * Starts the robber movement on the map. The map should pop out and the player should be able
         * move the robber.  This is called when the user plays a "solider" development card.
		 * @method doSoldierAction
		 * @return void
		**/		
		MapController.prototype.doSoldierAction = function(){    
		}
        
		/**
		 * Pops the map out and prompts the player to place two roads.
         * This is called when the user plays a "road building" progress development card.
		 * @method startDoubleRoadBuilding
		 * @return void
		**/	
		MapController.prototype.startDoubleRoadBuilding = function(){
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
		};
        
		/**
		 * This method is called from the modal view when the cancel button is pressed. 
		 * It should allow the user to continue gameplay without having to place a piece. 
		 * @method cancelMove
		 * @return void
		 * */
		MapController.prototype.cancelMove = function(){
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
		};

		/**
		 This method is called when the user clicks the mouse to place a piece.
         This method should close the modal and possibly trigger the Rob View.

		 @param {MapLocation} loc The location where the piece is being placed
		 @param {String} type The type of piece being placed ("robber","road","settlement","city")
		 @method onDrop
		*/
		MapController.prototype.onDrop = function (loc, type) {
		};
		
		/**
		Updates the view when the model is changed
		
		@method update
		@param clientModel the ClientModel that has been updated
		*/
		MapController.prototype.update = function(clientModel) {
		
			var view = this.getView();
			var map = clientModel.map;
			
			// Lookup tables
			var edLookup = ["NW","N","NE","SE","S","SW"]; 			// From hexgrid.js
			var EdgeDirection = core.numberEnumeration(edLookup);	// From hexgrid.js
			var vdLookup = ["W","NW","NE","E","SE","SW"]; 			// From hexgrid.js
			var VertexDirection = core.numberEnumeration(vdLookup);	// From hexgrid.js
			var colorLookup = {};
			for (playerNum in clientModel.players) {
				colorLookup[playerNum] = clientModel.players[playerNum].color;
			}
			
			// Update the hexes
			var hexes = map.getHexes();
			for (lineNum in hexes) {
				var line = hexes[lineNum];
				for (hexNum in line) {
					var hex = line[hexNum];
					var loc = hex.getLocation();
					var type = hex.getType();
					console.log('MapController.update(): Hex: (' + loc.x + ',' + loc.y + ') ' + type);
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
					console.log('MapController.update(): Token: (' + tokenLoc.x + ',' + tokenLoc.y + ') ' + n);
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
				console.log('MapController.update(): Port: (' + portLoc.x + ',' + portLoc.y + ',' + portLoc.rotation + ') ' + type);
				view.addPort(portLoc, type, true);
			}
			
			// Update the robber
			var robber = map.getRobber();
			console.log('MapController.update(): Robber: (' + robber.x + ',' + robber.y + ')');
			view.placeRobber(robber, true);
			
			// Update the settlements, cities, and roads (iterate through all hexes)
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
								var color = colorLookup[edge.getOwnerID()];
								console.log('MapController.update(): Road: (' + edgeLoc.x + ',' + edgeLoc.y + ',' + edgeLoc.dir + ') ' + edge.getOwnerID() + ' ' + color);
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
								var color = colorLookup[vertex.getOwnerID()];
								// If the vertex worth is 1, it's a settlement
								if (vertex.getWorth() == 1) {
									console.log('MapController.update(): Settlement: (' + vertexLoc.x + ',' + vertexLoc.y + ',' + vertexLoc.dir + ') ' + vertex.getOwnerID() + ' ' + color);
									view.placeSettlement(vertexLoc, color, true);
								// If the vertex worth is 2, it's a city
								} else if (vertex.getWorth() == 2) {
									console.log('MapController.update(): City: (' + vertexLoc.x + ',' + vertexLoc.y + ',' + vertexLoc.dir + ') ' + vertex.getOwnerID() + ' ' + color);
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

