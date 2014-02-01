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
        PRE: !isNaN(_brick)
        PRE: !isNaN(_ore)
        PRE: !isNaN(_sheep)
        PRE: !isNaN(_wheat)
        PRE: !isNaN(_wood)
        POST: All 5 variables are positive numbers
    </pre>
    @class ResourceList
    @constructor

    @param {_brick} Initial number of brick cards
    @param {_ore} Initial number of ore cards
    @param {_sheep} Initial number of sheep cards
    @param {_wheat} Initial number of wheat cards
    @param {_wood} Initial number of wood cards
*/
function ResourceList(_brick, _ore, _sheep, _wheat, _wood) {

}

/**
    <pre>
        PRE: All of the parameter changes are valid numbers
        PRE: All none of the negative changes are greater than the current value
        POST: The five numbers were changed by parameter amounts
    </pre>
    @method changeResources
    @param ResourceList, a ResourceList of number-changes to modify this list by
*/
ResourceList.prototype.changeResources = function() {

}

/**
    <pre>
        POST: The correct number was returned to the client
    </pre>
    @method getBrick
    @return Number, the number of brick cards in this ResourceList
*/
ResourceList.prototype.getBrick = function () {

}

/**
    <pre>
        POST: The correct number was returned to the client
    </pre>
    @method getOre
    @return Number, the number of ore cards in this ResourceList
*/
ResourceList.prototype.getOre = function () {

}

/**
    <pre>
        POST: The correct number was returned to the client
    </pre>
    @method getSheep
    @return Number, the number of sheep cards in this ResourceList
*/
ResourceList.prototype.getSheep = function () {

}

/**
    <pre>
        POST: The correct number was returned to the client
    </pre>
    @method getWheat
    @return Number, the number of wheat cards in this ResourceList
*/
ResourceList.prototype.getWheat = function () {

}

/**
    <pre>
        POST: The correct number was returned to the client
    </pre>
    @method getWood
    @return Number, the number of wood cards in this ResourceList
*/
ResourceList.prototype.getWood = function () {

}