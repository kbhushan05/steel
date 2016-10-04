//related code for authentication handling
'use strict';

angular.module('vi-app.auth')

.controller('authCtrl', authCtrl);

// Inject my dependencies
authCtrl.$inject = [ '$scope', '$location', '$rootScope',
		'$http','$cookieStore'];


function authCtrl($scope, $location, $rootScope, $http,$cookieStore) {
	$scope.user = {
		user_id : "",
		password : "",
		name : "",
		user_type : "",
		token:"",
		details:{}
	};

	$scope.config = {
		headers : {
			'Content-Type' : 'application/json'
		}
	};

	$scope.login = function() {
		var user = {};
		user.username = $scope.user.username;
		user.password = $scope.user.password;
        $http.post("api/auth/login?username="+$scope.user.username, angular.toJson(user) , $scope.config)
	   .then(
	       function(response){
	           $scope.user.token = "Bearer "+ response.data.token;
	           $scope.user.details = response.data.user;
	           var u = angular.copy($scope.user);
	           $rootScope.loggedInUser = u;
	           $cookieStore.put('viApp',u);
	           $location.path("/home/details");	
	       }, 
	       function(response){
	         alert("Invalid Credentials")
	       }
	    );
	};


	$scope.init = function(){
		var user = $cookieStore.get('viApp');
		if(user != undefined && user.token != ""){
			$rootScope.loggedInUser = user;
			$location.path("/home");
		}
	}
	$scope.init();
	
}