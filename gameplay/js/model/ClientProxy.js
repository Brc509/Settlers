// ClientProxy.js
// Author: Spencer Bench

var catan = catan || {};
catan.models = catan.models || {};

catan.models.ClientProxy = (function() {

	var myself;
	
	/**
		The ClientProxy class is an intermediary between the server and the client.
		<pre>
		Domain:
			playerIndex: The ID of the player who controls this client, number
			revision: The current version of the model
			
		Constructor Specification:
			PRE: playerIndex is an integer
		</pre>
		
		@class ClientProxy
		@constructor
	*/
	function ClientProxy(clientModel) {
		this.url;
		this.data = null;
		this.callback = clientModel.updateModel;
		myself = this;
	};
	ClientProxy.prototype.constructor = ClientProxy;


	ClientProxy.prototype.get = function (callback) {
		jQuery.ajax({ url: this.url })
			.done(function(data) 	{ callback(false, data); })
			.fail(function(jqxhr) 	{ callback(true, jqxhr); });
	}

	ClientProxy.prototype.post = function (callback) {
		jQuery.post(this.url, JSON.stringify(this.data),function (data) { 
			myself.callback(false, data); 
		})
			.fail (function (data) { myself.callback(true, data); });
	}

	return ClientProxy;
	
})();
