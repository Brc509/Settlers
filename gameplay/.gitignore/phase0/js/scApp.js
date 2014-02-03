var scApp = angular.module('scApp', [])

.controller('playerCtrl', ['$scope', '$http',
	function ($scope, $http) {

		//GET THE JSON API DOCUMENT
		$http.get('server-REST.json')
			.success(function(data) {
				$scope.commands = data;
			})
			.error(function(data, status, headers, config) {
				console.log('Error getting the Json doc');
			});

		//GET THE LIST OF COMMANDS
		$http.get('/games/list')
			.success(function(games) {
				$scope.games = games;
				console.log(games);
			})
			.error(function(data, status, headers, config) {
				console.log('Error getting Games');
			});

		//LOGIN
		$scope.setPlayer = function () {
			$scope.player = this.p;
		}
		$scope.login = function () {
			var str = $('#loginForm').serialize();

			$http.put('/user/login', str)
				.success(function (response, status) {
					$scope.response = "Logged In";
					$scope.resStatus = status;
				})
				.error(function(data, status, headers, config) {
					console.log('Error Logging In', status);
				});
		};

		//JOIN GAME
		$scope.joinGame = function() {
			console.log($scope.player);

			var request = {
				color: $scope.player.color,
				id: $scope.player.id
			}

			var str = $.param(request);
			console.log(str);
			$http.put('/games/join', str)
				.success(function (response, status) {
					console.log('Joined game', status);
					$scope.response = "Joined Game";
					$scope.resStatus = status;
				})
				.error(function(data, status, headers, config) {
					console.log('Error Joining Game', status);
				});

		}

		//EXECUTE A COMMAND
		$scope.sendCommand = function () {
			var com = $scope.command;
			$scope.response = null;
			if (com.method == 'GET') {
				console.log('GET COMMAND');

				$http.get(com.url)
					.success(function(response) {
						console.log(response);
						$scope.response = JSON.stringify(response, null, ' ');
						console.log($scope.response);
					})
					.error(function(data, status, headers, config) {
						console.log('Error Getting' + com.url, status);
					});
			}
			else {
				console.log('POST COMMAND');
				if ('template' in com) {
					var str = com.template;
				}
				else {
					var str = $('#comForm').serialize();
				}
				console.log(str);
				$http.put(com.url, str)
					.success(function(response, status) {
						$scope.response = response;
						$scope.resStatus = status;
						console.log(status);
					})
					.error(function(data, status, headers, config) {
						console.log('Error Posting' + com.url, status);
					});
			}
		};
	}]
);

scApp.directive('json', function() {
  return {
    restrict: 'A', // only activate on element attribute
    require: 'ngModel', // get a hold of NgModelController
    link: function(scope, element, attrs, ngModelCtrl) {
      function fromUser(text) {
        // Beware: trim() is not available in old browsers
        if (!text || text.trim() === '')
          return {}
        else
          // TODO catch SyntaxError, and set validation error..
          return angular.fromJson(text);
      }

      function toUser(object) {
          // better than JSON.stringify(), because it formats + filters $$hashKey etc.
          return angular.toJson(object, true);
      }
      
      // push() if faster than unshift(), and avail. in IE8 and earlier (unshift isn't)
      ngModelCtrl.$parsers.push(fromUser);
      ngModelCtrl.$formatters.push(toUser);
      
      // $watch(attrs.ngModel) wouldn't work if this directive created a new scope;
      // see http://stackoverflow.com/questions/14693052/watch-ngmodel-from-inside-directive-using-isolate-scope how to do it then
      scope.$watch(attrs.ngModel, function(newValue, oldValue) {
        if (newValue != oldValue) {
          ngModelCtrl.$setViewValue(toUser(newValue));
          // TODO avoid this causing the focus of the input to be lost..
          ngModelCtrl.$render();
        }
      }, true); // MUST use objectEquality (true) here, for some reason..
    }
  };  
});
