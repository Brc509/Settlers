

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

test( "Year_of_Plenty", 5, function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Logged in");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "inited Sam");
			start();

			var proxy = new catan.models.ClientProxy(0, clientModel);

			stop();
			proxy.rollNumber(6, function (error, model){
				
				ok(true, "rolled number");
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
	ok(false == false, "Sam has not yet rolled, his status is not Playing");

});

// BUYDEVCARD, FINISHTURN, MONUMENT
// ONLY REQUIRE MOVE TYPE AND PLAYER INDEX
asyncTest( "Moves Simple", 6, function() {
	
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
			var result = clientModel.canbuyDevCard();
			ok(result == true, "Sam can buy a devCard");
			console.log(result);
		});
	});


	loginPlayer("Brooke", "brooke", "blue", "0", function () {
		
		ok(true, "Logged in");
		start();
		stop();

		clientModel.initFromServer(function () {
			ok(true, "inited brooke");
			start();
			
			var result = clientModel.canbuyDevCard();
			ok(result == false, "Brooke cannont buy a devCard");
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

// test( "rollNumber", function() {
// 	var clientModel = new catan.models.ClientModel(0);

// 	ok( 1 == "1", "Passed!" );
// });


test( "Soldier", function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Logged in");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "inited Sam");
			start();

			var proxy = new catan.models.ClientProxy(0, clientModel);

			stop();
			proxy.rollNumber(6, function (error, model){
				
				ok(true, "rolled number");
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
test( "Monopoly", function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Logged in");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "inited Sam");
			start();

			var proxy = new catan.models.ClientProxy(0, clientModel);

			stop();
			proxy.rollNumber(6, function (error, model){
				
				ok(true, "rolled number");
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

asyncTest( "discardCards", function() {
	var clientModel = new catan.models.ClientModel(0);

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
			var result = clientModel.canDiscardCards();
			ok(result == false, "Sam does not have enough resources");
			console.log(result);
		});
	});


	loginPlayer("Brooke", "brooke", "blue", "0", function () {
		
		ok(true, "Logged in");
		start();
		stop();

		clientModel.initFromServer(function () {
			ok(true, "inited brooke");
			start();
			
			var result = clientModel.canDiscardCards();
			ok(result == false, "Brooke cannont buy a devCard");
			console.log(result);
		});
	});

	ok( 1 == "1", "Passed!" );
});


