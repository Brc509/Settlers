//STUDENT-EDITABLE-BEGIN
var catan = catan || {};
catan.points = catan.points || {};
catan.points.Controller = catan.points.Controller || {};

/**
    This is the namespace for point display
    @module catan.points
    @namespace points
*/

catan.points.Controller = (function VPController_Class(){

	var Controller = catan.core.BaseController;

	PointController.prototype = core.inherit(Controller.prototype);

	core.defineProperty(PointController.prototype, "gameFinishedView");

	/** 
		@class PointController
		@constructor 
		@extends misc.BaseController
		@param {points.View} view
		@param {misc.GameFinishedView} gameFinishedView
		@param {models.ClientModel} clientModel
	*/
	function PointController(view, gameFinishedView, clientModel){
		this.setGameFinishedView(gameFinishedView);
		Controller.call(this,view,clientModel);
		this.view = view;
		this.update(clientModel);
	}

	PointController.prototype.update = function(clientModel) {
		//console.log('update in the PointController', clientModel);
		this.view.setPoints(clientModel.clientPlayer.victoryPoints);
		var players = clientModel.players;
		if (clientModel.clientPlayer.victoryPoints > 9) {
			this.gameFinishedView.setWinner(clientModel.clientPlayer.name, true);
			this.gameFinishedView.showModal();
		} else {
			for (p in players) {
				if (players[p].victoryPoints > 9) {
					this.gameFinishedView.setWinner(players[p].name, false);
					this.gameFinishedView.showModal();	
				}
			}
		}
	}
	
	return PointController;	
}());
// STUDENT-REMOVE-END

