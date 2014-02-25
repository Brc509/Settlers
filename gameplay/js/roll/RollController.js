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

			// view.showModal();

			
	};
        
		/**
		 * This is called from the roll result view.  It should close the roll result view and allow the game to continue.
		 * @method closeResult
		 * @return void
		**/
		RollController.prototype.closeResult = function(){
			this.showRollResult = false;
			this.rollResultView.closeModal();
			this.ClientModel.rollNumber(value);
		}
		
		/**
		 * This method generates a dice roll
		 * @method rollDice
		 * @return void
		**/
		RollController.prototype.rollDice = function(){

			
			var numba1=Math.floor(Math.random()*7)
			var numba2=Math.floor(Math.random()*7)

			value = numba1 + numba2;

			if(value == 1){
				value = 2;
			}

			this.View.closeModal();
			this.showRollResult = true;
			this.rollResultView.amountDisplay.textContent = "You rolled a " + value;
			this.rollResultView.showModal();

		};

		RollController.prototype.update = function(){

			var currPlayerIndex = this.ClientModel.playerIndex;
			if(this.ClientModel.turnTracker.currentTurn == currPlayerIndex){
				if(this.ClientModel.turnTracker.status == "Rolling"){
					this.View.showModal();
				}
			}
			
			console.log("hi");

		}
		return RollController;
	}());
	
	return RollController;

}());

