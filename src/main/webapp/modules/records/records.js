//related code for authentication handling
'use strict';

angular.module('vi-app.records')

.controller('recordsCtrl', recordsCtrl);

// Inject my dependencies
authCtrl.$inject = [ '$scope', '$location', '$rootScope',
		'$http','$cookieStore','$state','userService' ];


function recordsCtrl($scope, $location, $rootScope, $http,$cookieStore,$state,userService) {
  $scope.orders = [];
  $scope.init = function(){
  	$scope.orders = [];
    var FHurl = (userService.isSupplier()?"api/orders?supplierName="+ userService.getSuppliername() + "&status=approved":"api/orders?status=fhtv_submitted")

  	$http.get(FHurl)
    .then(function(response) {
        $scope.orders = angular.fromJson(response.data);
    }, function(response) {
        alert("Unable to fetch details");
    });
  }

  $scope.view = function(data){
	  if(data.status == 'FHTV_APPROVED' || data.status == 'FHTV_REJECTED'){
		  alert("flow ends here.")
		  return;
	  }
    $rootScope.isFHT = false;
    var FHUrl = (userService.isSupplier()?"api/orders/"+data.orderId + "/fht":"api/orders/"+data.orderId)
	  $http({
			method : "GET",
			url : FHUrl
		}).then(function mySucces(response) {
			$rootScope.formData = angular.fromJson(response.data);
			$rootScope.fromEnable = false;
      $rootScope.isFTH = true;
			$state.transitionTo('home.request');
			//$state.go('home.request');
		}, function myError(response) {
			alert("Error loading request details");
		});

  }

  $scope.init();
}