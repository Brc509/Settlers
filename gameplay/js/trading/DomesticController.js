//STUDENT-EDITABLE-BEGIN
/**
    This is the namespace for domestic trading
    @module catan.trade
    @submodule catan.trade.domestic
    @namespace domestic
*/

var catan = catan || {};
catan.trade = catan.trade ||{};
catan.trade.domestic = catan.trade.domestic ||{};

catan.trade.domestic.Controller= (function trade_namespace(){

	var Controller = catan.core.BaseController;
	var Definitions = catan.definitions;
	var ResourceTypes = Definitions.ResourceTypes;
    
	var DomesticController = ( function DomesticController_Class() {
    
		/** 
		@class DomesticController
		@constructor 
		@extends misc.BaseController
		@param {domestic.View} view
		@param {misc.WaitOverlay} waitingView
		@param {domestic.AcceptView} acceptView
		@param {models.ClientModel} clientModel
		*/
		function DomesticController(view,waitingView,acceptView,clientModel){
			Controller.call(this,view,clientModel);
			this.waitingView = waitingView;
			this.acceptView = acceptView;
			this.trader = "";
			this.waitingView = waitingView;
			this.woodAmt = 0;
			this.brickAmt = 0;
			this.wheatAmt = 0;
			this.sheepAmt = 0;
			this.oreAmt = 0;
			this.setResources = new Array();
			this.getResources = new Array();
			
			this.clientModel = clientModel;
			this.view = view;
			this.legalTraders = new Array();
			for(var i in this.clientModel.players)
			{
				if(this.clientModel.players[i].playerID != this.clientModel.playerID)
					this.legalTraders.push(this.clientModel.players[i]);
			}
			this.view.setPlayers(this.legalTraders);
			this.clientModel.addObserver(this);
			
			//console.log(this.clientModel.tradeOffer);
			//console.log(this.clientModel);
		};
        
		DomesticController.prototype = core.inherit(Controller.prototype);
         
         
		/******** Methods called by the Domestic View *********/
        
        /**
        * @method setResourceToSend
        * @param{String} resource the resource to send ("wood","brick","sheep","wheat","ore")
        * @return void
        */
		DomesticController.prototype.setResourceToSend = function(resource){
		
			if(this.getResources.length > 0)
			{
				var index = this.getResources.indexOf(resource);
				if (index > -1)
				{
					this.getResources.splice(index, 1);
				}
			}	
		
			this.setResources.push(resource);
			
			console.log(this.setResources);
		};
        
		/**
		 * @method setResourceToReceive
		 * @param{String} resource the resource to receive ("wood","brick","sheep","wheat","ore")
		 * @return void
		 */
		 DomesticController.prototype.setResourceToReceive = function(resource){
		 
			if(this.setResources.length > 0)
			{
				var index = this.setResources.indexOf(resource);
				if (index > -1)
				{
					this.setResources.splice(index, 1);
				}
			}
			
			this.getResources.push(resource);
			
			console.log(this.getResources);
		};
        
		/**
		  * @method unsetResource
		  * @param{String} resource the resource to clear ("wood","brick","sheep","wheat","ore")
		  * @return void
		  */
		DomesticController.prototype.unsetResource = function(resource){
		
			if(this.getResources.length > 0)
			{
				var index = this.getResources.indexOf(resource);
				if (index > -1)
				{
					this.getResources.splice(index, 1);
				}
			}
			
			if(this.setResources.length > 0)
			{
				var index = this.setResources.indexOf(resource);
				if (index > -1)
				{
					this.setResources.splice(index, 1);
				}
			}
		};
        
		/**
		 * @method setPlayerToTradeWith
		 * @param{int} playerNumber the player to trade with
		 * @return void
		 */
		DomesticController.prototype.setPlayerToTradeWith = function(playerNumber){
			//console.log(playerNumber);
			if(playerNumber != -1)
			{
				this.trader = this.legalTraders[playerNumber];
				//console.log(this.trader.name);
				this.view.setStateMessage("set the trade you want to make");
				this.view.setTradeButtonEnabled(false);
				this.view.setResourceSelectionEnabled(true);
				
				this.view.setResourceAmountChangeEnabled("wood", true, true);
		 		this.view.setResourceAmountChangeEnabled("brick", true, true);
		 		this.view.setResourceAmountChangeEnabled("sheep", true, true);
		 		this.view.setResourceAmountChangeEnabled("wheat", true, true);
		 		this.view.setResourceAmountChangeEnabled("ore", true, true);
				
				this.view.setResourceAmount("wood", this.woodAmt);
				this.view.setResourceAmount("brick", this.brickAmt);
				this.view.setResourceAmount("sheep", this.sheepAmt);
				this.view.setResourceAmount("wheat", this.wheatAmt);
				this.view.setResourceAmount("ore", this.oreAmt);
			}
			else
			{
				this.woodAmt = 0;
				this.brickAmt = 0;
				this.wheatAmt = 0;
				this.sheepAmt = 0;
				this.oreAmt = 0;
			
				this.view.setStateMessage("select a player");
				this.view.setResourceSelectionEnabled(false);
				this.view.setTradeButtonEnabled(false);

		 		this.view.setResourceAmountChangeEnabled("wood", false, false);
		 		this.view.setResourceAmountChangeEnabled("brick", false, false);
		 		this.view.setResourceAmountChangeEnabled("sheep", false, false);
		 		this.view.setResourceAmountChangeEnabled("wheat", false, false);
		 		this.view.setResourceAmountChangeEnabled("ore", false, false);
		 		this.view.clearTradeView();
			}
		};
        
		/**
		* Increases the amount to send or receive of a resource
		* @method increaseResourceAmount
		* @param{String} resource ("wood","brick","sheep","wheat","ore")
		* @return void
		*/
		DomesticController.prototype.increaseResourceAmount = function(resource){
			console.log(this.clientModel.players);
			
				if(resource == "wood")
				{
					if(this.woodAmt < this.clientModel.players[this.clientModel.playerID].resources.wood)
					{
						this.woodAmt++;
						this.view.setResourceAmount("wood", this.woodAmt);
					}
				}
				else if(resource == "brick")
				{
					if(this.brickAmt < this.clientModel.players[this.clientModel.playerID].resources.brick)
					{
						this.brickAmt++;
						this.view.setResourceAmount("brick", this.brickAmt);
					}
				}
				else if(resource == "sheep")
				{
					if(this.sheepAmt < this.clientModel.players[this.clientModel.playerID].resources.sheep)
					{
						this.sheepAmt++;
						this.view.setResourceAmount("sheep", this.sheepAmt);
					}
				}
				else if(resource == "wheat")
				{
					if(this.wheatAmt < this.clientModel.players[this.clientModel.playerID].resources.wheat)
					{
						this.wheatAmt++;
						this.view.setResourceAmount("wheat", this.wheatAmt);
					}
				}
				else if(resource == "ore")
				{
					if(this.oreAmt < this.clientModel.players[this.clientModel.playerID].resources.ore)
					{
						this.oreAmt++;
						this.view.setResourceAmount("ore", this.oreAmt);
					}
				}

				if(	this.woodAmt == 0 &&
					this.brickAmt == 0 &&
					this.wheatAmt == 0 &&
					this.sheepAmt == 0 &&
					this.oreAmt == 0)
				{
					this.view.setTradeButtonEnabled(false);
				}
				else
				{
					this.view.setTradeButtonEnabled(true);
				}
		};
        
		/**
		 * Decreases the amount to send or receive of a resource
		 * @method decreaseResourceAmount
		 * @param{String} resource ("wood","brick","sheep","wheat","ore")
		 * @return void
		 */
		DomesticController.prototype.decreaseResourceAmount = function(resource){
		
				if(resource == "wood")
				{
					if(this.woodAmt > 0)
					{
						this.woodAmt--;
						this.view.setResourceAmount("wood", this.woodAmt);
					}
				}
				else if(resource == "brick")
				{
					if(this.brickAmt > 0)
					{
						this.brickAmt--;
						this.view.setResourceAmount("brick", this.brickAmt);
					}
				}
				else if(resource == "sheep")
				{
					if(this.sheepAmt > 0)
					{
						this.sheepAmt--;
						this.view.setResourceAmount("sheep", this.sheepAmt);
					}
				}
				else if(resource == "wheat")
				{
					if(this.wheatAmt > 0)
					{
						this.wheatAmt--;
						this.view.setResourceAmount("wheat", this.wheatAmt);
					}
				}
				else if(resource == "ore")
				{
					if(this.oreAmt > 0)
					{
						this.oreAmt--;
						this.view.setResourceAmount("ore", this.oreAmt);
					}
				}

				if(	this.woodAmt == 0 &&
					this.brickAmt == 0 &&
					this.wheatAmt == 0 &&
					this.sheepAmt == 0 &&
					this.oreAmt == 0)
				{
					this.view.setTradeButtonEnabled(false);
				}
				else
				{
					this.view.setTradeButtonEnabled(true);
				}				
		};
        
		/**
		  * Sends the trade offer to the accepting player
		  * @method sendTradeOffer
		  * @return void
		  */
		DomesticController.prototype.sendTradeOffer = function(){
			if(this.trader != "")
			{
				if(this.setResources.length > 0 && this.getResources.length > 0)
				{
					for(var i=0;i<this.setResources.length;i++)
					{
					
					}
					
					for(var i=0;i<this.getResources.length;i++)
					{
					
					}
				
				}
			
				this.waitingView.showModal();
				
				//send offer through the server here !!!!
			}
		};
        
        
		/******************* Methods called by the Accept Overlay *************/
		 
        /**
        * Finalizes the trade between players
        * @method acceptTrade
        * @param{Boolean} willAccept
        * @return void
		*/
		DomesticController.prototype.acceptTrade = function(willAccept){
		
			// need to update sender and receiver of changes (if any) to their resource cards through the server
			// receiver accepting or not determines if a change gets sent to the server.
		
			this.acceptView.closeModal();
		};
		
		DomesticController.prototype.update = function(){
		
			console.log(this.clientModel);
			
			if(this.clientModel.turnTracker.currentTurn != this.clientModel.playerID)
			{
				this.view.setStateMessage("Guess What? NACHO TURN!!!");
				this.view.setResourceSelectionEnabled(false);
				this.view.setTradeButtonEnabled(false);
				this.view.setPlayerSelectionEnabled(false);
			}
			else
			{							
				this.view.setPlayerSelectionEnabled(true);
			
				if(this.clientModel.tradeOffer != undefined)
				{
					if(this.clientModel.tradeOffer.receiver == this.clientModel.playerID)
					{
						// need to check client model for tradeOffer to parse and display to receiver
						
						//this.acceptView.addGiveResource('ore', this.oreAmt);
						//this.acceptView.addGetResource('ore', 1);	
						
						this.acceptView.showModal();
					}
				}
				else
				{
					this.waitingView.closeModal();
				}
				
				this.view.setStateMessage("select a player");
				this.view.setResourceSelectionEnabled(false);
				this.view.setTradeButtonEnabled(false);
				this.view.clearTradeView();
			}
		}
            
		return DomesticController;
    }());
			
	return DomesticController;
}());


