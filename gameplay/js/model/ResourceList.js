﻿var catan = catan || {};
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

var catan = catan || {};
catan.models = catan.models || {};


catan.models.ResourceList  = (function clientModelNameSpace(){

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

            return (this.brick >= 1 && this.wood >= 1);
        }

        /**
            <pre>
                POST: Correctly returns whether or not this ResourceList contains enough resources to afford a Settlement
            </pre>
            @method canAffordSettlement
            @return {boolean} Whether or not this ResourceList has the resources to buy a Settlement
        */
        ResourceList.prototype.canAffordSettlement = function() {

            return (this.brick >= 1 && this.wood >= 1 && this.wheat >= 1 && this.sheep >= 1);
        }

        /**
            <pre>
                POST: Correctly returns whether or not this ResourceList contains enough resources to afford a City
            </pre>
            @method canAffordCity
            @return {boolean} Whether or not this ResourceList has the resources to buy a City
        */
        ResourceList.prototype.canAffordCity = function() {

            return (this.wheat >= 2 && this.ore >= 3);
        }

        /**
            <pre>
                POST: Correctly returns whether or not this ResourceList contains enough resources to afford a Development Card
            </pre>
            @method canAffordDevCard
            @return {boolean} Whether or not this ResourceList has the resources to buy a Development Card
        */
        ResourceList.prototype.canAffordDevCard = function() {

            return (this.sheep >= 1 && this.wheat >= 1 && this.ore >= 1);
        }

        /*
            Create a list of the resources > 0 in this list
            Used by the bank list for the maritime controller
        */
        ResourceList.prototype.getOwnedResources = function() {

            var ownedResources = [];
            var types = catan.definitions.ResourceTypes;
            for (t in types) {

                if (this[types[t]] > 0)
                    ownedResources.push(types[t]);
            }
            return ownedResources;
        }

        /*
            Get resources in this list that are greater than the ratioList
        */
        ResourceList.prototype.getResourcesGreaterThan = function(ratioList) {

            var giveResources = [];
            var types = catan.definitions.ResourceTypes;
            
            for (t in types) {

                // add the value to the list IF the player can afford the ratio
                if (this[types[t]] >= ratioList[types[t]])
                    giveResources.push(types[t]);
            }

            return giveResources;
        }

        /*
            Gets the number of resources corresponding to the string resource name
        */
        ResourceList.prototype.getResource = function(resource) {

            if (resource == "brick")
                return this.brick;
            if (resource == "ore")
                return this.ore;
            if (resource == "sheep")
                return this.sheep;
            if (resource == "wheat")
                return this.wheat;
            if (resource == "wood")
                return this.wood;

            console.log("ERROR: Could not find resource in ResourceList.getResource");
            return -1;
        }

        /*
            Sets "resource" resource's value to "value"
        */
        ResourceList.prototype.setResource = function(resource, value) {

            if (resource == "brick")
                this.brick = value;
            if (resource == "ore")
                this.ore = value;
            if (resource == "sheep")
                this.sheep = value;
            if (resource == "wheat")
                this.wheat = value;
            if (resource == "wood")
                this.wood = value;
        }

        /*
            Sets all resources to 3 IF they're greater than 3
            (Used for the ratio resource list in Maritime trading)
        */
        ResourceList.prototype.setRatioThree = function() {

            if (this.brick > 3)
                this.brick = 3;
            if (this.ore > 3)
                this.ore = 3;
            if (this.sheep > 3)
                this.sheep = 3;
            if (this.wheat > 3)
                this.wheat = 3;
            if (this.wood > 3)
                this.wood = 3;
        }

        return ResourceList;
    }());

        return ResourceList;
}());  