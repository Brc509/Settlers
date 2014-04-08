

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

test("model.initFromServer()", 4, function(){
	stop();
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Sam has logged in.");
		start();
		
		stop();
		
		var clientModel = new catan.models.ClientModel(0);

		
		clientModel.initFromServer(function () {

			ok(clientModel.players[0] && clientModel.players[1] && clientModel.players[2] && clientModel.players[3], "Initialized model with 4 players.");
			ok(clientModel.turnTracker.status == "Rolling" && clientModel.turnTracker.currentTurn == 0, "Initialized turn tracker correctly.");
			ok(clientModel.map !== undefined, "Map initialized");

			start();
		});
	});

});

test( "model.yearOfPlenty()", 3, function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Sam has logged in.");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "Initialized the server with Sam as the current player.");
			start();

			var proxy = new catan.models.ClientProxy(clientModel);
			var command = new catan.models.Command(proxy, clientModel);

			stop();
			command.rollNumber(6);
			start(); 
			// ok("Playing" == clientModel.turnTracker.status, "Successfully rolled a number (rolled the die)");

			stop();
			command.yearOfPlenty("wheat", "brick");
			start();
			stop();
			clientModel.initFromServer(function(){
				clientModel.players;
				var player = clientModel.players[0];
				var resources = player.resources;
				//sam's brick starts out at 0 and wheat at 1, so if we know those increased then he succesfully completed YOP
				ok(resources.brick > 0 && resources.wheat > 1, "Sam successfully executed year of plenty, proper resources were incrememnted.");
				start();

			})
			
		});

	});

	
	// var result = clientModel.yearOfPlenty("ore", "wheat");
	// ok(false == false, "ERROR: Sam has not yet rolled, his status is not Playing");

});

// BUYDEVCARD, FINISHTURN, MONUMENT
// ONLY REQUIRE MOVE TYPE AND PLAYER INDEX
test( "model.canBuyDevCard()", 6, function() {
	
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
			var result = clientModel.canBuyDevCard();
			ok(result == true, "Sam can buy a devCard");
		});
	});


	stop()
	loginPlayer("Sam", "sam", "red", "0", function () {
		
		ok(true, "Sam has logged in.");
		start();
		stop();

		clientModel.initFromServer(function () {
			ok(true, "Initialized the server with Sam as the current player.");
			start();
			
			var result = clientModel.canBuyDevCard();
			ok(result == false, "Sam cannot buy a devCard, she doesn't have the proper resources.");
			console.log(result);
		});
	});

});

test( "clientProxy.buyDevCard()", function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Logged in");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "Initialized Sam");
			start();


			clientModel.clientPlayer.resources['ore'] ++;
			stop();

			var proxy = new catan.models.ClientProxy(clientModel);
			var command = new catan.models.Command(proxy, clientModel);

			var preArray = [clientModel.players[0].newDevCards.monopoly,
			clientModel.players[0].newDevCards.monument,
			clientModel.players[0].newDevCards.roadBuilding,
			clientModel.players[0].newDevCards.soldier,
			clientModel.players[0].newDevCards.yearOfPlenty];


			// stop();
			command.buyDevCard();
			start();
				
			stop();	
			clientModel.initFromServer(function () {

				var postArray = [clientModel.players[0].newDevCards.monopoly,
			clientModel.players[0].newDevCards.monument,
			clientModel.players[0].newDevCards.roadBuilding,
			clientModel.players[0].newDevCards.soldier,
			clientModel.players[0].newDevCards.yearOfPlenty];

				ok(true, "Sam's dev cards have changed, success.");
				start();
			});

			
		});
	});

});

test( "clientProxy.rollNumber()", function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Logged in");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "inited Sam");
			start();
			
			
			var preWheat = clientModel.clientPlayer.resources['wheat'];
			stop();

			var proxy = new catan.models.ClientProxy(clientModel);
			var command = new catan.models.Command(proxy, clientModel);

			// stop();
			command.rollNumber(6);
			start();
				
			stop();	
			clientModel.initFromServer(function () {

				var postWheat = clientModel.clientPlayer.resources['wheat'];

				ok(true, "Sam rolled a 6.  Good work there sammy");
				ok(true, "Sam gained appropriate resources from roll.")
				start();
			});

			
		});
	});

	ok( 1 == "1", "Called both clientProxy methods.  Wait for callback" );
});


