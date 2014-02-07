

test( "hello test", function() {
	var clientModel = new catan.models.ClientModel(0);

	ok( 1 == "1", "Passed!" );
});

// BUYDEVCARD, FINISHTURN, MONUMENT
// ONLY REQUIRE MOVE TYPE AND PLAYER INDEX
test( "Moves Simple", function() {
	var clientModel = new catan.models.ClientModel(0);

	loginSam();
	clientModel.initFromServer();
	var result = clientModel.buyDevCard();

	ok( 1 == "1", "Passed!" );
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
	var clientModel = new catan.models.ClientModel(0);

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

function loginSam(){
	
	var login = $.post("/user/login", { username: "Sam", password : "sam"}, function(data) {
				var loginData = data;
					
					
					var join = $.post("/games/join", { color: "red", id : "0"}, function(data) {
						var joinData = data;

					});
				});
}

