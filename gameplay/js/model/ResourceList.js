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
        PRE: !isNaN(brick)
        PRE: !isNaN(ore)
        PRE: !isNaN(sheep)
        PRE: !isNaN(wheat)
        PRE: !isNaN(wood)
        POST: All 5 variables are positive numbers
    </pre>
    @class ResourceList
    @constructor

    @param {Number} brick Initial number of brick cards
    @param {Number} ore Initial number of ore cards
    @param {Number} sheep Initial number of sheep cards
    @param {Number} wheat Initial number of wheat cards
    @param {Number} wood Initial number of wood cards
*/
function ResourceList(brick, ore, sheep, wheat, wood) {
    this.brick = brick;
    this.ore = ore;
    this.sheep = sheep;
    this.wheat = wheat;
    this.wood = wood;
}

/**
    <pre>
        PRE: The parameter (newBrick) is a non-negative integer
        POST: The current brick value is equal to the parameter (newBrick) value
    </pre>
    @method setBrick
    @param {Number} newBrick
*/
ResourceList.prototype.setBrick = function(newBrick) {

    this.brick = newBrick;
}

/**
    <pre>
        PRE: The parameter (newOre) is a non-negative integer
        POST: The current ore value is equal to the parameter (newOre) value
    </pre>
    @method setOre
    @param {Number} newOre
*/
ResourceList.prototype.setOre = function(newOre) {

    this.ore = newOre;
}

/**
    <pre>
        PRE: The parameter (newSheep) is a non-negative integer
        POST: The current sheep value is equal to the parameter (newSheep) value
    </pre>
    @method setSheep
    @param {Number} newSheep
*/
ResourceList.prototype.setSheep = function(newSheep) {

    this.sheep = newSheep;
}

/**
    <pre>
        PRE: The parameter (newWheat) is a non-negative integer
        POST: The current wheat value is equal to the parameter (newWheat) value
    </pre>
    @method setWheat
    @param {Number} newWheat
*/
ResourceList.prototype.setWheat = function(newWheat) {

    this.wheat = newWheat;
}

/**
    <pre>
        PRE: The parameter (newWood) is a non-negative integer
        POST: The current wood value is equal to the parameter (newWood) value
    </pre>
    @method setWood
    @param {Number} newWood
*/
ResourceList.prototype.setWheat = function(newWood) {

    this.wood = newWood;
}

/**
    <pre>
        POST: The correct number was returned to the client
    </pre>
    @method getBrick
    @return Number, the number of brick cards in this ResourceList
*/
ResourceList.prototype.getBrick = function () {

    return this.brick;
}

/**
    <pre>
        POST: The correct number was returned to the client
    </pre>
    @method getOre
    @return Number, the number of ore cards in this ResourceList
*/
ResourceList.prototype.getOre = function () {

    return this.ore;
}

/**
    <pre>
        POST: The correct number was returned to the client
    </pre>
    @method getSheep
    @return Number, the number of sheep cards in this ResourceList
*/
ResourceList.prototype.getSheep = function () {

    return this.sheep;
}

/**
    <pre>
        POST: The correct number was returned to the client
    </pre>
    @method getWheat
    @return Number, the number of wheat cards in this ResourceList
*/
ResourceList.prototype.getWheat = function () {

    return this.wheat;
}

/**
    <pre>
        POST: The correct number was returned to the client
    </pre>
    @method getWood
    @return Number, the number of wood cards in this ResourceList
*/
ResourceList.prototype.getWood = function () {

    return this.wood;
}