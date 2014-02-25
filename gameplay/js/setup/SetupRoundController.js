//STUDENT-EDITABLE-BEGIN
/**
    This is the namespace for the intitial game round
    @module catan.setup
    @namespace setup
*/

var catan = catan || {};
catan.setup= catan.setup || {};

catan.setup.Controller = (function(){
	
	var Controller = catan.core.BaseController;
    
	/** 
		@class SetupRoundController
		@constructor 
		@extends misc.BaseController
		@param {models.ClientModel} clientModel
		@param {map.MapController} mapController
	*/
	var SetupRoundController = (function (){
		
		var SetupRoundController = function (clientModel, mapController){
			this.mapController = mapController;

			Controller.call(this,undefined,clientModel);

		};
        
		core.forceClassInherit(SetupRoundController,Controller);
	
	SetupRoundController.prototype.update = function(){	

		var status = this.mapController.ClientModel.turnTracker.status;
		if(status == "FirstRound" || status == "SecondRound"){

			var currPlayerIndex = this.mapController.ClientModel.playerIndex;
			if(this.mapController.ClientModel.turnTracker.currentTurn == currPlayerIndex){
				this.mapController.startMove("settlement", true, true);
				this.mapController.startMove("road", true, true);		
				this.mapController.ClientModel.finishTurn();		
			}
		}else{

			//redirect to catan.html
			window.location = "/catan.html";
		}
	}
        
		return SetupRoundController;
	}());
    
	return SetupRoundController;
}());

