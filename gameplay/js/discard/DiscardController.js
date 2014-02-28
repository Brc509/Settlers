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
            waitingView.setController(this);
            this.setWaitingView(waitingView);


            this.view = view;
            this.clientModel = clientModel;
            this.resources = {};
            this.cards = {
	            brick : 0,
	            ore : 0,
	            sheep : 0, 
	            wheat : 0,
	            wood : 0
        	}
        	this.minCards = 0;
        	this.waiting = false;
            
            
		}

		core.forceClassInherit(DiscardController,Controller);

		core.defineProperty(DiscardController.prototype,"waitingView");

		DiscardController.prototype.update = function (model) {
			console.log('DiscardController. ready for some updating', model.turnTracker.status);
			if (model.turnTracker.status == "Discarding" && !this.waiting) {
				this.view.showModal();
				this.minCards = Math.floor(model.clientPlayer.getResourceCardCount() / 2);
				this.resources = this.clientModel.clientPlayer.resources;
				this.updateView();
			}
			if (model.turnTracker.status != "Discarding" && !this.waiting) {
				this.waiting = false;
				this.waitingView.closeModal();
			}
		}

		DiscardController.prototype.updateView = function () {
			for (c in this.cards) {
				var increase = (this.resources[c] > 0) ? true : false;
				var decrease = (this.cards[c] > 0) ? true : false;
				this.view.setResourceAmountChangeEnabled (c, increase, decrease);
				this.view.setResourceMaxAmount(c, this.resources[c]);
				this.view.setResourceAmount(c, this.cards[c]);	
			}
			var message = "hurry up";
			var canDiscard = false;
			if (this.getDiscardCount() >= this.minCards)	{
				message = 'Get rid of them!';
				canDiscard = true;
			}
			this.view.setDiscardButtonEnabled(canDiscard);
			this.view.setStateMessage(message);
		}

		DiscardController.prototype.getDiscardCount = function () {
			var count = 0;
			for (c in this.cards) { count += this.cards[c]; }
				
			return count;
		}

		/**
		 Called by the view when the player clicks the discard button.
         It should send the discard command and allow the game to continue.
		 @method discard
		 @return void
		 */	
		DiscardController.prototype.discard = function(){
			console.log('discard function');
			this.clientModel.discardCards(this.cards);
			this.view.closeModal();
			this.waitingView.showModal();
			this.waiting = true;

		}
        
		/**
		 Called by the view when the player increases the amount to discard for a single resource.
		 @method increaseAmount
		 @param {String} resource the resource to discard
		 @return void
		 */
		DiscardController.prototype.increaseAmount = function(resource){
			console.log('increaseAmount. resource', resource);
			if (this.resources[resource] > 0)
			{
				this.resources[resource] = this.resources[resource] - 1;
				this.cards[resource] = this.cards[resource] + 1;
				this.updateView();
			}
		}
        
		/**
		 Called by the view when the player decreases the amount to discard for a single resource.
		 @method decreaseAmount
		 @param {String} resource the resource to discard
		 @return void
		 */
		DiscardController.prototype.decreaseAmount = function(resource){
			console.log('decreaseAmount. resource', resource);
			if (this.cards[resource] > 0){
				this.resources[resource] = this.resources[resource] + 1;
				this.cards[resource] = this.cards[resource] - 1;
				this.updateView();
			}
		}
		
		return DiscardController;
	}());
	
    return DiscardController;
}());

