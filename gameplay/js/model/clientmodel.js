//STUDENT-EDITABLE-BEGIN
/**
	This module contains the top-level client model class
	
	@module		catan.models
	@namespace models
*/

//

var catan = catan || {};
catan.models = catan.models || {};


catan.models.ClientModel  = (function clientModelNameSpace(){
    /** 
	This the top-level client model class that contains the local player, map contents, etc.
	
	@class ClientModel
	@constructor
	@param {integer} playerID The id of the local player, extracted from the cookie
    */
	var ClientModel = (function ClientModelClass(){
        
		function ClientModel(playerID){
			console.log('CLIENT MODEL CONSTRUCTOR. playerID: ' + playerID);
			this.playerID 		= playerID;
			this.clientProxy 	= new catan.models.ClientProxy(playerID, this);
			this.map 			= new catan.models.Map(4);
			this.players 		= new Array();
			this.turnTracker 	= new catan.models.TurnTracker(playerID);
			this.bank			= {};
			this.deck 			= {};
		}     
		ClientModel.prototype.constructor = ClientModel; 
        
        /**
         * This is called to fetch the game state from the server the very first time.
         * It should: 1) fetch the game state JSON from the server, 2) update the client model with the
         * returned data, 3) notify all client model listeners that the model has changed, and 4) invoke
         * the success callback function with the object received from the server.
         * 
         * @method initFromServer
         * @param {function} success - A callback function that is called after the game state has been fetched from the server and the client model updated. This function is passed a single parameter which is the game state object received from the server.
         * */
		ClientModel.prototype.initFromServer = function(success){

			var myself = this;
            // TODO: 1) fetch the game state from the server, 2) update the client model, 3) call the "success" function.
			this.clientProxy.gameModel(function (error, model) {
				if (error) {
					console.log('Error Info: ', model);
					alert ('clientProxy returned error');
				}
				else {
					myself.updateModel(model, myself);
					success();
				}
			});
		}

		ClientModel.prototype.updateModel = function(model, myself) {
			console.log(model);
			myself.bank = model.bank;
			myself.deck = model.deck;

			//TODO finish the map class
			//myself.map.update(model.map);
			//myself.turnTracker.update(model.turnTracker);

			var playersList = {};
			for (p in model.players) {
				var temp = new catan.models.Player();
				temp.update(model.players[p]);

				playersList[p] = temp;
			}
			myself.players = playersList;
			myself.clientPlayer = myself.players[0];
			console.log(myself.players);

		}

		/**
			<pre>
				PRE: The settlement location is open
				PRE: The settlement location is not on water
				PRE: The settlement location is connected to one of client's roads
				PRE: The client has the necessary resources (1 wood, 1 brick, 1 road)
				POST: Client loses the resources to play the road
				POST: Map lists the road correctly
			</pre>
			@method buildRoad
			@param {boolean} free Whether or not client gets this piece for free (i.e. setup)
			@param {} 
		*/
		ClientModel.prototype.buildRoad = function(free, hex, direction) {

			if (!this.clientPlayer.canAffordRoad())
				console.log("You can't afford a road!");
			// TODO: Finish the conditions (The conditions should be easy to check
			// after learning how to reference a single edge or single vertex - 
			// Parameters will have to be modified to reference the map -> hex -> edge)
		}

		/**
			<pre>
				PRE: The settlement location is open
				PRE: The settlement location is not on water
				PRE: The settlement location is connected to one of your roads
				PRE: You have the resources (1 wood, 1 brick, 1 sheep, 1 settlement)
				POST: The resources required to build the settlement are expanded
				POST: The map lists the settlement correctly
			</pre>
			@method buildSettlement
			@param {boolean} free Whether or not the client gets this piece for free (i.e. setup)
		*/
		ClientModel.prototype.buildSettlement = function(free) {

			if (!this.clientPlayer.canAffordSettlement())
				console.log("You can't afford a settlement!");
			// TODO: Finish checking conditions
		}

		/**
			<pre>
				PRE: The city location is where you currently have a settlement
				PRE: You have the resources (2 wheat, 3 ore)
				POST: You expend the resources to build the city
				POST: You get a settlement back
				POST: Map displays the city correctly
			</pre>
			@method buildcity
		*/
		ClientModel.prototype.buildCity = function() {

			if (!this.clientPlayer.canAffordCity())
				console.log("You can't afford a city!");
			// TODO: Finish Checking Conditions
		}

		ClientModel.prototype.robPlayer = function(playerIndex, victimIndex, robberPoint) {

			//TODO: Implement
		}

		/**
		    <pre>
		        PRE: You have the necessary resources (1 ore, sheep, and wheat)
		        PRE: There are still available Dev cards (in the deck)
		        POST: Person now has the dev card (in the dev card hand)
		    </pre>
		    @method buyDevCard
		*/
		ClientModel.prototype.canbuyDevCard = function () {

			var deckCardCount = 0;
			for (card in this.deck) {
				deckCardCount += this.deck[card];
			}
			
			return (this.clientPlayer.canAffordDevCard() && deckCardCount > 0);
		}

		/**
		    <pre>
		        POST: Person now has the dev card (in the dev card hand)
		    </pre>
		    @method buyDevCard
		*/
		ClientModel.prototype.buyDevCard = function () {
			var myself = this;
			if (canBuyDevCard()) {
				this.clientProxy.buyDevCard(function () {
					myself.updateModel;
				});
			}
		}

		/**
		    <pre>
				PRE: Client has the specific card they want to play in their "oldDevCard" list
		    	PRE: Client hasn't played a dev card yet this turn
		    	PRE: It's the client's turn
		    	PRE: The client model status is "Playing"

		        PRE: Client's two specified resources are in the bank
		        POST: Person now has the two specified resources
		    </pre>
		    @method yearOfPlenty
		*/
		ClientModel.prototype.yearOfPlenty = function (resource1, resource2) {

			// All of the potentially failed pre-conditions
			if (!this.turnTracker.isMyTurn() || !this.turnTracker.statusEquals("Playing"))
			{
				console.log("ERROR: Isn't player's turn -OR- isn't \"Playing\"");
				return;
			} 
			if (this.clientPlayer.playedDevCard || this.clientPlayer.oldDevCards.yearOfPlenty < 1)
			{
				console.log("ERROR: Player has already played a dev card this turn -OR- they do not have a YearOfPlenty card");
				return;
			}
			if (this.bank[resource1] < 1 || this.bank[resource2] < 1 || 
				(resource1 == resource2 && this.bank[resource1] < 2))
			{
				console.log("ERROR: Bank does not have one or both of the desired resources");
				return;
			}

			// Success!
			var myself = this;
			this.clientProxy.yearOfPlenty(resource1, resource2, function () {
					myself.updateModel;
			});
		}

		/**
		    <pre>
				PRE: Client has the specific card they want to play in their "oldDevCard" list
		    	PRE: Client hasn't played a dev card yet this turn
		    	PRE: It's the client's turn
		    	PRE: The client model status is "Playing"

		        PRE: First road location is connected to one of the client's roads
		        PRE: Second road location is connected either to one of the client's roads or to the first road location
		        PRE: Neither road location is on water
		        PRE: Person has two roads
		        POST: Person uses two roads
		        POST: The map registered the road successfully
		    </pre>
		    @method roadBuilding
			@param {HexLocation} hex1 The hex to build the first road on
			@param {Edge} edge1 The edge of the first hex to build the first road on
			@param {HexLocation} hex2 The hex to build the second road on
			@param {Edge} edge2 The edge of the second hex to build the second road on
		*/

		ClientModel.prototype.roadBuilding = function (hex1, edge1, hex2, edge2) {

			// All of the potentially failed pre-conditions
			if (!this.turnTracker.isMyTurn() || !this.turnTracker.statusEquals("Playing"))
			{
				console.log("ERROR: Isn't player's turn -OR- isn't \"Playing\"");
				return;
			} 
			if (this.clientPlayer.playedDevCard || this.clientPlayer.oldDevCards.roadBuilding < 1)
			{
				console.log("ERROR: Player has already played a dev card this turn -OR- they do not have a roadBuilding card");
				return;
			}
			if (this.clientPlayer.roads < 2)
			{
				console.log("ERROR: Player does not have 2 roads to place")
				return;
			}

			// TODO: check if the road locations are valid (something should be implemented within Map)

			// Success!
			var myself = this;
			this.clientProxy.roadBuilding(hex1, edge1, hex2, edge2, function () {
					myself.updateModel;
				});
		}

		/**
		    <pre>
		    	PRE: Client has the specific card they want to play in their "oldDevCard" list
		    	PRE: Client hasn't played a dev card yet this turn
		    	PRE: It's the client's turn
		    	PRE: The client model status is "Playing"
		        PRE: Robber has moved
		        PRE: The victim has cards (or -1 if can't rob anyone)
		        POST: Robber is in a new location
		        POST: The victim gives one random resource card to the soldier
		    </pre>
		    @method soldier
		    @param HexLocation robberSpot, location of the robber
		    @param PlayerIndex victimIndex, index of the player being robbed
		*/
		ClientModel.prototype.soldier = function (robberSpot, victimID) {

			// All of the potentially failed pre-conditions
			if (!this.turnTracker.isMyTurn() || !this.turnTracker.statusEquals("Playing"))
			{
				console.log("ERROR: Isn't player's turn -OR- isn't \"Playing\"");
				return;
			} 
			if (this.clientPlayer.playedDevCard || this.clientPlayer.oldDevCards.soldier < 1)
			{
				console.log("ERROR: Player has already played a dev card this turn -OR- they do not	have a soldier card");
				return;
			}
			if (victimID != -1 && this.players[victimID].resources.getCardCount() < 1)
			{
				console.log("ERROR: Player to rob has no cards!")
				return;
			}

			// Success!
			var myself = this;
			this.clientProxy.soldier(victimID, robberSpot, function () {
					myself.updateModel;
				});
		}

		/**
		    <pre>
		    	PRE: Client has the specific card they want to play in their "oldDevCard" list
		    	PRE: Client hasn't played a dev card yet this turn
		    	PRE: It's the client's turn
		    	PRE: The client model status is "Playing"
		        POST: All other players lose the resource type that's chosen
		        POST: The player of the card gets an equal number
		    </pre>
		    @method monopoly
		*/
		ClientModel.prototype.monopoly = function () {
			
			// All of the potentially failed pre-conditions
			if (!this.turnTracker.isMyTurn() || !this.turnTracker.statusEquals("Playing"))
			{
				console.log("ERROR: Isn't player's turn -OR- isn't \"Playing\"");
				return;
			} 
			if (this.clientPlayer.playedDevCard || this.clientPlayer.oldDevCards.monopoly < 1)
			{
				console.log("ERROR: Player has already played a dev card this turn -OR- they do not	have a monopoly card");
				return;
			}
			
			// Success!
			var myself = this;
			this.clientProxy.monopoly(function () {
					myself.updateModel;
				});
		}

		/**
		    <pre>
		        PRE: Client has the specific card they want to play in EITHER DevCardList (This is an exception!)
		        PRE: Client hasn't played a dev card yet this turn
		    	PRE: It's the client's turn
		    	PRE: The client model status is "Playing"
		        POST: You gain one victory point
		    </pre>
		    @method monument
		*/
		ClientModel.prototype.monument = function () {

			// All of the potentially failed pre-conditions
			if (!this.turnTracker.isMyTurn() || !this.turnTracker.statusEquals("Playing"))
			{
				console.log("ERROR: Isn't player's turn -OR- isn't \"Playing\"");
				return;
			} 
			if (this.clientPlayer.playedDevCard ||
				(this.clientPlayer.oldDevCards.monument < 1 && this.clientPlayer.newDevCards.monument < 1))
			{
				console.log("ERROR: Player has already played a dev card this turn -OR- they do not	have a monopoly card");
				return;
			}

			// Success!
			var myself = this;
			this.clientProxy.monument(function () {
					myself.updateModel;
				});
		}

		/**
		    <pre>
		        PRE: Person has the necessary resources
		        POST: Trade if offered to the other player (in their model)
		    </pre>
		    @method offerTrade
		    @param ResourceList offer, pos numbers are traded away, negative numbers are received
		    @param PlayerIndex receiver, The recipient of the trade
		*/
		ClientModel.prototype.canOfferTrade = function (offer, receiver) {

		}

		ClientModel.prototype.offerTrade = function (offer, receiver) {
			if (canOfferTrade()) {
				this.clientProxy.offerTrade(offer, receiver);
			}
		}

		/**
		    <pre>
		        PRE: You were offered the trade
		        PRE: You have the resources necessary to accept
		        POST: If Accept, the resources are swapped
		        POST: If Declined, the resources are unchanged
		        POST: The trade offer is removed (either way)
		    </pre>
		    @method acceptTrade
		    @param boolean willAccept, whether or not the player will accept the offer
		*/
		ClientModel.prototype.canAcceptTrade = function () {

		}

		ClientModel.prototype.acceptTrade = function () {
			if (canAcceptTrade()) {
				var myself = this;
				this.clientProxy.acceptTrade(function () {
					myself.updateModel;
				});
			}
		}

		/**
		    <pre>
		        PRE: Player's current state is "Discarding"
		        PRE: Player has more than 7 cards
		        PRE: Player has all the cards that they are choosing to discard
		        POST: If player is last one to discard, client's model status is now "Robbing"
		        POST: Player lost the resources that they discarded
		    </pre>
		    @method discardCards
		    @param ResourceHand discardedCards, The cards being discarded
		*/
		ClientModel.prototype.canDiscardCards = function (discardedCards) {

			if (this.turnTracker.isMyTurn() && this.turnTracker.statusEquals("Discarding")
				&& this.clientPlayer.resources.getCardCount() > 7
				&& this.clientPlayer.hasResources(discardedCards)) {

				return true;
			}
			else { return false; }

		}

		/**
		    <pre>
		        POST: If player is last one to discard, client's model status is now "Robbing"
		        POST: Player lost the resources that they discarded
		    </pre>
		    @method discardCards
		    @param ResourceHand discardedCards, The cards being discarded
		*/
		ClientModel.prototype.discardCards = function (discardedCards) {

			if (canDiscardCards()) {
				var myself = this;
				this.clientProxy.discardCards(discardCards, function () {
					myself.updateModel;
				});
			}
		}
        
		return ClientModel;
	}());	
	
	return ClientModel;
}());

