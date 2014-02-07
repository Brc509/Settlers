

function loginPlayer(player, password, color, id){
	this.player;
	var login = $.post("/user/login", { username: player, password : password}, function(data) {
			var loginData = data;					
					
				var join = $.post("/games/join", { color: color, id : id}, function(data) {
					var joinData = data;

					var model = $.get("/game/model", function(data){
						var modelData = data;
						this.player = modelData.players[0];
						console.log(this.player);
						start();
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
test( "Moves Simple", function() {
	var clientModel = new catan.models.ClientModel(0);
	stop();
	loginPlayer("Sam", "sam", "red", "0");


	// while(typeof this.player === 'undefined'){

	// }
	stop();
	clientModel.initFromServer(function () {
		start();
	});
	console.log(this.player);
	var result = clientModel.canbuyDevCard();

	// loginPlayer("Brooke")

	ok( 1 == "1", "Passed!" );
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

// test( "Year_of_Plenty", function() {
// 	var clientModel = new catan.models.ClientModel(0);

// 	ok( 1 == "1", "Passed!" );
// });

// test( "Soldier", function() {
// 	var clientModel = new catan.models.ClientModel(0);

// 	ok( 1 == "1", "Passed!" );
// });
// test( "Monopoly", function() {
// 	var clientModel = new catan.models.ClientModel(0);

// 	ok( 1 == "1", "Passed!" );
// });

// test( "discardCards", function() {
// 	var clientModel = new catan.models.ClientModel(0);

// 	ok( 1 == "1", "Passed!" );
// });


