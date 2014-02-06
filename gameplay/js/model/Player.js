/**
    The Player class contains all objects needed to send to the client for a single game
    <pre>
    Domain:
        MAX_GAME_POINTS: int = 10
        resources: The resource cards this player has, ResourceList
        oldDevCards: The dev cards the player had when the turn started, DevCardList
        newDevCards: The dev cards the player bought this turn, DevCardList
        roads: Number of roads, int
        cities: Number of cities, int
        settlements: Number of settlements, int
        soldiers: Number of soldiers, int
        victoryPoints: Number of Victory Points, int
        monuments: Number of monuments, int
        longestRoad: Whether the player has the "Longest Road" card, boolean
        largestArmy: Whether the player has the "Largest Army" card, boolean
        playedDevCard: Whether the player has played a dev card this turn, boolean
        discarded: Whether the player has discarded or not already this discard phase, boolean
        playerID: Unique player ID to pick this client player apart from others, int
        orderNumber: Number in the turn order (0-3), int
        name: Player name, string
        color: Player color, string

    Invariants:
        INVARIANT: Must have the ID of a valid Client with a name
    
    Constructor Specification:
        PRE: !isNaN(ClientID)
        PRE: !idNull(ClientName)
        PRE: !isNull(ClientColor)
        POST: All variables initialized

    </pre>
    @class Player
    @constructor

    @param {Object} player, 
 */
function Player(player) {

    this.MAX_GAME_POINTS = player.MAX_GAME_POINTS;
    this.resources = new ResourceList(player.resources);
    this.newDevCards = new DevCardList(player.newDevCards);
    this.oldDevCards = new DevCardList(player.oldDevCards);

    this.roads = player.roads;
    this.cities = player.cities;
    this.settlements = player.settlements;
    this.soldiers = player.soldiers;
    this.victoryPoints = player.victoryPoints;
    this.monuments = player.monuments;

    this.longestRoad = player.longestRoad;
    this.largestArmy = player.largestArmy;
    this.playedDevCard = player.playedDevCard;
    this.discarded = player.discarded;

    this.playerID = player.playerID;
    this.orderNumber = player.orderNumber;

    this.name = player.name;
    this.color = player.color;
}

/**
    <pre>
        POST: Correctly returns if the player can afford to purchase a Road
    </pre>
    @method canAffordRoad
    @return {boolean} Whether the player can afford to buy a Road
*/
Player.prototype.canAffordRoad = function() {

    if (resources.canAffordRoad())
        return true;
    return false;
}

/**
    <pre>
        POST: Correctly returns if the player can afford to purchase a Settlement
    </pre>
    @method canAffordSettlement
    @return {boolean} Whether the player can afford to buy a Settlement
*/
Player.prototype.canAffordSettlement = function() {

    if (resources.canAffordSettlement())
        return true;
    return false;
}

/**
    <pre>
        POST: Correctly returns if the player can afford to purchase a City
    </pre>
    @method canAffordCity
    @return {boolean} Whether the player can afford to buy a City
*/
Player.prototype.canAffordCity = function() {

    if (resources.canAffordCity())
        return true;
    return false;
}

/**
    <pre>
        POST: Correctly returns if the player can afford to purchase a Development Card
    </pre>
    @method canAffordDevCard
    @return {boolean} Whether the player can afford to buy a Development Card
*/
Player.prototype.canAffordDevCard = function() {

    if (resources.canAffordDevCard())
        return true;
    return false;
}

/**
    <pre>
        PRE: Player can afford the Road 
        POST: IF TRUE: Player loses the resource cards required to make the purchase
        POST: IF TRUE: Player gains the Road
        POST: IF FALSE: Player data does not change
    </pre>
    @method buyRoad
    @return {boolean} Whether buying the Road was successful
*/
Player.prototype.buyRoad = function() {

    if (!resources.canAffordRoad())
        return false;
    // TODO: Buy the road


    resources.decrementRoad();
}

/**
    <pre>
        PRE: Player can afford the Settlement
        POST: IF TRUE: Player loses the resource cards required to make the purchase
        POST: IF TRUE: Player gains the Settlement
        POST: IF FALSE: Player data does not change
    </pre>
    @method buySettlement
    @return {boolean} Whether buying the Settlement was successful
*/
Player.prototype.buySettlement = function() {

    if (!resources.canAffordSettlement())
        return false;
    // TODO: Buy the settlement


    resources.decrementSettlement();
}

/**
    <pre>
        PRE: Player can afford the City
        POST: IF TRUE: Player loses the resource cards required to make the purchase
        POST: IF TRUE: Player gains the City
        POST: IF FALSE: Player data does not change
    </pre>
    @method buyCity
    @return {boolean} Whether buying the City was successful
*/
Player.prototype.buyCity = function() {

    if (!resources.canAffordCity())
        return false;
    // TODO: Buy the city


    resources.decrementCity();
}

/**
    <pre>
        PRE: Player can afford the Development Card
        POST: IF TRUE: Player loses the resource cards required to make the purchase
        POST: IF TRUE: Player gains the Development Card
        POST: IF FALSE: Player data does not change
    </pre>
    @method buyDevCard
    @return {boolean} Whether buying the Development Card was successful
*/
Player.prototype.buyDevCard = function() {

    if (!resources.canAffordDevCard())
        return false;
    // TODO: Buy the Development Card


    resources.decrementDevCard();
}

/**

*/
Player.prototype.discardCards = function(resourceHand) {


}