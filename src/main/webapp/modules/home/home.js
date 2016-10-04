//related code for authentication handling
'use strict';

angular.module('vi-app.home')

.controller('homeCtrl', homeCtrl);

// Inject my dependencies
authCtrl.$inject = [ '$scope', '$location', '$rootScope',
'$http','$cookieStore','$state' ];


function homeCtrl($scope, $location, $rootScope, $http,$cookieStore,$state) {

	$scope.logout = function(){
		$cookieStore.remove('viApp');

		$http.delete("api/auth/logout?username="+$scope.user.details.username, $scope.config)
	   .then(
	       function(){
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
			url : "api/orders/new?supplierName=" + $scope.user.details.supplierName
		}).then(function mySucces(response) {
			$rootScope.formData = angular.fromJson(response.data);
			$rootScope.fromEnable = true;
			$state.transitionTo('home.request');
			//$state.go('home.request');
		}, function myError(response) {
			alert("Error Generating request");
		});
	}

	$scope.init = function(){
      $scope.user = $rootScope.loggedInUser;
	} 

	$scope.init();
}