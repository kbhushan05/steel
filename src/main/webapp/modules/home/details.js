//related code for authentication handling
'use strict';

angular.module('vi-app.frmDetails')

.controller('frmDetailsCtrl', frmDetailsCtrl);

// Inject my dependencies
frmDetailsCtrl.$inject = [ '$scope', '$location', '$rootScope',
'$http','$cookieStore','$state','userService' ];


function frmDetailsCtrl($scope, $location, $rootScope, $http,$cookieStore,$state,userService) {
	$scope.orders = [];
  $rootScope.state = "Home";
  $scope.init = function(){
  	$scope.orders = [];
  	var url = userService.getRole() == 'ADMIN' ? "api/orders?status=submitted,fhtv_submitted,approved,rejected,fhtv_approved,fhtv_rejected": "api/orders?supplierName="+userService.getSuppliername();
  	$http.get(url)
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
      $rootScope.state = "Home";
			$state.transitionTo('home.request');
			//$state.go('home.request');
		}, function myError(response) {
			alert("Error Generating request");
		});

  }

  $scope.init();
}