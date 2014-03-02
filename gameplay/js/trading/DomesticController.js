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
			this.trader;
			this.waitingView = waitingView;
			this.woodAmt = 0;
			this.brickAmt = 0;
			this.wheatAmt = 0;
			this.sheepAmt = 0;
			this.oreAmt = 0;
			this.setResources = new Array();
			this.getResources = new Array();
			this.receivingWood = false;
			this.receivingBrick = false;
			this.receivingWheat = false;
			this.receivingSheep = false;
			this.receivingOre = false;
			this.trade;
			this.receiverIndex;
			
			this.clientModel = clientModel;
			
			console.log(this.clientModel);
			
			this.view = view;
			this.legalTraders = new Array();
			for(var i in this.clientModel.players)
			{
				if(this.clientModel.players[i].playerID != this.clientModel.playerID)
					this.legalTraders.push(this.clientModel.players[i]);
			}
			this.view.setPlayers(this.legalTraders);
			this.clientModel.addObserver(this);
		};
        
		DomesticController.prototype = core.inherit(Controller.prototype);
         
         
		/******** Methods called by the Domestic View *********/
        
        /**
        * @method setResourceToSend
        * @param{String} resource the resource to send ("wood","brick","sheep","wheat","ore")
        * @return void
        */
		DomesticController.prototype.setResourceToSend = function(resource){
		
			if(resource == "wood"){this.receivingWood = false;this.woodAmt = 0;
			this.view.setResourceAmount("wood", this.woodAmt);this.view.setResourceAmountChangeEnabled("wood", true, true);
			this.view.setResourceAmount("wood", this.woodAmt);}
			if(resource == "brick"){this.receivingBrick = false;this.brickAmt = 0;
			this.view.setResourceAmount("brick", this.brickAmt);this.view.setResourceAmountChangeEnabled("brick", true, true);
			this.view.setResourceAmount("brick", this.brickAmt);}
			if(resource == "sheep"){this.receivingSheep = false;this.sheepAmt = 0;
			this.view.setResourceAmount("sheep", this.sheepAmt);this.view.setResourceAmountChangeEnabled("sheep", true, true);
			this.view.setResourceAmount("sheep", this.sheepAmt);}
			if(resource == "wheat"){this.receivingWheat = false;this.wheatAmt = 0;
			this.view.setResourceAmount("wheat", this.wheatAmt);this.view.setResourceAmountChangeEnabled("wheat", true, true);
			this.view.setResourceAmount("wheat", this.wheatAmt);}
			if(resource == "ore"){this.receivingOre = false;this.oreAmt = 0;
			this.view.setResourceAmount("ore", this.oreAmt);this.view.setResourceAmountChangeEnabled("ore", true, true);
			this.view.setResourceAmount("ore", this.oreAmt);}	
		
			if(this.getResources.length > 0)
			{
				var index = this.getResources.indexOf(resource);
				if (index > -1)
				{
					this.getResources.splice(index, 1);
				}
			}	
		
			////////////////////////////////////////////////////////////////////////////////////////
			this.setResources.push(resource);
			
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
					if(this.setResources.length > 0 && this.getResources.length > 0)
					{
						if(	(this.woodAmt > 0  && !this.receivingWood ||
							this.brickAmt > 0 && !this.receivingBrick ||
							this.wheatAmt > 0 && !this.receivingWheat ||
							this.sheepAmt > 0 && !this.receivingSheep ||
							this.oreAmt > 0 && !this.receivingOre) && 
							(this.woodAmt > 0  && this.receivingWood ||
							this.brickAmt > 0 && this.receivingBrick ||
							this.wheatAmt > 0 && this.receivingWheat ||
							this.sheepAmt > 0 && this.receivingSheep ||
							this.oreAmt > 0 && this.receivingOre))
						{
							this.view.setTradeButtonEnabled(true);
						}
						else
							this.view.setTradeButtonEnabled(false);
					}
					else
						this.view.setTradeButtonEnabled(false);
				}
			
			//console.log(this.setResources);
		};
        
		/**
		 * @method setResourceToReceive
		 * @param{String} resource the resource to receive ("wood","brick","sheep","wheat","ore")
		 * @return void
		 */
		 DomesticController.prototype.setResourceToReceive = function(resource){
		 
			if(resource == "wood"){this.receivingWood = true;this.woodAmt = 0;
			this.view.setResourceAmount("wood", this.woodAmt);this.view.setResourceAmountChangeEnabled("wood", true, true);
			this.view.setResourceAmount("wood", this.woodAmt);}
			if(resource == "brick"){this.receivingBrick = true;this.brickAmt = 0;
			this.view.setResourceAmount("brick", this.brickAmt);this.view.setResourceAmountChangeEnabled("brick", true, true);
			this.view.setResourceAmount("brick", this.brickAmt);}
			if(resource == "sheep"){this.receivingSheep = true;this.sheepAmt = 0;
			this.view.setResourceAmount("sheep", this.sheepAmt);this.view.setResourceAmountChangeEnabled("sheep", true, true);
			this.view.setResourceAmount("sheep", this.sheepAmt);}
			if(resource == "wheat"){this.receivingWheat = true;this.wheatAmt = 0;
			this.view.setResourceAmount("wheat", this.wheatAmt);this.view.setResourceAmountChangeEnabled("wheat", true, true);
			this.view.setResourceAmount("wheat", this.wheatAmt);}
			if(resource == "ore"){this.receivingOre = true;this.oreAmt = 0;
			this.view.setResourceAmount("ore", this.oreAmt);this.view.setResourceAmountChangeEnabled("ore", true, true);
			this.view.setResourceAmount("ore", this.oreAmt);}		
		 
			if(this.setResources.length > 0)
			{
				var index = this.setResources.indexOf(resource);
				if (index > -1)
				{
					this.setResources.splice(index, 1);
				}
			}
			
			//////////////////////////////////////////////////////////////////////////////////////////////////
			this.getResources.push(resource);
			
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
					if(this.setResources.length > 0 && this.getResources.length > 0)
					{
						if(	(this.woodAmt > 0  && !this.receivingWood ||
							this.brickAmt > 0 && !this.receivingBrick ||
							this.wheatAmt > 0 && !this.receivingWheat ||
							this.sheepAmt > 0 && !this.receivingSheep ||
							this.oreAmt > 0 && !this.receivingOre) && 
							(this.woodAmt > 0  && this.receivingWood ||
							this.brickAmt > 0 && this.receivingBrick ||
							this.wheatAmt > 0 && this.receivingWheat ||
							this.sheepAmt > 0 && this.receivingSheep ||
							this.oreAmt > 0 && this.receivingOre))
						{
							this.view.setTradeButtonEnabled(true);
						}
						else
							this.view.setTradeButtonEnabled(false);
					}
					else
						this.view.setTradeButtonEnabled(false);
				}
			
			//console.log(this.getResources);
		};
        
		/**
		  * @method unsetResource
		  * @param{String} resource the resource to clear ("wood","brick","sheep","wheat","ore")
		  * @return void
		  */
		DomesticController.prototype.unsetResource = function(resource){
		
			if(resource == "wood"){this.receivingWood = false;this.woodAmt = 0;
			this.view.setResourceAmount("wood", this.woodAmt);this.view.setResourceAmountChangeEnabled("wood", false, false);
			this.view.setResourceAmount("wood", undefined);}
			if(resource == "brick"){this.receivingBrick = false;this.brickAmt = 0;
			this.view.setResourceAmount("brick", this.brickAmt);this.view.setResourceAmountChangeEnabled("brick", false, false);
			this.view.setResourceAmount("brick", undefined);}
			if(resource == "sheep"){this.receivingSheep = false;this.sheepAmt = 0;
			this.view.setResourceAmount("sheep", this.sheepAmt);this.view.setResourceAmountChangeEnabled("sheep", false, false);
			this.view.setResourceAmount("sheep", undefined);}
			if(resource == "wheat"){this.receivingWheat = false;this.wheatAmt = 0;
			this.view.setResourceAmount("wheat", this.wheatAmt);this.view.setResourceAmountChangeEnabled("wheat", false, false);
			this.view.setResourceAmount("wheat", undefined);}
			if(resource == "ore"){this.receivingOre = false;this.oreAmt = 0;
			this.view.setResourceAmount("ore", this.oreAmt);this.view.setResourceAmountChangeEnabled("ore", false, false);
			this.view.setResourceAmount("ore", undefined);}
		
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
					if(this.setResources.length > 0 && this.getResources.length > 0)
					{
						if(	(this.woodAmt > 0  && !this.receivingWood ||
							this.brickAmt > 0 && !this.receivingBrick ||
							this.wheatAmt > 0 && !this.receivingWheat ||
							this.sheepAmt > 0 && !this.receivingSheep ||
							this.oreAmt > 0 && !this.receivingOre) && 
							(this.woodAmt > 0  && this.receivingWood ||
							this.brickAmt > 0 && this.receivingBrick ||
							this.wheatAmt > 0 && this.receivingWheat ||
							this.sheepAmt > 0 && this.receivingSheep ||
							this.oreAmt > 0 && this.receivingOre))
						{
							this.view.setTradeButtonEnabled(true);
						}
						else
							this.view.setTradeButtonEnabled(false);
					}
					else
						this.view.setTradeButtonEnabled(false);
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
				
		 		this.view.setResourceAmountChangeEnabled("wood", false, false);
		 		this.view.setResourceAmountChangeEnabled("brick", false, false);
		 		this.view.setResourceAmountChangeEnabled("sheep", false, false);
		 		this.view.setResourceAmountChangeEnabled("wheat", false, false);
		 		this.view.setResourceAmountChangeEnabled("ore", false, false);
				
				this.view.setResourceAmount("wood", undefined);
				this.view.setResourceAmount("brick", undefined);
				this.view.setResourceAmount("sheep", undefined);
				this.view.setResourceAmount("wheat", undefined);
				this.view.setResourceAmount("ore", undefined);
			}
			else
			{			
				this.view.setStateMessage("select a player");
				this.view.setTradeButtonEnabled(false);
				this.view.setResourceSelectionEnabled(false);

		 		this.view.setResourceAmountChangeEnabled("wood", false, false);
		 		this.view.setResourceAmountChangeEnabled("brick", false, false);
		 		this.view.setResourceAmountChangeEnabled("sheep", false, false);
		 		this.view.setResourceAmountChangeEnabled("wheat", false, false);
		 		this.view.setResourceAmountChangeEnabled("ore", false, false);
				
				this.view.setResourceAmount("wood", undefined);
				this.view.setResourceAmount("brick", undefined);
				this.view.setResourceAmount("sheep", undefined);
				this.view.setResourceAmount("wheat", undefined);
				this.view.setResourceAmount("ore", undefined);
				
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
					if(!this.receivingWood)
					{
						if(this.woodAmt < this.clientModel.players[this.clientModel.playerID].resources.wood)
						{
							this.woodAmt++;
							this.view.setResourceAmount("wood", this.woodAmt);
						}
					}
					else
					{
						this.woodAmt++;
						this.view.setResourceAmount("wood", this.woodAmt);
					}
				}
				else if(resource == "brick")
				{
					if(!this.receivingBrick)
					{
						if(this.brickAmt < this.clientModel.players[this.clientModel.playerID].resources.brick)
						{
							this.brickAmt++;
							this.view.setResourceAmount("brick", this.brickAmt);
						}
					}
					else
					{
							this.brickAmt++;
							this.view.setResourceAmount("brick", this.brickAmt);
					}
				}
				else if(resource == "sheep")
				{
					if(!this.receivingSheep)
					{
						if(this.sheepAmt < this.clientModel.players[this.clientModel.playerID].resources.sheep)
						{
							this.sheepAmt++;
							this.view.setResourceAmount("sheep", this.sheepAmt);
						}
					}
					else
					{
							this.sheepAmt++;
							this.view.setResourceAmount("sheep", this.sheepAmt);
					}
				}
				else if(resource == "wheat")
				{
					if(!this.receivingWheat)
					{
						if(this.wheatAmt < this.clientModel.players[this.clientModel.playerID].resources.wheat)
						{
							this.wheatAmt++;
							this.view.setResourceAmount("wheat", this.wheatAmt);
						}
					}
					else
					{
							this.wheatAmt++;
							this.view.setResourceAmount("wheat", this.wheatAmt);
					}
				}
				else if(resource == "ore")
				{
					if(!this.receivingOre)
					{				
						if(this.oreAmt < this.clientModel.players[this.clientModel.playerID].resources.ore)
						{
							this.oreAmt++;
							this.view.setResourceAmount("ore", this.oreAmt);
						}
					}
					else
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
					if(this.setResources.length > 0 && this.getResources.length > 0)
					{
						if(	(this.woodAmt > 0  && !this.receivingWood ||
							this.brickAmt > 0 && !this.receivingBrick ||
							this.wheatAmt > 0 && !this.receivingWheat ||
							this.sheepAmt > 0 && !this.receivingSheep ||
							this.oreAmt > 0 && !this.receivingOre) && 
							(this.woodAmt > 0  && this.receivingWood ||
							this.brickAmt > 0 && this.receivingBrick ||
							this.wheatAmt > 0 && this.receivingWheat ||
							this.sheepAmt > 0 && this.receivingSheep ||
							this.oreAmt > 0 && this.receivingOre))
						{
							this.view.setTradeButtonEnabled(true);
						}
						else
							this.view.setTradeButtonEnabled(false);
					}
					else
						this.view.setTradeButtonEnabled(false);
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
					if(this.setResources.length > 0 && this.getResources.length > 0)
					{
						if(	(this.woodAmt > 0  && !this.receivingWood ||
							this.brickAmt > 0 && !this.receivingBrick ||
							this.wheatAmt > 0 && !this.receivingWheat ||
							this.sheepAmt > 0 && !this.receivingSheep ||
							this.oreAmt > 0 && !this.receivingOre) && 
							(this.woodAmt > 0  && this.receivingWood ||
							this.brickAmt > 0 && this.receivingBrick ||
							this.wheatAmt > 0 && this.receivingWheat ||
							this.sheepAmt > 0 && this.receivingSheep ||
							this.oreAmt > 0 && this.receivingOre))
						{
							this.view.setTradeButtonEnabled(true);
						}
						else
							this.view.setTradeButtonEnabled(false);
					}
					else
						this.view.setTradeButtonEnabled(false);
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
					//console.log(this.getResources);
					
					for(var i=0;i<this.getResources.length;i++)
					{
						if(this.getResources[i] == "brick" && this.receivingBrick)
							this.brickAmt = this.brickAmt*(-1);
						else if(this.getResources[i] == "wood" && this.receivingWood)
							this.woodAmt = this.woodAmt*(-1);
						else if(this.getResources[i] == "wheat" && this.receivingWheat)
							this.wheatAmt = this.wheatAmt*(-1);
						else if(this.getResources[i] == "sheep" && this.receivingSheep)
							this.sheepAmt = this.sheepAmt*(-1);
						else if(this.getResources[i] == "ore" && this.receivingOre)
							this.oreAmt = this.oreAmt*(-1);
					}			
				}
				
				this.offer = {
                "brick": this.brickAmt,
                "ore": this.oreAmt,
                "sheep": this.sheepAmt,
                "wheat": this.wheatAmt,
                "wood": this.woodAmt
				};
				
				this.receiverIndex = -1;
				for(var i in this.clientModel.players)
				{
					if(this.clientModel.players[i].name == this.trader.name)
						this.receiverIndex = i;
				}
			
				this.clientModel.offerTrade(this.receiverIndex, this.offer);
				
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
		
			this.clientModel.acceptTrade(willAccept);
		
			this.acceptView.closeModal();
		};
		
		DomesticController.prototype.update = function(model){
		
			this.trade = model.model.tradeOffer;
			console.log(this.clientModel);
			
			if(typeof this.trade == "undefined")
				this.waitingView.closeModal();
			
			if(this.clientModel.turnTracker.currentTurn != this.clientModel.playerID)
			{
				this.view.setStateMessage("Guess What? NACHO TURN!!!");
				this.view.setResourceSelectionEnabled(false);
				this.view.setTradeButtonEnabled(false);
				this.view.setPlayerSelectionEnabled(false);
				
				//console.log(typeof this.trade);
				
				if(typeof this.trade != "undefined")
				{
					console.log(this.trade.receiver);
					
					this.receiverIndex = -1;
					for(var i in this.clientModel.players)
					{
						if(this.clientModel.players[i].playerID == this.clientModel.playerID)
							this.receiverIndex = i;
							
						console.log(this.clientModel.players[i].playerID);
						console.log(this.clientModel.playerID);
					}
					
					console.log(this.receiverIndex);
					
					if(this.trade.receiver == this.receiverIndex)
					{
						// need to check client model for tradeOffer to parse and display to receiver
						//console.log(this.trade.wood)
						
						this.acceptView.setPlayerName(this.clientModel.players[this.trade.sender].name);
						
						if(this.trade.offer.wood > 0)
						{
							this.acceptView.addGiveResource('wood', this.trade.offer.wood);
						}
						if(this.trade.offer.wood < 0)
						{
							if(this.trade.offer.wood*(-1) > this.clientModel.players[this.receiverIndex].resources.wood) 
								this.acceptView.setAcceptEnabled(false);
							else
								this.acceptView.setAcceptEnabled(true);
							this.acceptView.addGetResource('wood', this.trade.offer.wood);
						}
						if(this.trade.offer.brick > 0)
						{
							this.acceptView.addGiveResource('brick', this.trade.offer.brick);
						}
						if(this.trade.offer.brick < 0)
						{
							if(this.trade.offer.brick*(-1) > this.clientModel.players[this.receiverIndex].resources.brick) 
								this.acceptView.setAcceptEnabled(false);
							else
								this.acceptView.setAcceptEnabled(true);
							this.acceptView.addGetResource('brick', this.trade.offer.brick);
						}
						if(this.trade.offer.sheep > 0)
						{
							this.acceptView.addGiveResource('sheep', this.trade.offer.sheep);
						}
						if(this.trade.offer.sheep < 0)
						{
							if(this.trade.offer.sheep*(-1) > this.clientModel.players[this.receiverIndex].resources.sheep) 
								this.acceptView.setAcceptEnabled(false);
							else
								this.acceptView.setAcceptEnabled(true);
							this.acceptView.addGetResource('sheep', this.trade.offer.sheep);
						}
						if(this.trade.offer.ore > 0)
						{
							this.acceptView.addGiveResource('ore', this.trade.offer.ore);
						}
						if(this.trade.offer.ore < 0)
						{
							if(this.trade.offer.ore*(-1) > this.clientModel.players[this.receiverIndex].resources.ore) 
								this.acceptView.setAcceptEnabled(false);
							else
								this.acceptView.setAcceptEnabled(true);
							this.acceptView.addGetResource('ore', this.trade.offer.ore);
						}
						if(this.trade.offer.wheat > 0)
						{
							this.acceptView.addGiveResource('wheat', this.trade.offer.wheat);
						}
						if(this.trade.offer.wheat < 0)
						{
							if(this.trade.offer.wheat*(-1) > this.clientModel.players[this.receiverIndex].resources.wheat) 
								this.acceptView.setAcceptEnabled(false);
							else
								this.acceptView.setAcceptEnabled(true);
							this.acceptView.addGetResource('wheat', this.trade.offer.wheat);
						}
			
						this.acceptView.showModal();
					}
				}
			}
			else
			{							
				this.view.setPlayerSelectionEnabled(true);
				//this.waitingView.closeModal();
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


