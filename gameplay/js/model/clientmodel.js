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

		var myself;
        
		function ClientModel(playerID){
			myself = this;

			var cookies 		= document.cookie.split(';');
			if(cookies){
				var userCookie = unescape(cookies[0]);
				userCookie = userCookie.split('=');
				var jsonPlayerCookie = JSON.parse(userCookie[1]);
				this.playerID = jsonPlayerCookie.playerID;
			}//JSON.parse(decodeURIComponent(document.cookie.get("catan.user"))).playerID;//JSON.parse(decodeURIComponent(Cookies.get('catan.user'))).playerID; // Get the player ID from the cookie
			this.clientProxy 	= new catan.models.ClientProxy(this);
			this.command		= new catan.models.Command(this.clientProxy, this);
			this.map 			= new catan.models.Map(4);
			this.players 		= new Array();
			this.turnTracker 	= new catan.models.TurnTracker();
			this.bank			= {};
			this.deck 			= {};
			this.chat 			= {};
			this.observers		= new Array();
			this.isModalUp      = false;

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
            
			myself.command.execute('gameModel', function(error, model) {
				if (error) {alert ('ERROR communicating with server: ' + model.statusText);}
				else {
					myself.updateModel(error, model);
					if (success) success();
				}
			});
		}

		ClientModel.prototype.updateModel = function(error, model) {
			if (error) {
				console.log('ERROR: ' + model.statusText);
				alert ('ERROR: ' + model.statusText);
			} else {
				//console.log(model);
				myself.bank = new catan.models.ResourceList();
				myself.deck = new catan.models.DevCardList();				
				myself.bank.update(model.bank);
				myself.deck.update(model.deck);

				myself.chat = model.chat;
				myself.log = model.log;
				myself.model = model;
				myself.turnTracker = model.turnTracker;
				myself.map.update(model.map);
				
				var playersList = {};
				for (p in model.players) {
					var temp = new catan.models.Player();
					temp.update(model.players[p]);

					playersList[p] = temp;
				}
				myself.players = playersList;
				
				// Find the index of the controlling player
				for (n in myself.players) {
					var player = myself.players[n];
					if (player.playerID == myself.playerID) {
						myself.playerIndex = parseInt(n);
					}
				}

				myself.clientPlayer = myself.players[myself.playerIndex];
				myself.notifyObservers();
			}
		}
		
		ClientModel.prototype.notifyObservers = function() {
			//console.log('notifying observers');
			for (o in myself.observers) {
				if (myself.observers[o].update){
					myself.observers[o].update(myself);
				}
			}
		};

		ClientModel.prototype.addObserver = function (controller) {
			this.observers.push(controller);
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
		ClientModel.prototype.buildRoad = function(edgeLoc, free) {

			this.command.execute('buildRoad', edgeLoc, free);
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
		ClientModel.prototype.buildSettlement = function(vertexLoc, free) {

			this.command.execute('buildSettlement', vertexLoc, free);
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
		ClientModel.prototype.buildCity = function(vertexLoc, free) {

			this.command.execute('buildCity', vertexLoc, free);
		}

		/**
			Send a request to the server to rob the player
		*/
		ClientModel.prototype.robPlayer = function(orderID, robberSpot) {

			this.command.execute('robPlayer', orderID, robberSpot);
		}

		/**
		    <pre>
		        PRE: You have the necessary resources (1 ore, sheep, and wheat)
		        PRE: There are still available Dev cards (in the deck)
		        POST: Person now has the dev card (in the dev card hand)
		    </pre>
		    @method buyDevCard
		*/
		ClientModel.prototype.canBuyDevCard = function () {

			return (this.clientPlayer.canAffordDevCard() && this.deck.hasAnyCard());
		}

		/**
		    <pre>
		        POST: Person now has the dev card (in the dev card hand)
		    </pre>
		    @method buyDevCard
		*/
		ClientModel.prototype.buyDevCard = function () {

			this.command.execute('buyDevCard');
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

			this.command.execute('yearOfPlenty', resource1, resource2);
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

		ClientModel.prototype.roadBuilding = function (roadBuildingLoc1, roadBuildingLoc2) {

			this.command.execute('roadBuilding', roadBuildingLoc1, roadBuildingLoc2);
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

			this.command.execute('soldier', victimID, robberSpot);
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
		ClientModel.prototype.monopoly = function (resource) {
			
			this.command.execute('monopoly', resource);
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

			this.command.execute('monument');
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
		ClientModel.prototype.offerTrade = function (receiver, offer) {

			this.command.execute('offerTrade', receiver, offer);
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
		ClientModel.prototype.acceptTrade = function (willAccept) {

			this.command.execute('acceptTrade',willAccept, this.updateModel);
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

			if (this.turnTracker.currentTurn == this.playerIndex && this.turnTracker.status == "Discarding"
				&& this.clientPlayer.resources.getCardCount() > 7
				&& this.clientPlayer.hasResources(discardedCards)) {

				return true;
			}
			else { return false; }
		}

		/**
			Trades resources between the client and the clientModel's bank
		*/
		ClientModel.prototype.maritimeTrade = function (ratio, input, output) {

			this.command.execute('maritimeTrade', ratio, input, output);
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

			this.command.execute('discardCards', discardedCards);
		}

		//	ClientProxy.prototype.sendChat = function(content, callback) {
		ClientModel.prototype.sendChat = function(lineContents) {

			this.command.execute('sendChat', lineContents);
		}

		ClientModel.prototype.rollNumber = function(value) {

			this.command.execute('rollNumber', value);
		}

		ClientModel.prototype.finishTurn = function(){

			this.command.execute('finishTurn');
		}

		ClientModel.prototype.isMyTurn = function() {

			return (this.turnTracker.currentTurn == this.playerIndex);
		}
        
		return ClientModel;
	}());	
	
	return ClientModel;
}());