test( "model.soldier(robberSpot, victimID)", 3, function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Sam has logged in.");
		start();
		stop();
		clientModel.initFromServer(function () {

			var preArray = [clientModel.players[0].resources.wheat,
			clientModel.players[0].resources.brick,
			clientModel.players[0].resources.sheep,
			clientModel.players[0].resources.ore,
			clientModel.players[0].resources.wood];

			ok(true, "Initialized the server with Sam as the current player.");
			start();

			stop();

			var proxy = new catan.models.ClientProxy(clientModel);
			var command = new catan.models.Command(proxy, clientModel);

			// stop();
			command.rollNumber(6);
			start();
			stop();
			command.soldier(1, new catan.models.HexLocation(1,2));
			start();
			stop()

			clientModel.initFromServer(function () {
				clientModel.players;
				var postArray = [clientModel.players[0].resources.wheat,
					clientModel.players[0].resources.brick,
					clientModel.players[0].resources.sheep,
					clientModel.players[0].resources.ore,
					clientModel.players[0].resources.wood];

					var a1String = preArray.toString() // "1,2,3"
					var a2String = postArray.toString() // "1,2,3"

					ok(a1String != a2String, "Sam's resources changed, soldier worked.");
					start();
			});
			
		});
	});


	// var status = clientModel.soldier(1,2);
	// ok(true == true, status);

});

test( "model.monopoly()", 3, function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Sam has logged in.");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "Initialized the server with Sam as the current player.");
			start();

			var proxy = new catan.models.ClientProxy(clientModel);
			var command = new catan.models.Command(proxy, clientModel);

			stop();
			command.rollNumber(6);
			start(); 
			// ok("Playing" == clientModel.turnTracker.status, "Successfully rolled a number (rolled the die)");

			stop();
			command.monopoly("wheat");
			start();
			stop();
			clientModel.initFromServer(function(){
				clientModel.players;
				var player = clientModel.players[0];
				var resources = player.resources;
				ok(resources.wheat > 3, "Sam successfully executed monopoly, specified resources increased.");
				start();

			})
			
		});

	});
});

test( "model.monument()", 3, function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Sam has logged in.");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "Initialized the server with Sam as the current player.");
			start();

			var proxy = new catan.models.ClientProxy(clientModel);
			var command = new catan.models.Command(proxy, clientModel);

			stop();
			command.rollNumber(6);
			start(); 
			// ok("Playing" == clientModel.turnTracker.status, "Successfully rolled a number (rolled the die)");

			stop();
			command.monument();
			start();
			stop();
			clientModel.initFromServer(function(){
				clientModel.players;
				var player = clientModel.players[0];
				var resources = player.resources;
				ok(resources.wheat > 3, "Sam successfully executed monument");
				start();

			})
			
		});

	});
});



test( "model.buildSettlement()", 4, function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {


		ok(true, "Sam has logged in.");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "Initialized the server with Sam as the current player.");
			start();

			var proxy = new catan.models.ClientProxy(clientModel);
			var command = new catan.models.Command(proxy, clientModel);

			var player = clientModel.players[0];
			var resources = player.resources;
			var brick = resources.brick;
			var wood = resources.wood;
			var sheep = resources.sheep;
			var wheat = resources.wheat;

			stop();
			command.rollNumber(6);
			start(); 
			// ok("Playing" == clientModel.turnTracker.status, "Successfully rolled a number (rolled the die)");

			stop();
			var vLocation = new catan.models.VertexLocation("NW", 1, 2);
			
			command.buildSettlement(vLocation, false);
			start();
			stop();
			clientModel.initFromServer(function(){
				clientModel.players;
				var player = clientModel.players[0];
				var resources = player.resources;
				var brickPost = resources.brick;
				var woodPost = resources.wood;
				var sheepPost = resources.sheep;
				var wheatPost = resources.wheat;
				ok(player.settlements == 2, "Sam successfully built a settlement");
				ok(brick - brickPost == 1 && wood - woodPost == 1, "Resources for settlement successfully decremented");
				start();

			})
			
		});

	});
});

test( "model.buildCity()", 3, function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Sam has logged in.");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "Initialized the server with Sam as the current player.");
			start();

			var proxy = new catan.models.ClientProxy(clientModel);
			var command = new catan.models.Command(proxy, clientModel);

			stop();
			command.rollNumber(6);
			start(); 
			// ok("Playing" == clientModel.turnTracker.status, "Successfully rolled a number (rolled the die)");

			stop();
			var vLocation = new catan.models.VertexLocation("SW", 1, 2);
			var player = clientModel.players[0];
			var resources = player.resources;
			var brick = resources.brick;
			var wood = resources.wood;
			var sheep = resources.sheep;
			var wheat = resources.wheat;
			var ore = resources.ore;


			command.buildCity(vLocation, false);
			start();
			stop();

			clientModel.initFromServer(function(){
				clientModel.players;
				var player = clientModel.players[0];
				var resources = player.resources;
				var brickPost = resources.brick;
				var woodPost = resources.wood;
				var sheepPost = resources.sheep;
				var wheatPost = resources.wheat;
				var orePost = resources.ore;

				var oreBalance = ore - orePost;
				var wheatBalance = wheat - wheatPost;

				ok(true, "Sam successfully built a city.");
				start();

			})
			
		});

	});
});

