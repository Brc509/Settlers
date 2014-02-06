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
			
		Constructor Specification:
			PRE: playerIndex is an integer
		</pre>
		
		@class ClientProxy
		@constructor
		
		@param {number} playerIndex The ID of the player who controls this client
	*/
	function ClientProxy(playerIndex) {
		this.playerIndex = playerIndex;
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
		// Create and execute the template command
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
		@param {boolean} free Whether or not the city is placed for free
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
		// Create and execute the template command
		var command = new catan.models.MovesCommand(catan.models.MovesCommand.BUILD_CITY_URL, data);
		command.execute();
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.buildRoad = function(hex, edge, free) {
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.buildSettlement = function(hex, vertex, free) {
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.buyDevCard = function() {
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.discardCards = function(discardedCards) {
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.finishTurn = function() {
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.gameModel = function(success) {
		//Skyler added this just to get the clientModel up and running.
		//TODO refactor to use the command class.
		$.get('/game/model', function(model){
			success(model);
		});
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.maritimeTrade = function(ratio, inputResource, outputResource) {
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.monopoly = function(resource) {
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.monument = function() {
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.offerTrade = function(receiver, offer) {
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.roadBuilding = function(hex1, edge1, hex2, edge2) {
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.robPlayer = function(victimIndex, robberSpot) {
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.rollNumber = function(number) {
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.sendChat = function(content) {
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.soldier = function(victimIndex, robberSpot) {
	};
	
	/**
		FUNCTION DESCRIPTION
		<pre>
		PRE: DESCRIPTION
		POST: DESCRIPTION
		</pre>
		
		@method buildCity
		@param {TYPE} PARAMNAME DESCRIPTION
		@return {TYPE} DESCRIPTION
	*/
	ClientProxy.prototype.yearOfPlenty = function(resource1, resource2) {
	};
	
	return ClientProxy;
	
})();
