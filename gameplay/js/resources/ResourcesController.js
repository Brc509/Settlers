//STUDENT-EDITABLE-BEGIN
var catan = catan || {};
catan.resources = catan.resources || {};

/**
    This is the namespace for resources
    @module catan.resources
    @namespace resources
*/
    
catan.resources.Controller = (function resources_namespace() {

	var Controller = catan.core.BaseController;
	var Definitions = catan.definitions;
	
	/*	these are the values to use for updating the view: */
    var ROAD = Definitions.ROAD;
    var SETTLEMENT = Definitions.SETTLEMENT;
    var CITY = Definitions.CITY;
    var BUY_CARD = Definitions.BUY_CARD;
    var PLAY_CARD = Definitions.PLAY_CARD;
    var ARMY = Definitions.ARMY;
    var Resources = Definitions.ResourceTypes;
    var Buyables = new Array(ROAD, SETTLEMENT, CITY, BUY_CARD, PLAY_CARD, ARMY);
		
	var ResourceBarController = (function ResourceBarController_Class(){
    
		/**
		 Controller class for the Resources View.
		 @class ResourceBarController
		 @constructor
		 @extends misc.BaseController
		 @param{resources.View} view The resource view
		 @param{models.ClientModel} clientModel The client model
		 @param{Object} actions The actions to take for each user input. The value of actions.elem_name is a function that is called when the specific element is selected (accessed by calling actions["elem_name"]).  The valid element names are defined in StudentDefinitions.js
        */
		function ResourceBarController(view,clientModel,actions){
			this.setActions(actions);
			Controller.call(this,view,clientModel);

			this.view = view;
			this.clientModel = clientModel;
		};

		core.forceClassInherit(ResourceBarController,Controller);
        
		ResourceBarController.prototype.constructor = ResourceBarController;

		/**
		 *  Initialize the values for the resource bar
		 */
		ResourceBarController.prototype.init = function() {

			// Update the view
			this.updateNumbers();
			this.setEnabled();
		}

		ResourceBarController.prototype.update = function(clientModel) {

			this.clientModel = clientModel;
			this.init();
		}
        
		core.defineProperty(ResourceBarController.prototype, "Actions");

		/**
		 * The action to take on clicking the resource bar road button. Brings up the map 
		 * overlay and allows you to place a road.
		 * 
		 * @method buildRoad
		 * @return void
		 */
		ResourceBarController.prototype.buildRoad = function(){
        
            // NOTE: YOU DON'T NEED TO CHANGE THIS METHOD

            // This calls the "startMove" method on the Map Controller.
			this.getActions()[ROAD]();
		}

		/**
		*	Update the number fields on the resource side-bar
		*/
		ResourceBarController.prototype.updateNumbers = function() {

			var types = catan.definitions.ResourceTypes;
			var resources = this.clientModel.clientPlayer.resources;
			var p = this.clientModel.clientPlayer;

			for (t in types)
				this.view.updateAmount(types[t], resources[types[t]]);

			this.view.updateAmount("Roads", p.roads);
			this.view.updateAmount("Settlements", p.settlements);
			this.view.updateAmount("Cities", p.cities);
			this.view.updateAmount("Soldiers", p.soldiers);
		}

		/**
		 *   Update the resource view with whichever fields can be enabled
		 */
		ResourceBarController.prototype.setEnabled = function() {

			var p = this.clientModel.clientPlayer;
			if (this.clientModel.isMyTurn()) {

				this.view.setActionEnabled("Roads", p.canAffordRoad());
				this.view.setActionEnabled("Settlements", p.canAffordSettlement());
				this.view.setActionEnabled("Cities", p.canAffordCity());
				this.view.setActionEnabled("BuyCard", p.canAffordDevCard());
			}
			else {

				this.view.setActionEnabled("Roads", false);
				this.view.setActionEnabled("Settlements", false);
				this.view.setActionEnabled("Cities", false);
				this.view.setActionEnabled("BuyCard", false);
			}
		}
        
		/**
		 * The action to take on clicking the resource bar settlement button. Brings up the map 
		 * overlay and allows you to place a settlement.
		 * 
		 * @method buildSettlement
		 * @return void
		 */
		ResourceBarController.prototype.buildSettlement = function(){
        
            // NOTE: YOU DON'T NEED TO CHANGE THIS METHOD

            // This calls the "startMove" method on the Map Controller.
			this.getActions()[SETTLEMENT]();
		}

		/**
		 * The action to take on clicking the resource bar city button. Brings up the map 
		 * overlay and allows you to place a city.
		 * 
		 * @method buildCity
		 * @return void
		 */
		ResourceBarController.prototype.buildCity = function(){
        
            // NOTE: YOU DON'T NEED TO CHANGE THIS METHOD

            // This calls the "startMove" method on the Map Controller.
			this.getActions()[CITY]();
		}
        
		/**
		 * The action to take on clicking the resource bar "buy a card" button. 
		 * Should bring up the "buy a card" overlay.
		 * 
		 * @method buyCard
		 * @return void
		 */
		ResourceBarController.prototype.buyCard = function(){
        
            // NOTE: YOU DON'T NEED TO CHANGE THIS METHOD

            // This calls the "showModal" method on the Buy Card View.
			this.getActions()[BUY_CARD]();
		}
        
		/**
		 * The action to take on clicking the resource bar "play a card" button. 
		 * Should bring up the "play a card" overlay.
		 * 
		 * @method playCard
		 * @return void
		 */
		ResourceBarController.prototype.playCard = function(){
        
            // NOTE: YOU DON'T NEED TO CHANGE THIS METHOD

            // This calls the "showModal" method on the Dev Card View.
			this.getActions()[PLAY_CARD]();
		}
		
		return ResourceBarController;
		
	}());
	
	return ResourceBarController;
}());

