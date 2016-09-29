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
		$location.path('/auth');
	}

	$scope.newRequest = function(){
		$http({
			method : "GET",
			url : "api/orders/new"
		}).then(function mySucces(response) {
			$rootScope.formData = angular.fromJson(response.data);
			$rootScope.fromEnable = true;
			$state.transitionTo('home.request');
			//$state.go('home.request');
		}, function myError(response) {
			alert("Error Generating request");
		});
	}
}