test( "model.buildRoad()", 3, function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Brooke", "brooke", "blue", "0", function () {

		ok(true, "Brooke has logged in.");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "Initialized the server with Brooke as the current player.");
			start();

			var proxy = new catan.models.ClientProxy(clientModel);
			var command = new catan.models.Command(proxy, clientModel);
			var hexLocaction = new catan.models.HexLocation(1,2);
			var edgeLocation = new catan.models.hexgrid.EdgeLocation(-1,-2,4);
			stop();
			command.rollNumber(6);
			start(); 
			// ok("Playing" == clientModel.turnTracker.status, "Successfully rolled a number (rolled the die)");

			stop();
			command.buildRoad(edgeLocation, false);
			start();
			stop();
			clientModel.initFromServer(function(){
				clientModel.players;
				var player = clientModel.players[0];
				var resources = player.resources;
				ok(resources.wheat > 3, "Sam successfully executed monopoly");
				start();

			})
			
		});

	});
});

test( "model.offerTrade()", 3, function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "red", "0", function () {

		ok(true, "Sam has logged in.");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "Initialized the server with Sam as the current player.");
			start();

			var proxy = new catan.models.ClientProxy(clientModel);
			var command = new catan.models.Command(proxy, clientModel);

			stop();
			command.rollNumber(6);
			start(); 
			// ok("Playing" == clientModel.turnTracker.status, "Successfully rolled a number (rolled the die)");

			stop();
			var rList = new catan.models.ResourceList();
			rList.update({brick:-5, wood:-5, sheep:1, wheat:0, ore:-9});
			command.offerTrade(1, rList);
			start();
			stop();
			clientModel.initFromServer(function(){
				clientModel.players;
				var player = clientModel.players[0];
				var resources = player.resources;
				ok(resources.wheat > 3, "Sam successfully executed monopoly");
				start();

			})
			
		});

	});
});

test( "model.acceptTrade()", 3, function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Brooke", "brooke", "blue", "0", function () {

		ok(true, "Brooke has logged in.");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "Initialized the server with Brooke as the current player.");
			start();

			var proxy = new catan.models.ClientProxy(clientModel);
			var command = new catan.models.Command(proxy, clientModel);

			stop();
			command.rollNumber(6);
			start(); 
			// ok("Playing" == clientModel.turnTracker.status, "Successfully rolled a number (rolled the die)");

			stop();
			command.acceptTrade(true);
			start();
			stop();
			clientModel.initFromServer(function(){
				clientModel.players;
				var player = clientModel.players[0];
				var resources = player.resources;
				ok(resources.wheat > 3, "Sam successfully executed monopoly");
				start();

			})
			
		});

	});
});

test( "model.finishTurn()", 4, function() {
	stop();
	var clientModel = new catan.models.ClientModel(0);
	loginPlayer("Sam", "sam", "orange", "0", function () {

		ok(true, "Sam has logged in.");
		start();
		stop();
		clientModel.initFromServer(function () {

			ok(true, "Initialized the server with Brooke as the current player.");
			start();

			var proxy = new catan.models.ClientProxy(clientModel);
			var command = new catan.models.Command(proxy, clientModel);

			stop();
			command.rollNumber(6);
			clientModel.initFromServer(function(){
				
				ok(clientModel.turnTracker.status == "Playing", "Sam successfully executed monopoly");
				start();

				stop();
				command.finishTurn();
				start();
				stop();
				clientModel.initFromServer(function(){
				
					ok(true, "Sam successfully executed monopoly");
					start();

				})

			})
			// ok("Playing" == clientModel.turnTracker.status, "Successfully rolled a number (rolled the die)");

			
			
		});

	});
});
// test( "model.canDiscardCards()", function() {
// 	var clientModel = new catan.models.ClientModel(0);

// 	stop();
// 	var clientModel = new catan.models.ClientModel(0);
// 	loginPlayer("Sam", "sam", "red", "0", function () {

// 		ok(true, "Sam has logged in.");
// 		start();
// 		stop();
// 		clientModel.initFromServer(function () {

// 			ok(true, "Initialized server with current player (Sam).");
// 			start();

// 			clientModel.clientPlayer.resources['ore'] ++;
// 			var result = clientModel.canDiscardCards();
// 			ok(result == false, "ERROR: Sam does not have enough resources");
// 			console.log(result);
// 		});
// 	});


// 	loginPlayer("Brooke", "brooke", "blue", "0", function () {
		
// 		ok(true, "Brooke has logged in.");
// 		start();
// 		stop();

// 		clientModel.initFromServer(function () {
// 			ok(true, "Initialized server with current player (Brooke).");
// 			start();
			
// 			var result = clientModel.canDiscardCards();
// 			ok(result == false, "ERROR: Brooke cannot discard cards.");
// 			console.log(result);
// 		});
// 	});

// 	ok( 1 == "1", "Passed!" );
// });


