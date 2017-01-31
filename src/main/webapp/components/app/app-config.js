/**
*  Module
*
* Description
*/
angular.module('steel.app')

.config(['$locationProvider','$routeProvider', 

	function($locationProvider, $routeProvider){
		$locationProvider.hashPrefix('!');

		$routeProvider.
		when('/',{
			template : '<authentication></authentication>'
		})
		.when('/test',{
			template : '<h2> from test</h2>'
		})
		.when('/dashboard',{
			template : '<dashboard></dashboard>'
		})
		.when('/logout',{
			template : '<logout></logout>'
		})
		.otherwise('/test');
	}

	]);