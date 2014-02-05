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
function TradeOffer(newTradeOffer) {
    this.sender = newTradeOffer.sender;
    this.receiver = newTradeOffer.receiver;
	this.offer = newTradeOffer.offer;
}

/**
<pre>
A sender needs to be defined
</pre>
@method getSender
@return {int} The sender ID.
*/
TradeOffer.prototype.getSender = function() {
    return this.sender;
}

/**
<pre>
A receiver needs to be defined
</pre>
@method getReceiver
@return {int} The receiver ID.
*/
TradeOffer.prototype.getReceiver = function() {
    return this.receiver;
}

/**
<pre>
An offer needs to be defined
</pre>
@method getOffer
@return {ResourceList} A list of the items in the offer.
*/
TradeOffer.prototype.getOffer = function() {
    return this.offer;
}