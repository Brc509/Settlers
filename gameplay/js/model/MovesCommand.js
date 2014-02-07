// MovesCommand.js
// Author: Spencer Bench

var catan = catan || {};
catan.models = catan.models || {};

catan.models.MovesCommand = (function() {

	var Command = catan.models.Command;
	
	// Define model update callback function
	MovesCommand.prototype.MODEL_UPDATE_FUNCTION = catan.models.ClientModel.updateModel;
	
	// Define API endpoint constants
	MovesCommand.prototype.ACCEPT_TRADE_URL			= '/moves/acceptTrade';
	MovesCommand.prototype.BUILD_CITY_URL			= '/moves/buildCity';
	MovesCommand.prototype.BUILD_ROAD_URL			= '/moves/buildRoad';
	MovesCommand.prototype.BUILD_SETTLEMENT_URL		= '/moves/buildSettlement';
	MovesCommand.prototype.BUY_DEV_CARD_URL			= '/moves/buyDevCard';
	MovesCommand.prototype.DISCARD_CARDS_URL		= '/moves/discardCards';
	MovesCommand.prototype.FINISH_TURN_URL			= '/moves/finishTurn';
	MovesCommand.prototype.MARITIME_TRADE_URL		= '/moves/maritimeTrade';	// Undocumented?
	MovesCommand.prototype.MONOPOLY_URL				= '/moves/Monopoly';
	MovesCommand.prototype.MONUMENT_URL				= '/moves/Monument';
	MovesCommand.prototype.OFFER_TRADE_URL			= '/moves/offerTrade';
	MovesCommand.prototype.ROAD_BUILDING_URL		= '/moves/Road_Building';
	MovesCommand.prototype.ROB_PLAYER_URL			= '/moves/robPlayer';		// Undocumented?
	MovesCommand.prototype.ROLL_NUMBER_URL			= '/moves/rollNumber';
	MovesCommand.prototype.SEND_CHAT_URL			= '/moves/sendChat';
	MovesCommand.prototype.SOLDIER_URL				= '/moves/Soldier';
	MovesCommand.prototype.YEAR_OF_PLENTY_URL		= '/moves/Year_of_Plenty';

	/**
		The MovesCommand class represents any command which modifies the state of the game and returns the game model.
		<pre>
		Domain:
			data: The object to send in the body of the request, object
			
		Constructor Specification:
			PRE: url is a valid server endpoint
		</pre>
		
		@class MovesCommand
		@extends Command
		@constructor
		
		@param {string} url The server endpoint that the command is sent to
		@param {object} data The data to send to the server
	*/
	function MovesCommand(url, data) {
		// Call the Command constructor
		Command.call(this, url, MODEL_UPDATE_FUNCTION);
		this.data = data;
	}
	MovesCommand.prototype = Object.create(Command.prototype);
	MovesCommand.prototype.constructor = MovesCommand;
	
	/**
		Sends data as JSON to the server and updates the client model on response.
		
		@method execute
	*/
	MovesCommand.prototype.execute = function() {
		jQuery.post(url, JSON.stringify(data), callback);
	};
	
	return MovesCommand;
	
})();
