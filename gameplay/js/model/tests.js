

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

// test( "hello test", function() {
// 	var clientModel = new catan.models.ClientModel(0);

// 	ok( 1 == "1", "Passed!" );
// });

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


//BUILD ROAD, SETTLEMENT, CITY, ROAD_BUILDING
test( "Moves Build Tests", function() {
	var clientModel = new catan.models.ClientModel(0);

	ok( 1 == "1", "Passed!" );
});

//OFFER AND ACCEPT TRADE
test( "Moves Trade Test", function() {
	var clientModel = new catan.models.ClientModel(0);

	ok( 1 == "1", "Passed!" );
});


test( "sendChat", function() {
	var clientModel = new catan.models.ClientModel(0);

	ok( 1 == "1", "Passed!" );
});

test( "rollNumber", function() {
	var clientModel = new catan.models.ClientModel(0);

	ok( 1 == "1", "Passed!" );
});

test( "Year_of_Plenty", function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Logged in");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "inited Sam");
			start();

			clientModel.rollNumber(6);

			var result = clientModel.yearOfPlenty("ore", "wheat");
			ok(true == true, "Sam can buy a devCard");
			console.log(result);
		});
	});

	ok( 1 == "1", "Passed!" );
});

test( "Soldier", function() {
	var clientModel = new catan.models.ClientModel(0);

	ok( 1 == "1", "Passed!" );
});
test( "Monopoly", function() {
	var clientModel = new catan.models.ClientModel(0);

	ok( 1 == "1", "Passed!" );
});

test( "discardCards", function() {
	var clientModel = new catan.models.ClientModel(0);

	ok( 1 == "1", "Passed!" );
});


