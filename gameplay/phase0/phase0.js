// phase0.js
// Author: Spencer Bench

// Global variables
var _API;
var _API_LOC = '../server-api.json';
var _DYN = '_dyn_';
var _GAMES;

// On document ready, initialize the event handlers and the API
jQuery(document).ready(function() {
	initHandlers();
	jQuery.getJSON(_API_LOC, initAPI);
});

// Add an argument form to the page
function addArgument(argument) {
	var type = argument.type;
	var name = argument.name;
	var id = _DYN + name;
	// Begin building the HTML string
	var str = '<p><div>';
	if (type == 'BOOLEAN') {
		// Create a checkbox
		str += '<input id="' + id + '" type="checkbox"> ' + name + '</input>';
	} else if (type == 'ENUMERATION') {
		// Create a combo box
		str += name + ':</div><div><select id="' + id + '"></select>';
	} else if (type == 'INTEGER') {
		// Create a spinner
		str += name + ':</div><div><input id="' + id + '" class="text" type="number"></input>';
	} else if (type == 'STRING') {
		// Create a text field
		str += name + ':</div><div><input id="' + id + '" class="text" type="text"></input>';
	}
	// Finish building the HTML string and add it to the page
	str += '</div><div class="hint">' + argument.description + '</div></p>';
	jQuery('#arguments').append(str);
	// If the argument is an enumeration, populate its combo box
	if (type == 'ENUMERATION') {
		var vals = argument.values;
		for (var n in vals) {
			jQuery('#' + id).append('<option value="' + vals[n] + '">' + vals[n] + '</option>');
		}
	}
}

// Make a call to the API
function callAPI(index, data, done, fail, always) {
	var endpoint = _API[index];
	console.debug('Sending request to ' + endpoint.url + ':\n' + JSON.stringify(data, null, '\t') + '\n');
	jQuery.ajax({
		type: endpoint.method,
		url: endpoint.url,
		data: data
	})
	.done(done)
	.fail(fail)
	.always(always);
}

// Get the value of an argument form element
function getArgument(name, type) {
	var element = jQuery('#' + _DYN + name);
	if (type == 'BOOLEAN') {
		return element.prop('checked');
	} else {
		return element.val();
	}
}

// Retrieve the index of a specific API endpoint
function getEndpointIndex(url) {
	for (var index in _API) {
		if (_API[index].url == url) {
			return index;
		}
	}
}

// Initialize the API (callback function)
function initAPI(response) {
	// Store the API globally, then initialize the endpoint list
	_API = response;
	initEndpoints();
	// Initialize the game list
	var index = getEndpointIndex('/games/list');
	callAPI(index, null, initGames, requestFail, null);
}

// Initialize the endpoint list
function initEndpoints() {
	// Populate the endpoint list
	for (var n in _API) {
		jQuery('#endpoint').append('<option value="' + n + '">' + _API[n].url + '</option>');
	}
	// Update the endpoint display
	updateEndpoint();
}

// Initialize the game list (callback function)
function initGames(response) {
	// Store the games globally
	_GAMES = response;
	// Populate the game list, then update the user list
	for (var n in _GAMES) {
		jQuery('#game').append('<option value="' + n + '">' + _GAMES[n].title + '</option>');
	}
	updateUsers();
}

// Initialize the event handlers
function initHandlers() {
	jQuery('#endpoint').change(updateEndpoint);
	jQuery('#game').change(updateUsers);
	jQuery('#joinButton').click(join);
	jQuery('#requestButton').click(request);
}

// Join the current game as the current user
function join() {
	// Clear existing join status
	jQuery('#joinStatus').empty();
	var gameIndex = jQuery('#game').val();
	var userIndex = jQuery('#user').val();
	var game = _GAMES[gameIndex];
	var user = game.players[userIndex];
	var argStr = 'username=' + user.name + '&password=' + jQuery('#password').val();
	var endpointIndex = getEndpointIndex('/user/login');
	// Join the current game (callback function)
	var join2 = function(response) {
		argStr = 'color=' + user.color + '&id=' + game.id;
		endpointIndex = getEndpointIndex('/games/join');
		callAPI(endpointIndex, argStr, joinDone, joinFail, null);
	}
	// Log in as the current user
	callAPI(endpointIndex, argStr, join2, joinFail, null);
}

// The user successfully joined a game (callback function)
function joinDone(response) {
	jQuery('#joinStatus').html('<span class="good">' + response + '</span>');
}

// The user failed to join a game (callback function)
function joinFail(response) {
	jQuery('#joinStatus').html('<span class="bad">' + response.responseText + '</span>');
}

// Send a request to the current endpoint
function request() {
	// Clear existing response status
	jQuery('#responseStatus').empty();
	var index = jQuery('#endpoint').val();
	var endpoint = _API[index];
	var argStr;
	if (endpoint.type == 'JSON') {
		argStr = jQuery('#template').val();
	} else if (endpoint.type == 'FORM') {
		var args = endpoint.args;
		// Build the first element of the argument string, then build the remaining elements
		argStr = args[0].name + '=' + getArgument(args[0].name, args[0].type);
		for (var n = 1; n < args.length; n++) {
			argStr += '&' + args[n].name + '=' + getArgument(args[n].name, args[n].type);
		}
	}
	callAPI(index, argStr, requestDone, requestFail, null);
}

// The request to the server was successful (callback function)
function requestDone(response) {
	jQuery('#responseStatus').html('<span class="good">200 OK</span>');
	var text = JSON.stringify(response, null, '\t');
	var rows = text.split('\n').length;
	jQuery('#response')
		.attr('rows', rows)
		.text(text);
}

// The request to the server failed (callback function)
function requestFail(response) {
	jQuery('#responseStatus').html('<span class="bad">' + response.responseText + '</span>');
}

// Update the endpoint display
function updateEndpoint() {
	var index = jQuery('#endpoint').val();
	var endpoint = _API[index];
	// Update the description and request method
	jQuery('#description').text(endpoint.description);
	jQuery('#method').text(endpoint.method);
	// Clear existing argument info
	jQuery('#argumentsLabel').empty();
	jQuery('#arguments').empty();
	// Dynamically create argument forms
	if (endpoint.type == 'JSON') {
		var template = JSON.stringify(endpoint.template, null, '\t');
		var rows = template.split('\n').length;
		jQuery('#arguments').html('<textarea id="template" rows="' + rows + '">' + template + '</textarea>');
	} else if (endpoint.type == 'FORM') {
		for (var n in endpoint.args) {
			addArgument(endpoint.args[n]);
		}
	} else {
		jQuery('#argumentsLabel').html('None');
	}
}

// Update the user list
function updateUsers() {
	// Clear the user list, then repopulate it
	jQuery('#user').empty();
	var index = jQuery('#game').val();
	var users = _GAMES[index].players;
	for (var n in users) {
		jQuery('#user').append('<option value="' + n + '">' + users[n].name + '</option>');
	}
}
