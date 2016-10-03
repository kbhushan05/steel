//from main app we are going to set only ui-routing, rest all functionality will be handled by it's respective module
app.config(function($stateProvider,$urlRouterProvider,$httpProvider)
{
	$httpProvider.interceptors.push(function($q,$rootScope) {
		return {
			'request': function(config) {
				var user = $rootScope.loggedInUser;
				if(user != undefined && user.token != ""){
				  config.headers['Bearer'] = user.token;
				}
				return config;
			},

			'requestError': function(rejection) {
				return $q.reject(rejection);
			},
			'response': function(response) {
				return response;
			},

			'responseError': function(rejection) {
				return $q.reject(rejection);
			}
		};
	});

	$urlRouterProvider
		.otherwise('/auth');

    //state which will load login screen
	$stateProvider
		.state('auth', {
			url: '/auth',
			templateUrl: "modules/auth/auth.html",
			controller: "authCtrl"
		})
		//state home
		.state('home', {
			url: '/home',
			templateUrl: "modules/home/home.html",
			controller: "homeCtrl"
		})
		//state home.request
		.state('home.request', {
			url: '/revFrms',
			templateUrl: "modules/form/form.html",
			controller: "formCtrl"
		})

		//state home.request
		.state('home.details', {
			url: '/details',
			templateUrl: "modules/home/details.html",
			controller: "frmDetailsCtrl"
		})

		//state home.request
		.state('home.deviation', {
			url: '/deviation',
			templateUrl: "modules/deviation/deviation.html",
			controller: "deviationCtrl"
		})
});
