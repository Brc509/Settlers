// Command.js
// Author: Spencer Bench

var catan = catan || {};
catan.models = catan.models || {};

catan.models.Command = (function() {

	// Lookup tables
	var edLookup = ["NW","N","NE","SE","S","SW"]; 			// From hexgrid.js
	var EdgeDirection = core.numberEnumeration(edLookup);	// From hexgrid.js
	var vdLookup = ["W","NW","NE","E","SE","SW"]; 			// From hexgrid.js
	var VertexDirection = core.numberEnumeration(vdLookup);	// From hexgrid.js

	var myself;

	/**
		The Command class represents any command which modifies the state of the game and returns the game model.
		<pre>
		Domain:
			data: The object to send in the body of the request, object
			
		Constructor Specification:
			PRE: url is a valid server endpoint
		</pre>
		
		@class Command
		@extends Command
		@constructor
		
		@param {string} url The server endpoint that the command is sent to
		@param {object} data The data to send to the server
	*/
	function Command(clientProxy, clientModel) {
		myself = this;
		this.url;
		this.clientProxy = clientProxy;
		this.clientModel = clientModel;
		this.revision = -1;
	}

	Command.prototype.constructor = Command;
	
	Command.prototype.execute = function(name) {
		return Command.prototype[name] && Command.prototype[name].apply( myself, [].slice.call(arguments, 1));
	}

	/**
	Retrieves the game model from the server. Updates the client model if the version has changed.
	
	@method gameModel
	@param function callback  this is the function passed in by the clientModel
	*/
	Command.prototype.gameModel = function (callback){
		var url = '/game/model';
		if (this.revision != -1){
			url += '?revision=' + myself.revision;
		}
		this.clientProxy.url = url;
		this.clientProxy.get(function(error, data) {
			if (error) {
				callback(true, data);
			} else {
				if (data != 'true') {
					console.log('ClientProxy.gameModel(): Received new model revision.');
					myself.revision = data.revision;
					callback(false, data);
				}
				else { console.log('ClientProxy.gameModel(): Already up to date'); }
			}
		});
	}

	/**
		Sends a chat message on behalf of the owning player.
		
		@method sendChat
		@param {string} content The content of the message
	*/
	Command.prototype.sendChat = function(content) {
		// Create the data for the command
		var data = {};
		data.type = 'sendChat';
		data.playerIndex = this.clientModel.playerIndex;
		data.content = content;
		// Create and execute the command
		this.clientProxy.url 	= '/moves/sendChat';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
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
	Command.prototype.rollNumber = function(number) {
		// Create the data for the command
		var data = {};
		data.type = 'rollNumber';
		data.playerIndex = this.clientModel.playerIndex;
		data.number = number;
		// Create and execute the command
		this.clientProxy.url 	= '/moves/rollNumber';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
	};

	/**
		Finishes the current player's turn.
		<pre>
		PRE: It is the owning player's turn
		</pre>
		
		@method finishTurn
	*/
	Command.prototype.finishTurn = function() {
		// Create the data for the command
		var data = {};
		data.type = 'finishTurn';
		data.playerIndex = this.clientModel.playerIndex;
		// Create and execute the command
		this.clientProxy.url 	= '/moves/finishTurn';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
	};

	Command.prototype.reset = function() {
		// Create the data for the command
		var data = {};
		data.type = 'reset';
		data.playerIndex = this.clientModel.playerIndex;
		// Create and execute the command
		this.clientProxy.url 	= '/game/reset';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
	};
	/**
		Accepts/rejects a trade offer.
		
		@method acceptTrade
		@param {boolean} willAccept Whether or not the owning player accepts the trade
	*/
	Command.prototype.acceptTrade = function(willAccept) {
		// Create the data for the command
		var data = {};
		data.type = 'acceptTrade';
		data.playerIndex = this.clientModel.playerIndex;
		data.willAccept = willAccept;
		// Create and execute the command
		this.clientProxy.url 	= '/moves/acceptTrade';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
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
	Command.prototype.offerTrade = function(receiver, offer) {
		// Create the data for the command
		var data = {};
		data.type = 'offerTrade';
		data.playerIndex = this.clientModel.playerIndex;
		data.receiver = receiver;
		data.offer = offer;
		// Create and execute the command
		this.clientProxy.url 	= '/moves/offerTrade';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
	};

	/**
		Executes a trade with the bank or with a port.
		<pre>
		PRE: ratio represents valid trade ratios
		PRE: inputResource and outputResource are valid resource names
		PRE: The owning player has enough of the required resources
		</pre>
		
		@method maritimeTrade
		@param {integer} ratio The trade ratios expressed as integers (i.e. 3 = 3:1 ratio)
		@param {string} inputResource The type of resource the owning player is giving
		@param {string} outputResource The type of resource the owning player is receiving
	*/
	Command.prototype.maritimeTrade = function(ratio, inputResource, outputResource) {
		// Create the data for the command
		var data = {};
		data.type = 'maritimeTrade';
		data.playerIndex = this.clientModel.playerIndex;
		data.ratio = ratio;
		data.inputResource = inputResource;
		data.outputResource = outputResource;
		// Create and execute the command
		this.clientProxy.url 	= '/moves/maritimeTrade';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
	};

	/**
		Builds a road.
		<pre>
		PRE: hex and edge represent a valid location for the owning player to build a road
		</pre>
		
		@method buildRoad
		@param {EdgeLocation} edgeLocation The edge location to build the road on
		@param {boolean} free Whether or not the road is built for free
	*/
	Command.prototype.buildRoad = function(edgeLocation, free) {
		// Create the data for the command
		var data = {};
		data.type = 'buildRoad';
		data.playerIndex = myself.clientModel.playerIndex;
		data.roadLocation = edgeLocation;
		data.roadLocation.direction = edLookup[edgeLocation.direction];
		data.free = free;
		// Create and execute the command
		this.clientProxy.url 	= '/moves/buildRoad';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
	};

	/**
		Builds a city.
		<pre>
		PRE: hex and vertex represent a valid location for the owning player to build a city
		</pre>
		
		@method buildCity
		@param {VertexLocation} vertexLocation The vertex location to build the city on
		@param {boolean} free Whether or not the city is built for free
	*/
	Command.prototype.buildCity = function(vertexLocation, free) {
		// Create the data for the command
		var data = {};
		data.type = 'buildCity';
		data.playerIndex = this.clientModel.playerIndex;
		data.vertexLocation = vertexLocation;
		data.vertexLocation.direction = vertexLocation.direction;
		data.free = free;
		// Create and execute the command
		this.clientProxy.url 	= '/moves/buildCity';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
	};

	/**
		Builds a settlement.
		<pre>
		PRE: hex and vertex represent a valid location for the owning player to build a settlement
		</pre>
		
		@method buildSettlement
		@param {VertexLocation} vertexLocation The vertex location to build the settlement on
		@param {boolean} free Whether or not the settlement is built for free
	*/
	Command.prototype.buildSettlement = function(vertexLocation, free) {
		// Create the data for the command
		var data = {};
		data.type = 'buildSettlement';
		data.playerIndex = this.clientModel.playerIndex;
		data.vertexLocation = vertexLocation;
		data.vertexLocation.direction = vertexLocation.direction;
		data.free = free;
		// Create and execute the command
		this.clientProxy.url 	= '/moves/buildSettlement';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
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
	Command.prototype.robPlayer = function(victimIndex, robberSpot) {
		// Create the data for the command
		var data = {};
		data.type = 'robPlayer';
		data.playerIndex = this.clientModel.playerIndex;
		data.victimIndex = victimIndex;
		data.location = robberSpot;
		// Create and execute the command
		this.clientProxy.url 	= '/moves/robPlayer';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
	};

	/**
		Buys a development card.
		<pre>
		PRE: The owning player has enough resources to buy a development card
		</pre>
		
		@method buyDevCard
	*/
	Command.prototype.buyDevCard = function() {
		// Create the data for the command
		var data = {};
		data.type = 'buyDevCard';
		data.playerIndex = this.clientModel.playerIndex;
		// Create and execute the command
		this.clientProxy.url 	= '/moves/buyDevCard';
		this.clientProxy.data 	= data;

		this.clientProxy.post();
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
	Command.prototype.soldier = function(victimIndex, robberSpot) {
		// Create the data for the
		var data = {};
		data.type = 'Soldier';
		data.playerIndex = this.clientModel.playerIndex;
		data.victimIndex = victimIndex;
		data.location = robberSpot;
		// Create and execute the command
		this.clientProxy.url 	= '/moves/Soldier';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
	};

	/**
		Plays a Monument development card.
		<pre>
		PRE: The owning player has a Monument card to play
		</pre>
		
		@method monument
	*/
	Command.prototype.monument = function() {
		// Create the data for the command
		var data = {};
		data.type = 'Monument';
		data.playerIndex = this.clientModel.playerIndex;
		// Create and execute the command
		this.clientProxy.url 	= '/moves/Monument';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
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
	Command.prototype.yearOfPlenty = function(resource1, resource2) {
		// Create the data for the command
		var data = {};
		data.type = 'Year_of_Plenty';
		data.playerIndex = this.clientModel.playerIndex;
		data.resource1 = resource1;
		data.resource2 = resource2;
		// Create and execute the command
		this.clientProxy.url 	= '/moves/Year_of_Plenty';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
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
	Command.prototype.monopoly = function(resource) {
		// Create the data for the command
		var data = {};
		data.type = 'Monopoly';
		data.playerIndex = this.clientModel.playerIndex;
		data.resource = resource;
		// Create and execute the command
		this.clientProxy.url 	= '/moves/Monopoly';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
	};

	/**
		Plays a Road Building development card.
		<pre>
		PRE: The owning player has a Road Building card to play
		PRE: The parameters represent valid locations for the owning player to build roads
		</pre>
		
		@method roadBuilding
		@param {EdgeLocation} edgeLocation1 The edge location to build the first road on
		@param {EdgeLocation} edgeLocation2 The edge location to build the second road on
	*/
	Command.prototype.roadBuilding = function(edgeLocation1, edgeLocation2) {
		// Create the data for the command
		var data = {};
		data.type = 'Road_Building';
		data.playerIndex = this.clientModel.playerIndex;
		data.spot1 = edgeLocation1;
		data.spot1.direction = edLookup[edgeLocation1.direction];
		data.spot2 = edgeLocation2;
		data.spot2.direction = edLookup[edgeLocation2.direction];
		// Create and execute the command
		this.clientProxy.url 	= '/moves/Road_Building';
		this.clientProxy.data 	= data;
		this.clientProxy.post();
	};

	return Command;
	
})();
