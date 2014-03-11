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
				this.giveResourceList = this.clientModel.clientPlayer.resources.getResourcesGreaterThan(this.ratios);
				this.getResourceList = this.clientModel.bank.getOwnedResources();
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
			var directions = ["W", "NW", "NE", "E", "SE", "SW"];

			ratios.brick = 4;
			ratios.ore = 4;
			ratios.sheep = 4;
			ratios.wheat = 4;
			ratios.wood = 4;

			var hexes = this.clientModel.map.hexGrid.hexes;

			for (i in this.clientModel.map.ports) {

				var p = this.clientModel.map.ports[i];
				var vv0 = p.validVertexes[0];
				var vv1 = p.validVertexes[1];
				var res = p.getInput();
				var vvOwner0;
				var vvOwner1;

				for (var j = 0; j < hexes.length; j++) {

					for (var k = 0; k < hexes[j].length; k++) {

						var hex = hexes[j][k];
						for (var l = 0; l < hex.vertexes.length; l++) {

							var vLoc = hex.vertexes[l].location;
							if (vLoc.x === vv0.x && vLoc.y === vv0.y && directions[vLoc.direction] === vv0.direction)
								vvOwner0 = hex.vertexes[l].ownerID;
							if (vLoc.x === vv1.x && vLoc.y === vv1.y && directions[vLoc.direction] === vv1.direction)
								vvOwner1 = hex.vertexes[l].ownerID;
						}
					}
				}
			
				// Means that the current player IS on this port
				if (vvOwner0 === this.clientModel.playerIndex || vvOwner1 === this.clientModel.playerIndex) {

					if (res === "none")
						ratios.setRatioThree();
					else
						ratios.setResource(res, 2);
				}
			}

			this.ratios = ratios;
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


