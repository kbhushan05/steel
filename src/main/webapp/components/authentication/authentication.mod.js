/**
*  Module
*
* Description
*/
'use strict';
angular.module('steel.authentication', ['steel.core','ngStorage'])

.config(['$httpProvider',function($httpProvider) {
	$httpProvider.interceptors.push('httpInterceptor');
}])

.factory('httpInterceptor', ['SessionService',function(sessionService){

	var httpInterceptor = {
		request : function(config){
			config.headers['Authorization'] = 'Bearer '+ sessionService.authToken();
			return config;
		}
	};
	return httpInterceptor;
}])

.component('authentication',{
	templateUrl : './components/authentication/authentication-view.html',
	controller : ['AuthenticationService','$location','User','SessionService',function AuthenticationController(authenticationService,$location,User,sessionService){
		var self = this;
		self.user = new User();
		self.message;
		self.unauthorized = false;

		self.authenticate = function(user){

			authenticationService.authenticate(user).
			then(
				function(loggedInUser){
					user = sessionService.currentUser();
					$location.path('/dashboard');
			},
				function(error){
					self.unauthorized = true;
					self.message = error;
			});
			
		}
	}]
})

.component('logout',{
	templateUrl : 'components/authentication/logout-view.html',
	controller : ['AuthenticationService','SessionService','$location','User', function LogoutConroller(authenticationService,sessionService, $location, User){
		
		this.logout = function(){
			var user = sessionService.currentUser();
			authenticationService.logout(user)
			.then(
				function(response){
					alert("hello world !!");
				},
				function(error){
					alert("Error" + error);
			});
		}
	}]

})

