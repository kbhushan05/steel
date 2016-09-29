//related code for authentication handling
'use strict';

angular.module('vi-app.frmDetails')

.controller('frmDetailsCtrl', frmDetailsCtrl);

// Inject my dependencies
frmDetailsCtrl.$inject = [ '$scope', '$location', '$rootScope',
'$http','$cookieStore','$state' ];


function frmDetailsCtrl($scope, $location, $rootScope, $http,$cookieStore,$state) {
	$scope.orders = [];
  $scope.init = function(){
  	$scope.orders = [];
  	$http.get("api/orders/")
    .then(function(response) {
        $scope.orders = angular.fromJson(response.data);
    }, function(response) {
        alert("Unable to fetch details");
    });
  }

  $scope.view = function(data){
	  $http({
			method : "GET",
			url : "api/orders/"+data.orderId
		}).then(function mySucces(response) {
			$rootScope.formData = angular.fromJson(response.data);
			$rootScope.fromEnable = false;
			$state.transitionTo('home.request');
			//$state.go('home.request');
		}, function myError(response) {
			alert("Error Generating request");
		});

  }

  $scope.init();
}