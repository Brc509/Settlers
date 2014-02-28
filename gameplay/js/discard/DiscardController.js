//STUDENT-EDITABLE-BEGIN
/**
    This is the namespace for discarding cards
    @module catan.discard
    @namespace discard
*/

var catan = catan || {};
catan.discard = catan.discard || {};

catan.discard.Controller = (function discard_namespace(){

	var Controller = catan.core.BaseController;
    
	var Definitions = catan.definitions;
	var ResourceTypes = catan.definitions.ResourceTypes;
	
    /**
     * @class DiscardController
     * @constructor
     * @extends misc.BaseController
     * @param{discard.DiscardView} view
     * @param{misc.WaitOverlay} waitingView
     * @param{models.ClientModel} clientModel
     */
	var DiscardController = (function DiscardController_Class(){
			
		function DiscardController(view, waitingView, clientModel){
        
            Controller.call(this,view,clientModel);
			
            view.setController(this);
            this.view = view;
            this.clientModel = clientModel;
            this.cards = {
	            brick : 0,
	            ore : 0,
	            sheep : 0, 
	            wheat : 0,
	            wood : 0
        	}
            
            waitingView.setController(this);
            this.setWaitingView(waitingView);
		}

		core.forceClassInherit(DiscardController,Controller);

		core.defineProperty(DiscardController.prototype,"waitingView");

		/**
		 Called by the view when the player clicks the discard button.
         It should send the discard command and allow the game to continue.
		 @method discard
		 @return void
		 */	
		DiscardController.prototype.discard = function(){
			console.log('discard function');
			clientModel.canDiscarCards(this.cards);
			clientModel.discardCards(this.cards);
		}
        
		/**
		 Called by the view when the player increases the amount to discard for a single resource.
		 @method increaseAmount
		 @param {String} resource the resource to discard
		 @return void
		 */
		DiscardController.prototype.increaseAmount = function(resource){
			console.log('increaseAmount. resource', resource);
			this.cards[resource] ++;
			view.setResourceAmount(resource, this.cards[resource]);
		}
        
		/**
		 Called by the view when the player decreases the amount to discard for a single resource.
		 @method decreaseAmount
		 @param {String} resource the resource to discard
		 @return void
		 */
		DiscardController.prototype.decreaseAmount = function(resource){
			console.log('decreaseAmount. resource', resource);
			if (this.cards[resource] > 0)
				this.cards[resource] --;
			view.setResourceAmount(resource, this.cards[resource]);
		}
		
		return DiscardController;
	}());
	
    return DiscardController;
}());

