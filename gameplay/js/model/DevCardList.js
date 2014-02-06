﻿/**
    The DevCardList class contains a list of each type of development card
    <pre>
    Domain:
        monopoly: Number of monopoly cards, Number
        monument: Number of monument cards, Number
        roadBuilding: Number of roadBuilding cards, Number
        soldier: Number of soldier cards, Number
        yearOfPlenty: Number of yearOfPlenty cards, Number

    Invariants:
        INVARIANT: The numbers of each development card (parameters) are all valid numbers
    
    Constructor Specification:
        PRE: !isNaN(newDevCardList.monopoly)
        PRE: !isNaN(newDevCardList.monument)
        PRE: !isNaN(newDevCardList.roadBuilding)
        PRE: !isNaN(newDevCardList.soldier)
        PRE: !isNaN(newDevCardList.yearOfPlenty)
        POST: All 5 variables are positive numbers
    </pre>
    @class DevCardList
    @constructor

    @param {newDevCardList} An object containing the necessary values for a Dev Card List 
*/
function DevCardList(newDevCardList) {

    this.monopoly = newDevCardList.monopoly;
    this.monument = newDevCardList.monument;
    this.roadBuilding = newDevCardList.roadBuilding;
    this.soldier = newDevCardList.soldier;
    this.yearOfPlenty = newDevCardList.yearOfPlenty;
}

/**
    <pre>
        PRE: All of the parameter changes are valid numbers
        PRE: All none of the negative changes are greater than the current value
        POST: The five numbers were changed by parameter amounts
    </pre>
    @method changeDevCards
    @param DevCardList, a DevCardList of number-changes to modify this list by
*/
DevCardList.prototype.changeDevCards = function () {

}

/**
    <pre>
        POST: The correct number was returned to the client
    </pre>
    @method getMonopoly
    @return Number, the number of monopoly cards in this DevCardList
*/
DevCardList.prototype.getMonopoly = function () {

}

/**
    <pre>
        POST: The correct number was returned to the client
    </pre>
    @method getMonument
    @return Number, the number of monument cards in this DevCardList
*/
DevCardList.prototype.getMonument = function () {

}

/**
    <pre>
        POST: The correct number was returned to the client
    </pre>
    @method getRoadBuilding
    @return Number, the number of roadBuilding cards in this DevCardList
*/
DevCardList.prototype.getRoadBuilding = function () {

}

/**
    <pre>
        POST: The correct number was returned to the client
    </pre>
    @method getSoldier
    @return Number, the number of soldier cards in this DevCardList
*/
DevCardList.prototype.getSoldier = function () {

}

/**
    <pre>
        POST: The correct number was returned to the client
    </pre>
    @method getYearOfPlenty
    @return Number, the number of yearOfPlenty cards in this DevCardList
*/
DevCardList.prototype.getYearOfPlenty = function () {

}