// ClientProxy.js
// Author: Spencer Bench

var catan = catan || {};
catan.models = catan.models || {};

catan.models.ClientProxy = (function() {

	/**
		The ClientProxy class is an intermediary between the server and the client.
		<pre>
		Domain:
			playerIndex: The ID of the player who controls this client, number
			modelVersion: The current version of the model
			
		Constructor Specification:
			PRE: playerIndex is an integer
		</pre>
		
		@class ClientProxy
		@constructor
		
		@param {number} playerIndex The ID of the player who controls this client
	*/
	function ClientProxy(playerIndex) {
		this.playerIndex = playerIndex;
		this.modelVersion = null;
	};
	ClientProxy.prototype.constructor = ClientProxy;
	
	/**
		Accepts/rejects a trade offer.
		
		@method acceptTrade
		@param {boolean} willAccept Whether or not the owning player accepts the trade
	*/
	ClientProxy.prototype.acceptTrade = function(willAccept) {
		// Create the data for the command
		var data = {};
		data.type = 'acceptTrade';
		data.playerIndex = this.playerIndex;
		data.willAccept = willAccept;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.ACCEPT_TRADE_URL, data);
		command.execute();
	};
	
	/**
		Builds a city.
		<pre>
		PRE: hex and vertex represent a valid location for the owning player to build a city
		</pre>
		
		@method buildCity
		@param {HexLocation} hex The hex to build the city on
		@param {Vertex} vertex The vertex of the hex to build the city on
		@param {boolean} free Whether or not the city is built for free
	*/
	ClientProxy.prototype.buildCity = function(hex, vertex, free) {
		// Create the data for the command
		var data = {};
		data.type = 'buildCity';
		data.playerIndex = this.playerIndex;
		data.vertexLocation = {};
		data.vertexLocation.x = hex.x;
		data.vertexLocation.y = hex.y;
		data.vertexLocation.direction = vertex.direction;
		data.free = free;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.BUILD_CITY_URL, data);
		command.execute();
	};
	
	/**
		Builds a road.
		<pre>
		PRE: hex and edge represent a valid location for the owning player to build a road
		</pre>
		
		@method buildRoad
		@param {HexLocation} hex The hex to build the road on
		@param {Edge} edge The edge of the hex to build the road on
		@param {boolean} free Whether or not the road is built for free
	*/
	ClientProxy.prototype.buildRoad = function(hex, edge, free) {
		// Create the data for the command
		var data = {};
		data.type = 'buildRoad';
		data.playerIndex = this.playerIndex;
		data.roadLocation = {};
		data.roadLocation.x = hex.x;
		data.roadLocation.y = hex.y;
		data.roadLocation.direction = edge.direction;
		data.free = free;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.BUILD_ROAD_URL, data);
		command.execute();
	};
	
	/**
		Builds a settlement.
		<pre>
		PRE: hex and vertex represent a valid location for the owning player to build a settlement
		</pre>
		
		@method buildSettlement
		@param {HexLocation} hex The hex to build the settlement on
		@param {Vertex} vertex The vertex of the hex to build the settlement on
		@param {boolean} free Whether or not the settlement is built for free
	*/
	ClientProxy.prototype.buildSettlement = function(hex, vertex, free) {
		// Create the data for the command
		var data = {};
		data.type = 'buildSettlement';
		data.playerIndex = this.playerIndex;
		data.vertexLocation = {};
		data.vertexLocation.x = hex.x;
		data.vertexLocation.y = hex.y;
		data.vertexLocation.direction = vertex.direction;
		data.free = free;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.BUILD_SETTLEMENT_URL, data);
		command.execute();
	};
	
	/**
		Buys a development card.
		<pre>
		PRE: The owning player has enough resources to buy a development card
		</pre>
		
		@method buyDevCard
	*/
	ClientProxy.prototype.buyDevCard = function() {
		// Create the data for the command
		var data = {};
		data.type = 'buyDevCard';
		data.playerIndex = this.playerIndex;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.BUY_DEV_CARD_URL, data);
		command.execute();
	};
	
	/**
		Discards resources from the owning player's hand.
		<pre>
		PRE: discardedCards represents a valid quantity of the owning player's resource cards
		</pre>
		
		@method discardCards
		@param {ResourceList} discardedCards A ResourceList representing the cards the owning player is to discard
	*/
	ClientProxy.prototype.discardCards = function(discardedCards) {
		// Create the data for the command
		var data = {};
		data.type = 'discardCards';
		data.playerIndex = this.playerIndex;
		data.discardedCards = discardedCards;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.DISCARD_CARDS_URL, data);
		command.execute();
	};
	
	/**
		Finishes the current player's turn.
		<pre>
		PRE: It is the owning player's turn
		</pre>
		
		@method finishTurn
	*/
	ClientProxy.prototype.finishTurn = function() {
		// Create the data for the command
		var data = {};
		data.type = 'finishTurn';
		data.playerIndex = this.playerIndex;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.FINISH_TURN_URL, data);
		command.execute();
	};
	
	/**
		Retrieves the game model from the server. Executes a callback function if the model version has changed.
		<pre>
		PRE: callback is a function which takes a single object as a parameter
		</pre>
		
		@method gameModel
		@param {function} callback Callback function executed if the model version has changed
	*/
	ClientProxy.prototype.gameModel = function(callback) {
		// Create and execute the command
		var url = '/game/model';
		if (this.modelVersion != null) {
			url += '?version=' + this.modelVersion;
		}
		var command = new catan.models.GetCommand(url);
		command.execute(function(data) {
			var model = data;
			if (model != null || model != true || model != 'true') { // It's not super clear which one the server returns if the model is up to date
				this.modelVersion = model.version;
				callback(model);
			}
		});
	};
	
	/**
		Executes a trade with the bank or with a port.
		<pre>
		PRE: ratio represents valid trade ratios
		PRE: inputResource and outputResource are valid resource names
		PRE: The owning player has enough of the required resources
		</pre>
		
		@method maritimeTrade
		@param {ResourceList} ratio The trade ratios expressed as integers (i.e. 3 = 3:1 ratio)
		@param {string} inputResource The type of resource the owning player is giving
		@param {string} outputResource The type of resource the owning player is receiving
	*/
	ClientProxy.prototype.maritimeTrade = function(ratio, inputResource, outputResource) {
		// Create the data for the command
		var data = {};
		data.type = 'maritimeTrade';
		data.playerIndex = this.playerIndex;
		data.ratio = ratio;
		data.inputResource = inputResource;
		data.outputResource = outputResource;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.MARITIME_TRADE_URL, data);
		command.execute();
	};
	
	/**
		Plays a Monopoly development card.
		<pre>
		PRE: resource is a valid resource name
		PRE: The owning player has a Monopoly card to play
		</pre>
		
		@method monopoly
		@param {string} resource The type of resource the owning player is monopolizing
	*/
	ClientProxy.prototype.monopoly = function(resource) {
		// Create the data for the command
		var data = {};
		data.type = 'Monopoly';
		data.playerIndex = this.playerIndex;
		data.resource = resource;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.MONOPOLY_URL, data);
		command.execute();
	};
	
	/**
		Plays a Monument development card.
		<pre>
		PRE: The owning player has a Monument card to play
		</pre>
		
		@method monument
	*/
	ClientProxy.prototype.monument = function() {
		// Create the data for the command
		var data = {};
		data.type = 'Monument';
		data.playerIndex = this.playerIndex;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.MONUMENT_URL, data);
		command.execute();
	};
	
	/**
		Offers a trade to another player.
		<pre>
		PRE: receiver is a valid player ID
		PRE: The owning player has enough resources to complete the trade if accepted
		</pre>
		
		@method offerTrade
		@param {number} receiver The player to extend the offer to
		@param {ResourceList} offer What the owning player gives (-) and receives (+)
	*/
	ClientProxy.prototype.offerTrade = function(receiver, offer) {
		// Create the data for the command
		var data = {};
		data.type = 'offerTrade';
		data.playerIndex = this.playerIndex;
		data.receiver = receiver;
		data.offer = offer;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.OFFER_TRADE_URL, data);
		command.execute();
	};
	
	/**
		Plays a Road Building development card.
		<pre>
		PRE: The owning player has a Road Building card to play
		PRE: The parameters represent valid locations for the owning player to build roads
		</pre>
		
		@method roadBuilding
		@param {HexLocation} hex1 The hex to build the first road on
		@param {Edge} edge1 The edge of the first hex to build the first road on
		@param {HexLocation} hex2 The hex to build the second road on
		@param {Edge} edge2 The edge of the second hex to build the second road on
	*/
	ClientProxy.prototype.roadBuilding = function(hex1, edge1, hex2, edge2) {
		// Create the data for the command
		var data = {};
		data.type = 'Road_Building';
		data.playerIndex = this.playerIndex;
		data.spot1 = {};
		data.spot1.x = hex1.x;
		data.spot1.y = hex1.y;
		data.spot1.direction = edge1.direction;
		data.spot2 = {};
		data.spot2.x = hex2.x;
		data.spot2.y = hex2.y;
		data.spot2.direction = edge2.direction;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.ROAD_BUILDING_URL, data);
		command.execute();
	};
	
	/**
		Robs an opponent when the owning player rolls a seven.
		<pre>
		PRE: The owning player has rolled a seven
		PRE: victimIndex is the ID of a player bordering the robberSpot
		PRE: robberSpot represents a valid location to move the robber to
		</pre>
		
		@method robPlayer
		@param {number} victimIndex The ID of the player to rob
		@param {HexLocation} robberSpot The hex to move the robber to
	*/
	ClientProxy.prototype.robPlayer = function(victimIndex, robberSpot) {
		// Create the data for the command
		var data = {};
		data.type = 'robPlayer';
		data.playerIndex = this.playerIndex;
		data.victimIndex = victimIndex;
		data.robberSpot = robberSpot;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.ROB_PLAYER_URL, data);
		command.execute();
	};
	
	/**
		Rolls the dice for the owning player.
		<pre>
		PRE: It is the beginning of the owning player's turn
		PRE: number is an integer in the range [2, 12]
		</pre>
		
		@method rollNumber
		@param {number} number The number the owning player rolled
	*/
	ClientProxy.prototype.rollNumber = function(number) {
		// Create the data for the command
		var data = {};
		data.type = 'rollNumber';
		data.playerIndex = this.playerIndex;
		data.number = number;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.ROLL_NUMBER_URL, data);
		command.execute();
	};
	
	/**
		Sends a chat message on behalf of the owning player.
		
		@method sendChat
		@param {string} content The content of the message
	*/
	ClientProxy.prototype.sendChat = function(content) {
		// Create the data for the command
		var data = {};
		data.type = 'sendChat';
		data.playerIndex = this.playerIndex;
		data.content = content;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.SEND_CHAT_URL, data);
		command.execute();
	};
	
	/**
		Plays a Soldier development card.
		<pre>
		PRE: The owning player has a Soldier card to play
		PRE: victimIndex is the ID of a player bordering the robberSpot
		PRE: robberSpot is a valid hex for the robber to move to
		</pre>
		
		@method soldier
		@param {number} victimIndex The ID of the player to rob
		@param {HexLocation} robberSpot the hex to move the robber to
	*/
	ClientProxy.prototype.soldier = function(victimIndex, robberSpot) {
		// Create the data for the command
		var data = {};
		data.type = 'Soldier';
		data.playerIndex = this.playerIndex;
		data.victimIndex = victimIndex;
		data.robberSpot = robberSpot;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.SOLDIER_URL, data);
		command.execute();
	};
	
	/**
		Plays a Year Of Plenty development card.
		<pre>
		PRE: The owning player has a Year Of Plenty development card to play
		PRE: resource1 and resource2 are valid resource names
		</pre>
		
		@method yearOfPlenty
		@param {string} resource1 The first resource the owning player wants to obtain
		@param {string} resource2 The second resource the owning player wants to obtain
	*/
	ClientProxy.prototype.yearOfPlenty = function(resource1, resource2) {
		// Create the data for the command
		var data = {};
		data.type = 'Year_of_Plenty';
		data.playerIndex = this.playerIndex;
		data.resource1 = resource1;
		data.resource2 = resource2;
		// Create and execute the command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.YEAR_OF_PLENTY_URL, data);
		command.execute();
	};
	
	return ClientProxy;
	
})();
