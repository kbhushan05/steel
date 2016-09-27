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
		token:""
	};

	$scope.config = {
		headers : {
			'Content-Type' : 'application/json'
		}
	};

	$scope.login = function() {

		/*$http.post(url, data, config)
   .then(
       function(response){
         // success callback
       }, 
       function(response){
         // failure callback
       }
    );*/
        if($scope.user.name == "admin" && $scope.user.password == "admin"){
        	$scope.token = "12345";
           var u = angular.copy($scope.user);
           $cookieStore.put('viApp',u);
           $location.path("/home/details");	
        }else{
        	alert("Invalid Credentials")
        }
		
	};


	$scope.init = function(){
		var user = $cookieStore.get('viApp');
		if(user != undefined && user.token == ""){
			$location.path("/home");
		}
	}
	$scope.init();
	
}