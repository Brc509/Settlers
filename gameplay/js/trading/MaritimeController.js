//STUDENT-EDITABLE-BEGIN
/**
    This is the namespace for maritime trading
    @module catan.trade
    @submodule catan.trade.maritime
    @namespace maritime
*/

var catan = catan || {};
catan.trade = catan.trade || {};
catan.trade.maritime = catan.trade.maritime || {};

catan.trade.maritime.Controller = (function trade_namespace(){
    
	var Definitions = catan.definitions;
	var ResourceTypes = Definitions.ResourceTypes;
    
	var MaritimeController = ( function MaritimeController_Class() {

        var Controller = catan.core.BaseController;
        
        /**
		@class MaritimeController
		@constructor 
		@extends misc.BaseController
		@param {maritime.View} view
		@param {models.ClientModel} clientModel
		*/
		function MaritimeController(view,clientModel){
			Controller.call(this,view,clientModel);
			this.view = view;
			this.clientModel = clientModel;
		};

		MaritimeController.prototype = core.inherit(Controller.prototype);

		MaritimeController.prototype.update = function(clientModel) {

			this.clientModel = clientModel;
			this.init();
		}

		MaritimeController.prototype.init = function() {

			if (this.clientModel.isMyTurn()) {
			
				this.setResourceRatios(this.giveResourceList);
				this.setResourcesClientCanGive();
				this.listBankResources();
				this.unsetGiveValue();
			}
			else {

				this.view.hideGiveOptions();
				this.view.hideGetOptions();
				this.view.enableTradeButton(false);
				this.view.setMessage("It's not your turn!");
			}
		}

		MaritimeController.prototype.setResourceRatios = function() {

			var ratios = new catan.models.ResourceList();
			var resourcesTypes = ["brick", "ore", "sheep", "wheat", "wood"];

			// TODO: Get the actual values (4 is standard, 3 is if player is on a 3:1 location, 
			// 		 2 is if player is on a tile for that specific resource)
			ratios.brick = 4;
			ratios.ore = 4;	
			ratios.sheep = 4;
			ratios.wheat = 4;
			ratios.wood = 4;

			/*
			var ports = this.clientModel.map.ports;
			for (var i = 0; i < ports.length; i++) {

				var curPort = ports[i];
				for (var j = 0; j < ports[i].validVertexes.length; j++) {

					var curVertex = ports[i].validVertexes[j];
					console.log(curVertex.direction);
				}
			}*/

			this.ratios = ratios;
		}

		/* 
			Generate a list of all the resourceTypes that the bank currently has
		*/
		MaritimeController.prototype.listBankResources = function() {

            var ownedResources = [];
            var resourceTypes = ["brick", "ore", "sheep", "wheat", "wood"];
            for (r in resourceTypes) {

            	if (this.clientModel.bank[resourceTypes[r]] > 0)
            		ownedResources.push(resourceTypes[r]);
            }
            this.getResourceList = ownedResources;
        }

		MaritimeController.prototype.setResourcesClientCanGive = function() {

			var giveResources = [];
			var types = catan.definitions.ResourceTypes;
			var resources = this.clientModel.clientPlayer.resources;
			
			for (t in types) {

				// add the value to the list IF the player can afford the ratio
				if (resources[types[t]] >= this.ratios[types[t]])
					giveResources.push(types[t]);
			}

			this.giveResourceList = giveResources;
		}

		/**
         * Called by the view when the player "undoes" their give selection
		 * @method unsetGiveValue
		 * @return void
		 */
		MaritimeController.prototype.unsetGiveValue = function(){

			this.view.showGiveOptions(this.giveResourceList);
			this.view.hideGetOptions();
			this.view.enableTradeButton(false);
			this.view.setMessage("Select which resource to give up");
		};
        
		/**
         * Called by the view when the player "undoes" their get selection
		 * @method unsetGetValue
		 * @return void
		 */
		MaritimeController.prototype.unsetGetValue = function(){

			this.view.showGetOptions(this.getResourceList);
			this.view.enableTradeButton(false);
			this.view.setMessage("Select which resource to get");
		};
        
		/**
         * Called by the view when the player selects which resource to give
		 * @method setGiveValue
		 * @param{String} resource The resource to trade ("wood","brick","sheep","wheat","ore")
		 * @return void
		 */
		MaritimeController.prototype.setGiveValue = function(resource){

			this.giveResource = resource;
			this.ratio = this.ratios.getResource(resource);
			this.view.selectGiveOption(resource, this.ratio);
			this.view.showGetOptions(this.getResourceList);
			this.view.setMessage("Select which resource to get");
		};
        
		/**
         * Called by the view when the player selects which resource to get
		 * @method setGetValue
		 * @param{String} resource The resource to trade ("wood","brick","sheep","wheat","ore")
		 * @return void
		 */
		MaritimeController.prototype.setGetValue = function(resource){

			this.getResource = resource;
			this.view.selectGetOption(resource, 1);
			this.view.enableTradeButton(true);
			this.view.setMessage("Trade!");
		};
        
        function capFirst(str){
            return str[0].toUpperCase() + str.slice(1);
        }
        
		/** Called by the view when the player makes the trade
		 * @method makeTrade
		 * @return void
		 */
		MaritimeController.prototype.makeTrade= function(){

			this.clientModel.maritimeTrade(this.ratio, this.giveResource, this.getResource);
		}
		
       return MaritimeController;
	}());

	return MaritimeController;
}());


