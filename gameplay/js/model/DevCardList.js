var catan = catan || {};
catan.models = catan.models || {};

/**
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

    @param {Object} newDevCardList An object containing the five necessary values for a DevCardList 
*/
var DevCardList = (function DevCardListClass() {

    function DevCardList() {

    }

    /**
        <pre>
            PRE: newDevCardList contains the necessary values
            POST: All values are correctly updated
        </pre>
        @method update
        @param {Object} newDevCardList A list of values to update this object
    */ 
    DevCardList.prototype.update = function(newDevCardList) {

        this.monopoly = newDevCardList.monopoly;
        this.monument = newDevCardList.monument;
        this.roadBuilding = newDevCardList.roadBuilding;
        this.soldier = newDevCardList.soldier;
        this.yearOfPlenty = newDevCardList.yearOfPlenty;
    }
}());