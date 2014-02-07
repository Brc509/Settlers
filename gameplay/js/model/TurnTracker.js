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
function TurnTracker(newTurnTracker) {
    this.currentTurn = newTurnTracker.currentTurn;
    this.status = newTurnTracker.status;
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
    PRE: A currentTurn player needs to have a status defined
</pre>
@method getStatus
@return {String} The status of the player whose turn it is.
*/
TurnTracker.prototype.getStatus = function() {
    return this.status;
}

/**
    <pre>
        PRE: queryStatus is a valid int with value in the range of 0-3
        POST: Correctly returned true if the two statuses match up
    </pre>
    @method isStatus
    @return {boolean} Whether the current status matches queryStatus
*/
TurnTracker.prototype.isStatus = function(queryStatus) {

    if (this.status == queryStatus)
        return true;
    return false;
}

/**
    <pre>
        PRE: queryCurrentTurn is a valid string
        POST: Correctly returned true if the two currentTurn strings match up
    </pre>
    @method isCurrentTurn
    @return {boolean} Whether the "currentTurn" String matches the parameter "queryCurrentTurn"
*/

TurnTracker.prototype.isCurrentTurn = function(queryCurrentTurn) {

    if (this.currentTurn = queryCurrentTurn)
        return true;
    return false;
}