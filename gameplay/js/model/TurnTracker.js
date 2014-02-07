var catan = catan || {};
catan.models = catan.models || {};

catan.models.TurnTracker = (function () {

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
@param {integer} playerId The ID of the player that owns this model
*/
    var TurnTracker = (function TurnTrackerClass() {

        function TurnTracker(playerId) {

            this.playerId = playerId;
        }

        /**
            <pre>
                PRE: newTurnTracker contains the two necessary values
                POST: This object's local variables are set to the newTurnTracker's variables' values
            </pre>
            @method update
            @param {Object} newTurnTracker An object containing new values for this TurnTracker
        */
        TurnTracker.prototype.update = function(newTurnTracker) {

            this.currentTurn = newTurnTracker.currentTurn;
            this.status = newTurnTracker.status;
            if (this.currentTurn == this.playerId)
                this.isCurrentTurn = true;
        }

        /**
            <pre>
                PRE: update() has already been called, and "this.status" has been defined
                PRE: queryStatus is a string
                POST: Correctly returns whether or not the queryStatus equals this.status
            </pre>
            @method statusEquals
            @param {String} queryStatus The string to compare to the current status
            @return {boolean} Whether or not the queryStatus is that same string as the current status
        */
        TurnTracker.prototype.statusEquals = function(queryStatus) {

            if (this.status == queryStatus)
                return true;
            return false;
        }

        /**
            <pre>
                PRE: update() has already been called, and "this.isCurrentTurn" has been defined
                POST: Correctly returns whether or not this.currentTurn == this.playerId
            </pre>
            @method isMyTurn
            @return {boolean} 
        */
        TurnTracker.prototype.isMyTurn = function() {

            return isCurrentTurn;
        }

        /**
            <pre>
                PRE: playerIs is a valid integer ID number
                POST: Correctly returns if it is currently this player's turn
            </pre>
            @method isTurn
            @param {integer} playerId The ID of the player to check 
        */
        TurnTracker.prototype.isTurn = function(playerId) {

            if (this.playerId == playerId)
                return true;
            return false;
        }

        return TurnTracker;
    }());

    return TurnTracker;
}());