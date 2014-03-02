//STUDENT-EDITABLE-BEGIN
/**
    This is the namespace for development cards
    @module catan.devCards
    @namespace devCards
*/

var catan = catan || {};
catan.devCards = catan.devCards || {};

catan.devCards.Controller = (function(){

	var Controller = catan.core.BaseController;
	var Definitions = catan.definitions;
	
	var DevCardController = (function card_namespace(){
		
		core.forceClassInherit(DevCardController,Controller);

		core.defineProperty(DevCardController.prototype, "BuyView");

		/**
		 * @class DevCardController
		 * @constructor
		 * @extends misc.BaseController
		 * @param {devCards.DevCardView} view
		 * @param {devCards.BuyCardView} buyView
		 * @param {models.ClientModel} clientModel
		 * @param {function} soldierAction
		 * @param {function} roadAction
		 */
		function DevCardController(view, buyView, clientModel, soldierAction, roadAction){
			Controller.call(this,view,clientModel);
			this.setBuyView(buyView);
			this.clientModel = clientModel;
			this.view = view;

			// Store the soldierAction and roadAction
			this.soldierAction = soldierAction;
			this.roadAction = roadAction;
		}
		
		/**
		 *  Initialize the view by saying what numbers should appear and what to enable
		 */
		DevCardController.prototype.init = function() {

			this.enableButtons();
			this.updateNumbers();
		}

		DevCardController.prototype.update = function(clientModel) {

			this.clientModel = clientModel;
			this.init();
		}

		/**
		 *	Enable buttons IF they're in the current player's OLD devCardList
		 */
		DevCardController.prototype.enableButtons = function() {

			cardTypes = catan.definitions.CardTypes;
			cards = this.clientModel.clientPlayer.oldDevCards;

			if (this.clientModel.clientPlayer.playedDevCard) {

				for (c in cardTypes)
					this.view.setCardEnabled(cardTypes[c], false);
			}
			else {

				for (c in cardTypes)
					this.view.setCardEnabled(cardTypes[c], (cards[cardTypes[c]] > 0));
			}
		}

		/**
		 *  Update the number of each devCard (BOTH old and new combined) for the current player
		 */
		DevCardController.prototype.updateNumbers = function() {

			cardTypes = catan.definitions.CardTypes;
			oldCards = this.clientModel.clientPlayer.oldDevCards;
			newCards = this.clientModel.clientPlayer.newDevCards;

			for (c in cardTypes)
				this.view.updateAmount(cardTypes[c], (oldCards[cardTypes[c]] + newCards[cardTypes[c]]));
		}

		/**
		 * Called when the player buys a development card
		 * @method buyCard
		 * @return void
		 */
		DevCardController.prototype.buyCard = function(){

			this.ClientModel.buyDevCard();
		}
        
		/**
		 * Called when the player plays a year of plenty card
		 * @method useYearOfPlenty
		 * @param {String} resource1 The first resource to obtain
		 * @param {String} resource2 The second resource to obtain
		 * @return void
		 */
		DevCardController.prototype.useYearOfPlenty = function(resource1, resource2){

			this.ClientModel.yearOfPlenty(resource1, resource2);
			this.view.clearView();
		}
        
		/**
		 * Called when the player plays a monopoly card
		 * @method useMonopoly
		 * @param {String} resource the resource to obtain
		 * @return void
		 */
		DevCardController.prototype.useMonopoly= function(resource){

			this.ClientModel.monopoly(resource);
			this.view.clearView();
		}
        
		/**
		 * Called when the player plays a monument card
		 * @method useMonument
		 * @return void
		 */
		DevCardController.prototype.useMonument = function(){

			this.ClientModel.monument();
			this.view.clearView();
		}
        
		/**
		 * Called when the player plays a soldier card
		 * @method useSoldier
		 * @return void
		 */
		DevCardController.prototype.useSoldier= function(){

			this.soldierAction();
			this.view.clearView();
		}
        
		/**
		 * Called when the player plays the road building card
		 * @method useRoadBuild
		 * @return void
		 */
		DevCardController.prototype.useRoadBuild = function(resource){

			this.roadAction();
			this.view.clearView();
		}

		return DevCardController;
	}());
	
	return DevCardController;
}());

