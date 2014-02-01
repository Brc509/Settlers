var games;
var allRequests;

function startDocument() {

	// When the button is clicked to execute a request
	$('#postButton').on('click', function(){
		// When you say $('.formInputs'), it'll reference all the objects with that class

		var request = getCurrentRequest();
		if (request.type == 'JSON' && request.method == 'POST') {

			$.post(request.url, $('#reqJSON').val())
			.done(function(response) {
				$('#textResponse').val(JSON.stringify(response));
			})
			.fail(function(){
				console.log("failed JSON POST request!");
			})
		}
		else {
			// If it's not JSON, we have to do work on the parameters
			var postParams = {};
			$('.formInputs').each(function(index, input) {
				
				if (input.type == "number")
					postParams[input.id] = parseInt($(input).val());
				else if (input.type == "checkbox")
					postParams[input.id] = $(input).is(":checked");
				else
					postParams[input.id] = $(input).val();
			});
			if (request.method == 'POST') {
				
				$.post(request.url, $.param(postParams))
				.done(function(response){
					$('#textResponse').val(JSON.stringify(response));
				})
				.fail(function(){
					console.log("failed POST request!");
				})
			}
			if (request.method == 'GET') {

				$.get(request.url, function(response){
					$('#textResponse').val(JSON.stringify(response));
				})
				.fail(function(){
					console.log("failed GET request!");
				})
			}
		}
	});

	// When the button is clicked, set the current user to your cookie values
	$('#setGamePlayer').on('click', function(){

		// Get the gameId for the selected game and set our cookie to that id
		var gameId = $('#gameDropdown option:selected').first().attr('gameId');
		setGameCookie(gameId);

		// Get the player information
		var players = getListPlayers(gameId);
		var playerId = $('#playerDropdown option:selected').first().attr('playerId');
		var player;
		$.each(players, function(index, curPlayer) {
			if (curPlayer.id == playerId)
				player = curPlayer;
		});
		setUserCookie(player.name, player.id);
	});

	$.getJSON('/phase0/server-REST.json', function(restRequests){

		allRequests = restRequests;
		$.each(allRequests, function(index, request){

			$('#restDropdown').append('<option url="' + request.url + '">' + request.url +  ' (' + request.method + ')</option>');
		});
		setApiInfo();

		$('#restDropdown').on('change', function() {

			setApiInfo();
		});
		//$('#backfromJSON').html('<p>' + JSON.stringify(allRequests) + '</p>');
	});
 
	$.get('/games/list', function(data, status, jqxhr){
		games = data;
		$.each(games, function(index, game){

			$('#gameDropdown').append('<option gameId=' + game.id + '>' + game.title + '</option>');
		});
		gameChanged();

		// This is a change handler (when you change an item from the GAME dropdownlist)
		$('#gameDropdown').on('change', function() {

			gameChanged();
		});
	});
}

// Set the necessary API info for the changed request
function setApiInfo() {

	var curRequest = getCurrentRequest(); 
	$('#description').empty().append(curRequest.description);
	$('#apiParameters').empty();
	$('#textResponse').val('');
	if (curRequest.method == "POST")
		setParameters(curRequest);
	else
		$('#apiParameters').append('<p>NONE</p>');
};

// You have the url from the request, get the full request
function getCurrentRequest() {

	var requestURL = $('#restDropdown option:selected').first().attr('url');
	var curRequest;
	$.each(allRequests, function(index, request){
	if (request.url == requestURL)
			curRequest = request;
	});	
	return curRequest;
};

// Create a list in HTML for your arguments
function setParameters(curRequest) {

	var types = {
		"STRING" : "text",
		"BOOLEAN" : "checkbox",
		"INTEGER" : "text"
	};
	if (curRequest.type == 'JSON')
		$('#apiParameters').append('<textarea id="reqJSON" style="height: 180px" class="span7" class="formInputs">' 
			+ JSON.stringify(curRequest.template) + '</textarea>');
	else {
		$.each(curRequest.args, function(index, arg){

			var html = '';
			// Check the type of the argument and render the appropriate object
			if (arg.type == 'ENUMERATION') {
				
				html += '<select id="' + arg.name + '" class="formInputs">';
				$.each(arg.values, function(index, value){
					html += '<option>' + value + '</option>';
				});
				html += '</select>';
			}
			else
				html += '<input id="' + arg.name + '" class="formInputs" type="' + types[arg.type] + '" ></input> ';
			html += arg.name + '<br /> (' + arg.description + ')<br/><br />'
			$('#apiParameters').append(html);
		});
	}
};

// When the selected game changes, load the new list of users
function gameChanged() {
	
	var gameId = $('#gameDropdown option:selected').first().attr('gameId');
	var players = getListPlayers(gameId);
	// Remove all the old options and add the new one for the selected game
	$('#playerDropdown').find('option').remove().end();
	$.each(players, function(index, player){

		$('#playerDropdown').append('<option style="color:' + player.color + '"playerId=' + 
			player.id + '>' + player.name + ' (' + player.id + ')</option>');
	});
};

// Get the list of all players participating in this gameId
function getListPlayers(gameId) {

	var players;
	$.each(games, function(index, game){

		if (gameId == game.id) {
		
			players = game.players;
		}
	});
	return players;
}

// Sets the cookie for the given game
function setGameCookie(gameId) {

	Cookies.set('catan.game', gameId);
}

// Sets the cookie for the given username and password
function setUserCookie(userName, userId){
	var cookieVal = '{userName:' + userName + ' 	userId":' + userId + '}';
	Cookies.set('catan.user', encodeURI(cookieVal));
}