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

		var cm = this.mapController.ClientModel;
		var status = cm.turnTracker.status;
		if(status == "FirstRound" || status == "SecondRound"){

			var currPlayerIndex = cm.playerIndex;
			var players = cm.players;
			var settlements = players[currPlayerIndex].settlements;
			var roads = players[currPlayerIndex].roads;

			if(cm.turnTracker.currentTurn == currPlayerIndex){

				if(roads == 14 && settlements == 4 && status == "FirstRound"){
					cm.finishTurn();		
				}else if(roads == 15 && settlements == 5 && status == "FirstRound" && !cm.isModalUp){
					this.mapController.startMove("settlement", true, true);		
				}else if(roads == 15 && settlements == 4 && status == "FirstRound" && !cm.isModalUp){
					this.mapController.startMove("road", true, true);		
				}else if(roads == 13 && settlements == 3 && status == "SecondRound"){
					cm.finishTurn();		
				}else if(roads == 14 && settlements == 4 && status == "SecondRound" && !cm.isModalUp){
					this.mapController.startMove("settlement", true, true);		
				}else if(roads == 14 && settlements == 3 && status == "SecondRound" && !cm.isModalUp){
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

