/**
    The ClientModel class contains all objects needed to send to the client for a single game
    <pre>
    Domain:
        deck: Deck of all available development cards, DevCardList
        bank: List of all available resource cards (for player distribution), ResourceList
        biggestArmy: ID of the player with the largest army (or -1 if no one yet), Number
        chat: List of all the chat messages, MessageList
        log: List of all the log messages, MessageList
        longestRoad: ID of the player with the longest road (-1 for no one), Number
        map: The map for this game, Map
        players: An array of all the players for this game, array[Player]
        tradeOffer: The current trade offer (if there is one), TradeOffer
        turnTracker: Tracks who's turn it is and what phase of the turn they're at, TurnTracker
        winner: ID of the player who wins (-1 for no one), Number

    Invariants:
        INVARIANT: The parameter player is not a null Player
    
    Constructor Specification:
        PRE: !isNull(Player)
        POST: All variables initialized

    </pre>
    @class ClientModel
    @constructor

    @param {Player} Host The creator of the game
 */
function ClientModel(hostPlayer) {

}

/**
    <pre>
        POST: Chat correctly contains the client's message
    </pre>
    @method sendChat
    @param (string) The client's message
*/
ClientModel.prototype.sendChat = function (message) {

}

/**
    <pre>
        PRE: Client's status is "rolling"
        PRE: It is currently the client's turn
        POST: Client's status is now "discarding", "robbing", or "playing"
    </pre>
    @method rollNumber
    @return (Number) A number between 2 and 12
*/
ClientModel.prototype.rollNumber = function () {

}

/**
    <pre>
        PRE: Client's current status is "Playing"
        POST: It is no longer the client's turn
    </pre>
    @method finishTurn
*/
ClientModel.prototype.finishTurn = function () {

}

/**
    <pre>
        PRE: You have the necessary resources (1 ore, sheep, and wheat)
        PRE: There are still available Dev cards (in the deck)
        POST: Person now has the dev card (in the dev card hand)
    </pre>
    @method buyDevCard
*/
ClientModel.prototype.buyDevCard = function () {

}

/**
    <pre>
        PRE: Client's turn and status is "Playing"
        PRE: Person hasn't played a dev card yet this turn
        PRE: Client's two specified resources are in the bank
        POST: Person now has the two specified resources
    </pre>
    @method yearOfPlenty
*/
ClientModel.prototype.yearOfPlenty = function () {

}

/**
    <pre>
        PRE: Client's turn and status is "Playing"
        PRE: Person hasn't played a dev card yet this turn
        PRE: First road location is connected to one of the client's roads
        PRE: Second road location is connected either to one of the client's roads or to the first road location
        PRE: Neither road location is on water
        PRE: Person has two roads
        POST: Person uses two roads
        POST: The map registered the road successfully
    </pre>
    @method roadBuilding
    @param (EdgeLocation) spot1, The first of the new road locations
    @param (EdgeLocation) spot2, the second of the new road locations
*/
ClientModel.prototype.roadBuilding = function (spot1, spot2) {

}

/**
    <pre>
        PRE: Robber has moved
        PRE: The victim has cards (or -1 if can't rob anyone)
        POST: Robber is in a new location
        POST: The victim gives one random resource card to the soldier
    </pre>
    @method soldier
    @param HexLocation robberSpot, location of the robber
    @param PlayerIndex victimIndex, index of the player being robbed
*/
ClientModel.prototype.soldier = function (robberSpot, victimIndex) {

}

/**
    <pre>
        POST: All other players lose the resource type that's chosen
        POST: The player of the card gets an equal number
    </pre>
    @method monopoly
*/
ClientModel.prototype.monopoly = function () {

}

/**
    <pre>
        PRE: CAN be played on the turn that you buy it
        POST: You gain one victory point
    </pre>
    @method monument
*/
ClientModel.prototype.monument = function () {

}

/**
    <pre>
        PRE: Person has the necessary resources
        POST: Trade if offered to the other player (in their model)
    </pre>
    @method offerTrade
    @param ResourceList offer, pos numbers are traded away, negative numbers are received
    @param PlayerIndex receiver, The recipient of the trade
*/
ClientModel.prototype.offerTrade = function (offer, receiver) {

}

/**
    <pre>
        PRE: You were offered the trade
        PRE: You have the resources necessary to accept
        POST: If Accept, the resources are swapped
        POST: If Declined, the resources are unchanged
        POST: The trade offer is removed (either way)
    </pre>
    @method acceptTrade
    @param boolean willAccept, whether or not the player will accept the offer
*/
ClientModel.prototype.acceptTrade = function () {

}

/**
    <pre>
        PRE: Player's current state is "Discarding"
        PRE: Player has more than 7 cards
        PRE: Player has all the cards that they are choosing to discard
        POST: If player is last one to discard, client's model status is now "Robbing"
        POST: Player lost the resources that they discarded
    </pre>
    @method discardCards
    @param ResourceHand discardedCards, The cards being discarded
*/
ClientModel.prototype.discardCards = function (discardedCards) {

}