//STUDENT-EDITABLE-BEGIN
/**
    This is the namespace the rolling interface
    @module catan.roll
    @namespace roll
*/

var catan = catan || {};
catan.roll = catan.roll || {};

catan.roll.Controller = (function roll_namespace(){

	var Controller = catan.core.BaseController;
	var value;
	var timeout;
    
	/**
		 * @class RollController
		 * @constructor
		 * @extends misc.BaseController
		 * @param{roll.View} view
		 * @param{roll.ResultView} resultView
		 * @param{models.ClientModel} clientModel
		 */
	var RollController = (function RollController_Class(){
		
		core.forceClassInherit(RollController,Controller);
 
		core.defineProperty(RollController.prototype,"rollResultView");
		
		function RollController(view,resultView, clientModel){
			this.setRollResultView(resultView);
			Controller.call(this,view,clientModel);
			this.rollInterval = false;
			this.showRollResult = false;
	};
        
		/**
		 * This is called from the roll result view.  It should close the roll result view and allow the game to continue.
		 * @method closeResult
		 * @return void
		**/
		RollController.prototype.closeResult = function(){
			this.showRollResult = false;
			this.rollResultView.closeModal();
			this.ClientModel.isModalUp = false;

			if(this.ClientModel.turnTracker.status == "Robbing" && value == 7 && !this.ClientModel.isModalUp){
				this.displayRobber();
			}
		}
		
		/**
		 * This method generates a dice roll
		 * @method rollDice
		 * @return void
		**/
		RollController.prototype.rollDice = function(){

			clearTimeout(timeout);

			this.View.closeModal();
			this.ClientModel.isModalUp = false;
			this.showRollResult = true;
			
			if(!this.ClientModel.isModalUp){
				
				var numba1=Math.floor((Math.random()*6) + 1);
            	var numba2=Math.floor((Math.random()*6) + 1);

				value = numba1 + numba2;
				if (value == 7) value = 6;
				
				this.rollResultView.amountDisplay.textContent = "You rolled " + value;

				this.rollResultView.showModal();
				this.ClientModel.isModalUp = true;
				this.ClientModel.rollNumber(value);

			}

		};

		RollController.prototype.update = function(){

			var currPlayerIndex = this.ClientModel.playerIndex;
			var status = this.ClientModel.turnTracker.status;

			if(this.ClientModel.turnTracker.currentTurn == currPlayerIndex){
				if(status == "Rolling" && !this.ClientModel.isModalUp){
					
					this.View.showModal();
					this.ClientModel.isModalUp = true;
					
					this.countdown();
		
				}else if((!this.ClientModel.isModalUp && status == "Robbing") || 
					(!this.ClientModel.isModalUp && status == "Robbing" && value == 7)){
					this.displayRobber();
			}
		}
	}

	RollController.prototype.countdown = function(){
		myself = this;
		var str = "Rolling automatically in... ";
					
		this.View.MessageElem.innerText = str;
					
		var count=5;
		myself.View.MessageElem.innerText += count;
		timeout = setInterval(timer, 1000); 

		function timer(){
  			count=count-1;
  			if (count <= 0){
     						
     			clearInterval(timeout);
				myself.rollDice();
     			return;
  						
  			}

		str = str.substring(0, str.length - 1);
		myself.View.MessageElem.innerText = (str += count);
		}				
	}

	RollController.prototype.displayRobber = function(){

		this.ClientModel.isModalUp = true;
		this.ClientModel.observers[2].getModalView().showModal("robber");
		this.ClientModel.observers[2].getView().startDrop("robber");	
	
	}
		return RollController;
	}());
	
	return RollController;

}());

