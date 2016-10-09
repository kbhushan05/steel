//related code for authentication handling
'use strict';

angular.module('vi-app.home')

.controller('homeCtrl', homeCtrl);

// Inject my dependencies
authCtrl.$inject = [ '$scope', '$location', '$rootScope',
'$http','$cookieStore','$state' ,'userService'];


function homeCtrl($scope, $location, $rootScope, $http,$cookieStore,$state,userService) {

	$scope.logout = function(){
		$cookieStore.remove('viApp');

		$http.delete("api/auth/logout?username="+userService.getUsername(), $scope.config)
	   .then(
	       function(){
	       	   $cookieStore.remove('vi-app')
	           $location.path("/");	
	       }, 
	       function(){
	         alert("Fail to logout. Contact admin for more details")
	       }
	    );
	}

	$scope.user = {};

	$scope.newRequest = function(){
		$http({
			method : "GET",
			url : "api/orders/new?supplierName=" + userService.getSuppliername()
		}).then(function mySucces(response) {
			$rootScope.formData = angular.fromJson(response.data);
			$rootScope.fromEnable = true;
			
			if($state.current.name == 'home.request'){
			  $state.reload();
			}
			else{
				$state.transitionTo('home.request');
			}
			//$state.go('home.request');
		}, function myError(response) {
			alert("Error Generating request");
		});
	}

	$scope.init = function(){
        var user = $cookieStore.get('viApp');
		if(user != undefined && user.token != ""){
			$rootScope.loggedInUser = user;
			userService.setUser();
		}else{
			$location.path("/auth");
		}
		$rootScope.isFTH = false;
	} 

	$scope.init();
}