var catan = catan || {};
catan.models = catan.models || {};

/**
The Trade Offer Class 
<pre>
Domain:
    sender: unique id number of the sender
    receiver: unique id number of the receiver
	offer: a ResourceList containing items in offer
        
    Invariants:
        INVARIANT: ownerID exists
		INVARIANT: receiver exists
     
    Constructor Specification:
        PRE: sender is a player's ID
        PRE: receiver is a player's ID
		PRE: offer ResourceList is not empty
        POST: getSender() == sender
        POST: getReceiver() == receiver
		POST: getOffer() == offer
</pre>
@class TradeOffer 
@constructor
@param {:sender} (int)The ID of the sender.
@param {:receiver} (int)The ID of the receiver.
@param {:offer} (ResourceList)The list containing items in offer.
*/
var TradeOffer = (function TradeOfferClass() {
    function TradeOffer() {

    }

    TradeOffer.prototype.update = function(newTradeOffer) {

        this.sender = newTradeOffer.sender;
        this.receiver = newTradeOffer.receiver;
        this.offer = newTradeOffer.offer;
    }
}());