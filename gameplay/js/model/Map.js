/** 
	The Map Class represents the board and holds all of the hexagons
	<pre>
        Domain:
            hexGrid: The gameboard grid of hexagons, HexGrid
            numbers: List of possible numbers that can be associated with each Hex, TokenList
            ports: List of all the ports on the game board, array[Port]
            radius: Radius of the map (including the center hex and ocean hexes), int
            lastRobber: Last location of the robber, HexLocation
            robber: Current location of the robber, HexLocation

        Invariants:
            INVARIANT: Map must belong to a game

        Constructor Specifications:
            PRE: None
            POST: All above variables initialized
            
    </pre>
	@class Map
    @constructor
*/
function Map() {

}

/**
    <pre>
        PRE: location1 and location2 are legitimate locations on the HexGrid
        POST: If true, The space is actually unoccupied and fits all criteria to be a valid placement
        POST: If false, There is really a reason that the space cannot be occupied by that player's road
    </pre>
    @method canPlaceRoad
    @param location1, The first location of the road (first vertex)
    @param location2, The second location of the road
    @return boolean, Whether or not the road can be placed at the location
*/
function canPlaceRoad(location1, location2) {
    
}

/**
    Place the road in a specific position  
    <pre>
        PRE: Validated from canPlaceRoad()
        POST: The map object now includes this road for this player
    </pre>
        
    @method	placeRoad
    @param 	{location}	where the road will be placed
	@param	{playerID}	ID of the player who is placing the road
	@return	{boolean}	returns true if road is placed.  false otherwise
*/
function placeRoad(location, playerID) {
    
}

/**
    <pre>
        PRE: The settlement location is a valid location on the map
        POST: If true, the settlement can really be placed on the given location for the given player
        POST: If false, the settlement can not be placed on the location for a legitimate reason
    </pre>
    @method canPlaceSettlement
    @param location, The location of the settlement to check
    @return boolean, Whether or not the settlement can be placed in the current location
*/
function canPlaceSettlement(location, playerID) {
}

/**
     Place the settlement in a specific position  
    <pre>
        PRE: Validated from the canPlaceSettlement() method
        POST: The map object now contains the given settlement at the location
    </pre>
        
    @method	placeSettlement
    @param 	{location}	where the settlement will be placed
	@param	{playerID}	ID of the player who is placing the settlement
	@return	{boolean}	returns true if settlement is placed.  false otherwise
*/
function placeSettlement(location, playerID) {
    
}

/**
    <pre>
        PRE: The location is a legitimate location on the map
        POST: If true, the city can really be placed at the given location
        POST: If false, the city can not be placed at the location for a legitimate reason
    </pre>
    @canPlaceCity
    @return boolean, Whether or not the city can be placed at the given location
*/
function canPlaceCity(location, playerID) {
}

/**
    Place the city in a specific position
    <pre>
        PRE: Validated from the canPlaceCity() method
        POST: The map object now contains a city at the given location
    </pre>
        
    @method	placeCity
    @param 	{location}	where the city will be placed
	@param	{playerID}	ID of the player who is placing the city
	@return	{boolean}	returns true if city is placed.  false otherwise
*/
function placeCity(location, playerID) {
    
}

/**
	Move the robber to a specified position and set robber’s position
	<pre>
        PRE: Ensure that the robber's location is changed from the previous location
        POST: The robber's location has actually changed to a valid location
	</pre>
	@method	moveRobber
	@param {location}	where the robber is moving to
*/
function moveRobber(location) {
    
}