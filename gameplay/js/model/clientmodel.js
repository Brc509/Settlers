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
		}      
        
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
            
            // TODO: 1) fetch the game state from the server, 2) update the client model, 3) call the "success" function.


			var login = $.post("/user/login", { username: "Sam", password : "sam"}, function(data) {
				var loginData = data;
					
					
					var join = $.post("games/join", { color: "red", id : "0"}, function(data) {
						var joinData = data;
						
							
							var gameState = $.get("/game/model", function(data){
								
								var gameStateData = data;
								var map = data["map"];
								var players = data["players"];

								player = new Object();

								player.name = players[0].name;
								player.resources = players[0].resources;
								player.oldDevCards = players[0].oldDevCards;
								player.newDevCards = players[0].newDevCards;
								player.roads = players[0].roads;
								player.cities = players[0].cities;
								player.settlements = players[0].settlements;
								player.soldiers = players[0].soldiers;
								player.monuments = players[0].monuments;
								player.playerID = players[0].playerID;
								player.orderNumber = players[0].orderNumber;
								player.color = players[0].red;

								turnTracker = gameStateData.turnTracker;

								devCards = gameStateData.deck;

								var cm = new catan.models.ClientModel(0);
								cm.buyDevCard();

								//Map m = new Map(map);
								//for players 
								//Player p = new Player(players[i])
			
					});

				});

			});
			


            // success();
		}

		ClientModel.prototype.robPlayer = function(playerIndex, victimIndex, robberPoint){
			//robbing a player
		}


		/**
		    <pre>
		        PRE: You have the necessary resources (1 ore, sheep, and wheat)
		        PRE: There are still available Dev cards (in the deck)
		        POST: Person now has the dev card (in the dev card hand)
		    </pre>
		    @method buyDevCard
		*/
		ClientModel.prototype.buyDevCard = function () {
			
			var resources = player.resources;
			var sheepNum = resources["sheep"];
			var oreNum = resources["ore"];
			var wheatNum = resources["wheat"];
			var devCards = devCards;
			
			if(sheepNum > 0 && oreNum > 0 && wheatNum > 0){
				// player.devCard ++;
				// devCard --;
				Proxy.buyDevCard(player);
				alert("bought a card");
			}
		}

		/**
		    <pre>
		        PRE: Client's turn and status is "Playing"
		        PRE: Person hasn't played a dev card yet this turn
		        PRE: Client's two specified resources are in the bank
		        POST: Person now has the two specified resources
		    </pre>
		    @method yearOfPlenty
		*/
		ClientModel.prototype.yearOfPlenty = function (resource1, resource2) {

		}

		/**
		    <pre>
		        PRE: Client's turn and status is "Playing"
		        PRE: Person hasn't played a dev card yet this turn
		        PRE: First road location is connected to one of the client's roads
		        PRE: Second road location is connected either to one of the client's roads or to the first road location
		        PRE: Neither road location is on water
		        PRE: Person has two roads
		        POST: Person uses two roads
		        POST: The map registered the road successfully
		    </pre>
		    @method roadBuilding
		    @param (EdgeLocation) spot1, The first of the new road locations
		    @param (EdgeLocation) spot2, the second of the new road locations
		*/
		ClientModel.prototype.roadBuilding = function (spot1, spot2) {

		}

		ClientModel.prototype.cityBuilding = function (spot1, spot2) {

		}

		ClientModel.prototype.settlementBuilding = function (spot1, spot2) {

		}

		/**
		    <pre>
		        PRE: Robber has moved
		        PRE: The victim has cards (or -1 if can't rob anyone)
		        POST: Robber is in a new location
		        POST: The victim gives one random resource card to the soldier
		    </pre>
		    @method soldier
		    @param HexLocation robberSpot, location of the robber
		    @param PlayerIndex victimIndex, index of the player being robbed
		*/
		ClientModel.prototype.soldier = function (robberSpot, victimIndex) {

		}

		/**
		    <pre>
		        POST: All other players lose the resource type that's chosen
		        POST: The player of the card gets an equal number
		    </pre>
		    @method monopoly
		*/
		ClientModel.prototype.monopoly = function () {

		}

		/**
		    <pre>
		        PRE: CAN be played on the turn that you buy it
		        POST: You gain one victory point
		    </pre>
		    @method monument
		*/
		ClientModel.prototype.monument = function () {

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
		ClientModel.prototype.offerTrade = function (offer, receiver) {

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
		ClientModel.prototype.acceptTrade = function () {

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
		ClientModel.prototype.discardCards = function (discardedCards) {

		}




        
		return ClientModel;
	}());	
	
	return ClientModel;
}());

