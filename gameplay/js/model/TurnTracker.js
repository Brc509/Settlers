/**
The Turn Tracker Class 
<pre>
Domain:
            currentTurn: unique id number of player
            status: string to show status
            
        Invariants:
            INVARIANT: player ID exists
         
        Constructor Specification:
            PRE: currentTurn is a player's ID
            POST: getCurrentTurn() == currentTurn
            POST: getStatus() == status
</pre>
@class TurnTracker 
@constructor
@param {:currentTurn} (int)The ID of the player whose turn it is.
@param {:status} (String)The status of the player whose turn it is.
*/
function TurnTracker(currentTurn, status) {
    this.currentTurn = currentTurn;
    this.status = status;
}

/**
<pre>
A currentTurn player ID needs to be defined
</pre>
@method getCurrentTurn
@return {int} The current turn's player ID.
*/
TurnTracker.prototype.getCurrentTurn = function() {
    return this.currentTurn;
}

/**
<pre>
A currentTurn player needs to have a status defined
</pre>
@method getStatus
@return {String} The status of the player whose turn it is.
*/
TurnTracker.prototype.getStatus = function() {
    return this.status;
}