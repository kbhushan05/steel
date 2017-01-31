/**
* steel.authentication Module
*
* Description
*/
'use strict';

angular.module('steel.authentication')


.service('AuthenticationService', ['$http','$q','User','Api','SessionService',function($http,$q, User,Api,sessionService){
	var self = this;
	
	this.authenticate = function (user){
		var deferred = $q.defer();
		return $http.post(Api.baseUrl+'/auth/login?username='+user.username, user,null)
		.then(
			function(response){
				// success
				var loggedInUser = new User();
				loggedInUser.username = response.data.user.username;
				loggedInUser.roles = response.data.user.role;
				sessionService.currentUser(loggedInUser);
				sessionService.authToken(response.data.token);

				deferred.resolve(loggedInUser);
				return deferred.promise;
			}, 
			function(response){
				// error
				deferred.reject(response.status);
				return deferred.promise;
			});
	}

	this.logout = function(user){
		var deferred = $q.defer;
		return $http.delete(Api.baseUrl+'/auth/logout?username='+user.username)
		.then(
			function(response){
				//success
				deferred.resolve(response);
				return deferred.promise;
			},
			function(response){
				//error
				deferred.resolve(response);
				return deferred.promise;
			});
	}

}])

.service('SessionService', ['User', function(User){
	this.user;
	this.authTokenValue;

	this.currentUser = function (user){
		if(typeof user === "undefined")
		return this.user;

		this.user = user;
	}

	this.authToken = function(token){
		if(typeof token === 'undefine')
			return this.authTokenValue;

		this.authTokenValue = token;
	}


}]);