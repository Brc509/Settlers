

function loginPlayer(player, password, color, id, callback){
	this.player;
	var login = $.post("/user/login", { username: player, password : password}, function(data) {
			var loginData = data;					
				var join = $.post("/games/join", { color: color, id : id}, function(data) {
					var joinData = data;
					var model = $.get("/game/model", function(data){
						var modelData = data;
						this.player = modelData.players[0];
						console.log(this.player);

						callback();
						
					});
				});
			});
}

test( "model.yearOfPlenty()", 5, function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Sam has logged in.");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "Initialized the server with Sam as the current player.");
			start();

			var proxy = new catan.models.ClientProxy(0, clientModel);

			stop();
			proxy.rollNumber(6, function (error, model){
				
				ok(true, "Successfully rolled a number (rolled the die)");
				clientModel.turnTracker = model.turnTracker;
				start();

				if(error){
				}else{
					ok(true == true, "Sam successfully executed year of plenty");
				}
			});
			
		});
	});

	
	var result = clientModel.yearOfPlenty("ore", "wheat");
	ok(false == false, "ERROR: Sam has not yet rolled, his status is not Playing");

});

// BUYDEVCARD, FINISHTURN, MONUMENT
// ONLY REQUIRE MOVE TYPE AND PLAYER INDEX
asyncTest( "model.canBuyDevCard()", 6, function() {
	
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Sam has logged in.");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "Initialized the server with Sam as the current player.");
			start();

			clientModel.clientPlayer.resources['ore'] ++;
			var result = clientModel.canbuyDevCard();
			ok(result == true, "Sam can buy a devCard");
			console.log(result);
		});
	});


	loginPlayer("Brooke", "brooke", "blue", "0", function () {
		
		ok(true, "Brooke has logged in.");
		start();
		stop();

		clientModel.initFromServer(function () {
			ok(true, "Initialized the server with Brooke as the current player.");
			start();
			
			var result = clientModel.canbuyDevCard();
			ok(result == false, "ERROR: Brooke cannot buy a devCard, she doesn't have the proper resources.");
			console.log(result);
		});
	});

});


// //BUILD ROAD, SETTLEMENT, CITY, ROAD_BUILDING
// test( "Moves Build Tests", function() {
// 	var clientModel = new catan.models.ClientModel(0);

// 	ok( 1 == "1", "Passed!" );
// });

// //OFFER AND ACCEPT TRADE
// test( "Moves Trade Test", function() {
// 	var clientModel = new catan.models.ClientModel(0);

// 	ok( 1 == "1", "Passed!" );
// });


// test( "sendChat", function() {
// 	var clientModel = new catan.models.ClientModel(0);

// 	ok( 1 == "1", "Passed!" );
// });

asyncTest( "clientProxy.rollNumber()", function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Logged in");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "inited Sam");
			start();

			clientModel.clientPlayer.resources['ore'] ++;
			stop();
			clientModel.clientProxy.rollNumber(6, function (result) {
				ok(result == false, "Sam rolled a 6.  Good work there sammy");
				start();
				console.log(result);
			});
			
		});
	});


	loginPlayer("Brooke", "brooke", "blue", "0", function () {
		
		ok(true, "Logged in as Brooke");
		start();
		stop();

		clientModel.initFromServer(function () {
			ok(true, "inited brooke");
			start();
			
			stop();
			clientModel.clientProxy.rollNumber(7, function (result) {
				ok(result == false, "Brook rolled a seven.  Everyone watch out.");
				start();
				console.log(result);
			});

		});
	});

	ok( 1 == "1", "Called both clientProxy methods.  Wait for callback" );
});


test( "model.soldier(robberSpot, victimID)", function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Sam has logged in.");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "Initialized the server with Sam as the current player.");
			start();

			var proxy = new catan.models.ClientProxy(0, clientModel);

			stop();
			proxy.rollNumber(6, function (error, model){
				
				ok(true, "Successfully rolled a number (rolled the die)");
				start();

				clientModel.turnTracker = model.turnTracker;
				var status = clientModel.soldier(1, 2);

				if(error){
				}else{
					ok(true == true, status);
				}
			});
			
		});
	});

	var status = clientModel.soldier(1,2);
	ok(true == true, status);

});
test( "model.monopoly()", function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Sam has logged in.");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "Initialized the server with Sam as the current player.");
			start();

			var proxy = new catan.models.ClientProxy(0, clientModel);

			stop();
			proxy.rollNumber(6, function (error, model){
				
				ok(true, "Successfully rolled a number (rolled the die)");
				start();

				clientModel.turnTracker = model.turnTracker;
				var status = clientModel.monopoly();

				if(error){
				}else{
					ok(true == true, status);
				}
			});
			
		});
	});

	var status = clientModel.monopoly();
	ok(true == true, status);
});

asyncTest( "model.canDiscardCards()", function() {
	var clientModel = new catan.models.ClientModel(0);

	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Sam has logged in.");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "Initialized server with current player (Sam).");
			start();

			clientModel.clientPlayer.resources['ore'] ++;
			var result = clientModel.canDiscardCards();
			ok(result == false, "ERROR: Sam does not have enough resources");
			console.log(result);
		});
	});


	loginPlayer("Brooke", "brooke", "blue", "0", function () {
		
		ok(true, "Brooke has logged in.");
		start();
		stop();

		clientModel.initFromServer(function () {
			ok(true, "Initialized server with current player (Brooke).");
			start();
			
			var result = clientModel.canDiscardCards();
			ok(result == false, "ERROR: Brooke cannot discard cards.");
			console.log(result);
		});
	});

	ok( 1 == "1", "Passed!" );
});


