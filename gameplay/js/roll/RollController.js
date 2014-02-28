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

			window.clearInterval(timeout);
			
			var numba1=Math.floor((Math.random()*6) + 1);
            var numba2=Math.floor((Math.random()*6) + 1);

			value = numba1 + numba2;
			// value = 7;

			this.View.closeModal();
			this.showRollResult = true;
			this.rollResultView.amountDisplay.textContent = "You rolled " + value;
			this.rollResultView.showModal();

		};

		RollController.prototype.update = function(){

			var currPlayerIndex = this.ClientModel.playerIndex;
			if(this.ClientModel.turnTracker.currentTurn == currPlayerIndex){
				if(this.ClientModel.turnTracker.status == "Rolling"){
					this.View.showModal();
					myself = this;
					var str = myself.View.MessageElem.innerText;
					console.log(str.length);
					
					this.View.MessageElem.innerText = "Rolling automatically in... ";

					this.View.MessageElem.innerText += "5";
					timeout = setTimeout(function(){
							str = str.substring(0, str.length - 1);
							str += "4";
							myself.View.MessageElem.innerText = str;
						timeout = setTimeout(function(){
							str = str.substring(0, str.length - 1);
							str += "3";
							myself.View.MessageElem.innerText = str;
							timeout = setTimeout(function(){
								str = str.substring(0, str.length - 1);
								str += "2";
								myself.View.MessageElem.innerText = str;
									timeout = setTimeout(function(){
									str = str.substring(0, str.length - 1);
									str += "1";
									myself.View.MessageElem.innerText = str;
										timeout = setTimeout(function(){
											str = str.substring(0, str.length - 1);
											myself.View.MessageElem.innerText = str;
											myself.rollDice();},1000);},1000);},1000);},1000);},1000);

				}
			}
		}
		return RollController;
	}());
	
	return RollController;

}());

