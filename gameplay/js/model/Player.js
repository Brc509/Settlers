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

    @param clientID, The unique player ID of this client
    @param clientName, The player's name
    @param clientColor, The color that this client selected
 */
function Player(newPlayer) {

    this.MAX_GAME_POINTS = player.MAX_GAME_POINTS;
    this.resources = ResourceList(player.resources);
    this.newDevCards = DevCardList(player.newDevCards);
    this.oldDevCards = DevCardList(player.oldDevCards);

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
        PRE: Player has the permissions to access this info
    </pre>
    @method getResources
    @return ResourceList, The player's resource list
*/
Player.prototype.getResources = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method getOldDevCards
    @return DevCardList, The player's DevCardList from the beginning of the turn
*/
Player.prototype.getOldDevCards = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method getNewDevCards
    @return DevCardList, The player's current DevCardList
*/
Player.prototype.getNewDevCards = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method getRoadsCount
    @return int, The player's number of roads
*/
Player.prototype.getRoadsCount = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method getCitiesCount
    @return int, The player's number of cities
*/
Player.prototype.getCitiesCount = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method getSettlementsCount
    @return int, The player's number of settlements
*/
Player.prototype.getSettlementsCount = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method getSoldiersCount
    @return int, The player's number of soldiers
*/
Player.prototype.getSoldiersCount = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method getVictoryPoints
    @return int, The player's number of victory points
*/
Player.prototype.getVictoryPoints = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method getMonumentsCount
    @return int, The player's number of monuments
*/
Player.prototype.getMonumentsCount = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method hasLongestRoad
    @return boolean, Whether or not this player currently has the "Longest Road" card
*/
Player.prototype.hasLongestRoad = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method hasLargestArmy
    @return boolean, Whether or not this player currently has the "Largest Army" card
*/
Player.prototype.hasLargestArmy = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method hasPlayedDevCard
    @return boolean, Whether or not this player has played a Dev card this turn
*/
Player.prototype.hasPlayedDevCard = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method hasDiscarded
    @return boolean, Whether or not this player has discarded this turn
*/
Player.prototype.hasDiscarded = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method getPlayerID
    @return int, The player's unique Player ID
*/
Player.prototype.getPlayerID = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method getOrderNumber
    @return int, The player's order number (0-3) for this game
*/
Player.prototype.getOrderNumber = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method getColor
    @return String, The player's color for this game
*/
Player.prototype.getColor = function () {

}

/**
    <pre>
        PRE: Player has the permissions to access this info
    </pre>
    @method getName
    @return String, The player's name
*/
Player.prototype.getName = function () {

}