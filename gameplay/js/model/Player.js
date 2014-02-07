var catan = catan || {};
catan.models = catan.models || {};


catan.models.Player  = (function clientModelNameSpace(){
    /** 
    This the top-level client model class that contains the local player, map contents, etc.
    
    @class ClientModel
    @constructor
    @param {integer} playerID The id of the local player, extracted from the cookie
    */
    

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
    var Player = (function PlayerClass(){
        
        function Player(){ }

        /**
            <pre>
                PRE: newPlayer contains all of the necessary values to update
                POST: All local player values are successfully updated
            </pre>
        */
        Player.prototype.update = function(newPlayer) {

            this.MAX_GAME_POINTS = player.MAX_GAME_POINTS;
            this.resources = new ResourceList();
            this.resources.update(player.resources);
            this.newDevCards = new DevCardList();
            this.newDevCards.update(player.newDevCards);
            this.oldDevCards = new DevCardList();
            this.oldDevCards.update(player.oldDevCards);

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
                PRE: The parameter resourceList contains five values that are all positive integers
                POST: Correctly returns true if the player has the specified resources
            </pre>
            @hasResources
            @param {Object} resourceList A list of 5 integers to represent the number of each resource
            @return {boolean} If the player's resources contain the requested amounts
        */
        Player.prototype.hasResources = function(resourceList) {

            hasBrick    = (resources.brick <= resourceList.brick);
            hasOre      = (resources.ore   <= resourceList.ore);
            hasSheep    = (resources.sheep <= resourceList.sheep);
            hasWheat    = (resources.wheat <= resourceList.wheat);
            hasWood     = (resources.wood  <= resourceList.wood);

            return (hasBrick && hasOre && hasSheep && hasWheat && hasWood);
        }

        return Player;
    }());

        return Player;
}());   