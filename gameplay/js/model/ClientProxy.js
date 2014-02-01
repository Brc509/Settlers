/**
The Client Proxy Class 
<pre>
Domain:
            clientModel: the class where the game model is parsed into
            gameServer: the server that game commands are sent to
            
        Invariants:
            INVARIANT: clientModel exists
			INVARIANT: gameServer exists
         
        Constructor Specification:
            PRE: clientModel is not null
            PRE: gameServer is not null
            POST: userLogin() == updated game model (JSON)
            POST: userRegister() == updated game model (JSON)
</pre>
@class ClientProxy 
@constructor
@param {:clientModel} The x coordinate of the Hex tile.
@param {:gameServer} The y coordinate of the Hex tile.
*/
function ClientProxy(clientModel, gameServer) {
    this.clientModel = clientModel;
    this.gameServer = gameServer;
}

/**
<pre>
Must be allowed to place a road before executing
</pre>
@method placeRoad
@return {JSON} The updated game model(JSON).
*/
ClientProxy.prototype.placeRoad = function(){

}

/**
<pre>
Must be allowed to place a settlement before executing
</pre>
@method placeSettlement
@return {JSON} The updated game model(JSON).
*/
ClientProxy.prototype.placeSettlement = function(){

}

/**
<pre>
Must be allowed to place a city before executing
</pre>
@method placeCity
@return {JSON} The updated game model(JSON).
*/
ClientProxy.prototype.placeCity = function(){

}

/**
Store game model JSON in Client model
<pre>
Must have a valid game model JSON
</pre>
@method storeGameModel
@param {JSON} The updated game model(JSON).
@return none
*/
ClientProxy.prototype.storeGameModel = function(){

}

/**
Retrieve current game model JSON from server at regular intervals
<pre>
Must have a valid game model JSON
</pre>
@method updateGameModel
@param {JSON} The updated game model(JSON).
@return none
*/
ClientProxy.prototype.updateGameModel = function(){

}

/**
<pre>
username and password greater than two characters
</pre>
@method userLogin
@param {:username} name of user.
@param {:password} user's password.
@return {JSON} The current game model(JSON).
*/
ClientProxy.prototype.userLogin = function(username, password){
        this.username = username;
        this.password = password;
}

/**
<pre>
username and password greater than two characters
</pre>
@method	userRegister
@param {:username} name of user.
@param {:password} user's password.
@return {JSON} The current game model(JSON).
*/
ClientProxy.prototype.userRegister = function(username, password){
    VALID INPUT -- but only the first time you try it with the name 
        this.username = username;
        this.password = password;
}

/**
<pre>
None
</pre>
@method	gameList
@return {JSON} The games list(JSON).
*/
ClientProxy.prototype.gameList = function(){

}
    
/**
<pre>
Registered user must initiate this request
</pre>
@method	gameCreate
@param {:randomTiles} (boolean)set this feature to T/F.
@param {:randomNumbers} (boolean)set this feature to T/F.
@param {:randomPorts} (boolean)set this feature to T/F.
@param {:name} name of new game.
@return {JSON} new game created(JSON).
*/
ClientProxy.prototype.gameCreate = function(){

}

/**
<pre>
Player must be registered and logged in before executing
</pre>
@method	gameJoin
@param {:color} color that represents player.
@param {:id} the id number of game to join.
@return {JSON} The current game model(JSON).
*/
ClientProxy.prototype.gameJoin = function(color, id){

}

/**
<pre>
Registered user must initiate this request
</pre>
@method	gameModel
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.gameModel = function(){

}

/**
<pre>
Registered user must initiate this request
</pre>
@method	gameReset
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.gameReset = function(){

}

/**
<pre>
gameReset() needs to be run before executing this method and a valid JSON of game commands needs to be provided
</pre>
@method	gameCommandsSend
@param {JSON} set of commands for server to run
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.gameCommandsSend = function(JSON){

}					   

/**
<pre>
None
</pre>
@method	gameCommands
@return {JSON} The list of commands run by Player during gameplay(JSON).
*/
ClientProxy.prototype.gameCommands = function(){

}

