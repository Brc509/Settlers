/**
    The ResourceList class contains a list of each type of resource
    <pre>
    Domain:
        brick: Number of brick cards, Number
        ore: Number of ore cards, Number
        sheep: Number of sheep cards, Number
        wheat: Number of wheat cards, Number
        wood: Number of wood cards, Number

    Invariants:
        INVARIANT: The numbers of each resource (parameters) are all valid numbers
    
    Constructor Specification:
        PRE: !isNaN(newResourceList.brick)
        PRE: !isNaN(newResourceList.ore)
        PRE: !isNaN(newResourceList.sheep)
        PRE: !isNaN(newResourceList.wheat)
        PRE: !isNaN(newResourceList.wood)
        POST: All 5 variables are positive numbers
    </pre>
    @class ResourceList
    @constructor

    @param {Object} newResourceList An object containing the 5 necessary values to 
*/
function ResourceList(newResourceList) {
    this.brick = newResourceList.brick;
    this.ore = newResourceList.ore;
    this.sheep = newResourceList.sheep;
    this.wheat = newResourceList.wheat;
    this.wood = newResourceList.wood;
}

ResourceList.prototype.getCardCount = function () {
    var count = this.brick + this.ore + this.sheep + this.wheat + this.wood;
    return count;
}

/**
    <pre>
        POST: Correctly returns whether or not this ResourceList contains enough resources to afford a Road
    </pre>
    @method canAffordRoad
    @return {boolean} Whether or not this ResourceList has the resources to buy a Road
*/
ResourceList.prototype.canAffordRoad = function() {

    if (this.brick >= 1 && this.wood >= 1)
        return true;
    return false;
}

/**
    <pre>
        POST: Correctly returns whether or not this ResourceList contains enough resources to afford a Settlement
    </pre>
    @method canAffordSettlement
    @return {boolean} Whether or not this ResourceList has the resources to buy a Settlement
*/
ResourceList.prototype.canAffordSettlement = function() {

    if (this.brick >= 1 && this.wood >= 1 && this.wheat >= 1 && this.sheep >= 1)
        return true;
    return false;
}

/**
    <pre>
        POST: Correctly returns whether or not this ResourceList contains enough resources to afford a City
    </pre>
    @method canAffordCity
    @return {boolean} Whether or not this ResourceList has the resources to buy a City
*/
ResourceList.prototype.canAffordCity = function() {

    if (this.wheat >= 2 && this.ore >= 3) 
        return true;
    return false;
}

/**
    <pre>
        POST: Correctly returns whether or not this ResourceList contains enough resources to afford a Development Card
    </pre>
    @method canAffordDevCard
    @return {boolean} Whether or not this ResourceList has the resources to buy a Development Card
*/
ResourceList.prototype.canAffordDevCard = function() {

    if (this.sheep >= 1 && this.wheat >= 1 && this.ore >= 1)
        return true;
    return false;
}

/**
    <pre>
        PRE: Contains the necessary resources to buy a Road
        POST: Brick and Wood are correctly decremented by 1 and are not negative values
    </pre>
    @method decrementRoad
*/
ResourceList.prototype.decrementRoad = function() {

    this.brick--;
    this.wood--;
}

/**
    <pre>
        PRE: Contains the necessary resources to buy a Settlement
        POST: Brick, wood, wheat, and sheep are correctly decremented by 1 and are not negative values
    </pre>
    @method decrementSettlement
*/
ResourceList.prototype.decrementSettlement = function() {

    this.brick--;
    this.wood--;
    this.wheat--;
    this.sheep--;
}

/**
    <pre>
        PRE: Contains the necessary resources to buy a City
        POST: Wheat is correctly decremented by 2, and ore is correctly decremented by 3, and neither become negative values
    </pre>
    @method decrementCity
*/
ResourceList.prototype.decrementCity = function() {

    this.wheat -= 2;
    this.ore -= 3;
}

/**
    <pre>
        PRE: Contains the necessary resources to buy a Development Card
        POST: Sheep, wheat, and ore are correctly decremented by 1 and are not negative values
    </pre>
    @method decrementDevCard
*/
ResourceList.prototype.decrementDevCard = function() {

    this.sheep--;
    this.wheat--;
    this.ore--;
}

/**
    <pre>
        PRE: resourceHand contains all valid, positive number
        POST: The ResourceList correctly decremented these values 
    </pre>
    @method discardCards
    @param {Object} A ResourceHand which contains the 5 changes in values (0 for no change) 
*/
ResourceList.prototype.discardCards = function(resourceHand) {

    this.brick -= resourceHand.brick;
    this.ore -= resourceHand.ore;
    this.sheep -= resourceHand.sheep;
    this.wheat -= resourceHand.wheat;
    this.wood -= resourceHand.wood;
}