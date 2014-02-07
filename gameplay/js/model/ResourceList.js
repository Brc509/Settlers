var catan = catan || {};
catan.models = catan.models || {};

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

var ResourceList = (function ResourceListClass() {

    function ResourceList() {

    }

    /**
        <pre>
            PRE: newResourceList contains five values that are all positive integers
            POST: Successfully updates the five local variables
        </pre>
        @method update
        @param {Object} newResourceList A new list to update current values to
    */
    ResourceList.prototype.update = function(newResourceList) {

        this.brick = newResourceList.brick;
        this.ore = newResourceList.ore;
        this.sheep = newResourceList.sheep;
        this.wheat = newResourceList.wheat;
        this.wood = newResourceList.wood;
    }

    /**
        <pre>
            POST: Returns correct count
        </pre>
        @method getCardCount
        @return {int} Number of cards that the player has
    */
    ResourceList.prototype.getCardCount = function () {

        return this.brick + this.ore + this.sheep + this.wheat + this.wood;
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
}());