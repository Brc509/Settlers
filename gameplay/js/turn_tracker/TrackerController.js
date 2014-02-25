//STUDENT-EDITABLE-BEGIN
/**
The namespace for the turn tracker

@module catan.turntracker
@namespace turntracker
**/

var catan = catan || {};
catan.turntracker = catan.turntracker || {};

catan.turntracker.Controller = (function turntracker_namespace() {	

	var Controller = catan.core.BaseController;
    
	/**
		The controller class for the Turn Tracker
		@class TurnTrackerController 
		@extends misc.BaseController
		@param {turntracker.View} view The view for this object to control.
		@param {models.ClientModel} clientModel The clientModel for this object to control.
		@constructor
	**/
	var TurnTrackerController = (function TurnTrackerController_Class(){
	
		function TurnTrackerController(view, clientModel){
			Controller.call(this,view,clientModel);

			var player = this.getCurrentPlayer();
			this.View.setClientColor(player.color);
			this.View.initializePlayer(this.ClientModel.turnTracker.currentTurn, player.name, player.color);

            // TODO: This constructor should configure its view by calling view.setClientColor and view.initializePlayer
            // NOTE: The view.updateViewState and view.updatePlayer will not work if called from here.  Instead, these
            //          methods should be called later each time the client model is updated from the server.
		}

		core.forceClassInherit(TurnTrackerController,Controller);

		/**
		 * Called by the view when the local player ends their turn.
		 * @method endTurn
		 * @return void
		 */
		TurnTrackerController.prototype.endTurn = function(){
			this.ClientModel.finishTurn();
		}


		TurnTrackerController.prototype.getCurrentPlayer = function(){
				
				var curTurn = this.ClientModel.turnTracker.currentTurn;
				var currPlayerIndex = this.ClientModel.playerID;
				for(p in this.ClientModel.players){
					var player = this.ClientModel.players[p];
					if(player.playerID == currPlayerIndex){
						return this.ClientModel.players[p];
					}
				}
			
		
		}

		TurnTrackerController.prototype.update = function(){
			// console.log("updating shiz");
			var player = this.getCurrentPlayer();
			
			var currPlayerIndex = this.ClientModel.playerIndex;
			if(this.ClientModel.turnTracker.currentTurn == currPlayerIndex){

				if(this.ClientModel.turnTracker.status == "Playing" || this.ClientModel.turnTracker.status == "Robbing"){
					this.View.updateStateView(true, "End Turn");
				}else{

				}
			
			}else{
				this.View.updateStateView(false, "Not your turn");
			}
		}
		
		return TurnTrackerController;
	} ());

	return TurnTrackerController;
} ());