/**
<pre>
a valid player index number and message content needs to be serialized in JSON format.
</pre>
@method	gameSendChat
@param {JSON} properly formatted JSON that contains the Player's index number and message content
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.gameSendChat = function(JSON){

}

/**
<pre>
Method is executed by player with current turn.
a valid player index number and number needs to be serialized in JSON format.
</pre>
@method	movesRollNumber
@param {JSON} properly formatted JSON that contains the Player's index number and a number
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.movesRollNumber = function(JSON){

}

/**
<pre>
Method is executed by player with current turn
</pre>
@method	movesFinishTurn
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.movesFinishTurn = function(){

}

/**
<pre>
Method is executed by player with current turn.
a valid player index number needs to be serialized in JSON format.
</pre>
@method	movesBuyDevCard
@param {JSON} properly formatted JSON that contains the Player's index number.
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.movesBuyDevCard = function(JSON){

}

/**
<pre>
Method is executed by player with current turn.
a valid player index number and two valid resource cards need to be serialized in JSON format.
</pre>
@method	movesYearOfPlenty
@param {JSON} properly formatted JSON that contains the Player's index number and two valid resource cards.
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.movesYearOfPlenty = function(JSON){

}

/**
<pre>
Method is executed by player with current turn.
a valid player index number, two valid spots with x and y coordinates, and direction need to be serialized in JSON format.
</pre>
@method	movesRoadBuilding
@param {JSON} properly formatted JSON that contains the Player's index number and two valid spots with x and y coordinates, and direction.
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.movesRoadBuilding = function(JSON){

}

/**
<pre>
Method is executed by player with current turn.
a valid player index number, a victim player index number, and a valid robber spot with x and y coordinates need to be serialized in JSON format.
</pre>
@method	movesSoldier
@param {JSON} properly formatted JSON that contains the Player's index number, victim player's index number, and a valid robberSpot with x and y coordinates.
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.movesSoldier = function(JSON){

}

/**
<pre>
Method is executed by player with current turn.
a valid player index number and a valid resource card need to be serialized in JSON format.
</pre>
@method	movesMonopoly
@param {JSON} properly formatted JSON that contains the Player's index number and a valid resource card.
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.movesMonopoly = function(JSON){

}

/**
<pre>
Method is executed by player with current turn.
a valid player index number needs to be serialized in JSON format.
</pre>
@method	movesMonument
@param {JSON} properly formatted JSON that contains the Player's index number.
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.movesMonument = function(JSON){

}

/**
<pre>
Method is executed by player with current turn.
Player must be allowed to place a road according to game rules.
a valid player index number, a valid roadLocation with x, y, and direction parameters, and a free cost boolean variable need to be serialized in JSON format.
</pre>
@method	movesBuildRoad
@param {JSON} properly formatted JSON that contains a valid player index number, a valid roadLocation with x, y, and direction parameters, and a free cost boolean variable need to be serialized in JSON format.
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.movesBuildRoad = function(JSON){

}

/**
<pre>
Method is executed by player with current turn.
Player must be allowed to place a settlement according to game rules.
a valid player index number, a valid vertexLocation with x, y, and direction parameters, and a free cost boolean variable need to be serialized in JSON format.
</pre>
@method	movesBuildSettlement
@param {JSON} properly formatted JSON that contains a valid player index number, a valid vertexLocation with x, y, and direction parameters, and a free cost boolean variable need to be serialized in JSON format.
@return {JSON} The game's current model(JSON).
*/		
ClientProxy.prototype.movesBuildSettlement = function(){

}

/**
<pre>
Method is executed by player with current turn.
Player must be allowed to place a city according to game rules.
a valid player index number, a valid vertexLocation with x, y, and direction parameters, and a free cost boolean variable need to be serialized in JSON format.
</pre>
@method	movesBuildCity
@param {JSON} properly formatted JSON that contains a valid player index number, a valid vertexLocation with x, y, and direction parameters, and a free cost boolean variable need to be serialized in JSON format.
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.movesBuildCity = function(JSON){

}

/**
<pre>
Player must registered, logged in, and joined to a game.
a valid player index number, a list of resources and their respective quantities, and a receiver player number need to be serialized in JSON format.
</pre>
@method	movesOfferTrade
@param {JSON} properly formatted JSON that contains a valid player index number, a list of resources and their respective quantities, and a receiver player number.
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.movesOfferTrade = function(JSON){

}

/**
<pre>
Player must registered, logged in, and joined to a game.
A movesOfferTrade needs to have been executed before movesAcceptTrade can be executed.
a valid player index number and a boolean that indicates if the player will accept the trade need to be serialized in JSON format.
</pre>
@method	movesAcceptTrade
@param {JSON} properly formatted JSON that contains a valid player index number and a boolean that indicates if the player will accept the trade.
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.movesAcceptTrade = function(JSON){

}

/**
<pre>
Method is executed by player with current turn
</pre>
@method	movesDiscardCards
@return {JSON} The game's current model(JSON).
*/
ClientProxy.prototype.movesDiscardCards = function(){

}