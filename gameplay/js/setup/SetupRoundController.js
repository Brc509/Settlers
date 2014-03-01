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
			var status = this.mapController.ClientModel.turnTracker.status;

			Controller.call(this,undefined,clientModel);

			if(status != "FirstRound" && status != "SecondRound"){
		
				window.location = "/catan.html";

			}


		};
        
		core.forceClassInherit(SetupRoundController,Controller);
	
	SetupRoundController.prototype.update = function(){	

		var status = this.mapController.ClientModel.turnTracker.status;
		if(status == "FirstRound" || status == "SecondRound"){

			var currPlayerIndex = this.mapController.ClientModel.playerIndex;
			var players = this.mapController.ClientModel.players;
			var settlements = players[currPlayerIndex].settlements;
			var roads = players[currPlayerIndex].roads;
			if(this.mapController.ClientModel.turnTracker.currentTurn == currPlayerIndex){

				if(roads == 14 && settlements == 4 && status == "FirstRound"){
					this.mapController.ClientModel.finishTurn();		
				}else if(roads == 15 && settlements == 5 && status == "FirstRound"){
					this.mapController.startMove("settlement", true, true);		
				}else if(roads == 15 && settlements == 4 && status == "FirstRound"){
					this.mapController.startMove("road", true, true);		
				}else if(roads == 13 && settlements == 3 && status == "SecondRound"){
					this.mapController.ClientModel.finishTurn();		
				}else if(roads == 14 && settlements == 4 && status == "SecondRound"){
					this.mapController.startMove("settlement", true, true);		
				}else if(roads == 14 && settlements == 3 && status == "SecondRound"){
					this.mapController.startMove("road", true, true);		
				}		
			}
			
		}else{

			window.location = "/catan.html";
		}
	}
        
		return SetupRoundController;
	}());
    
	return SetupRoundController;
}());

