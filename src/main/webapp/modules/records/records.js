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

  /*$scope.getclass = function (status) {
       if(status == 'SAVED'){
          return "glyphicon glyphicon-floppy-save";
       }else if(status == "REJECTED" || status == "FHTV_REJECTED "){
          return "glyphicon glyphicon-remove";
       }else if(status == "FHTV_APPROVED" || status == "APPROVED"){
          return "glyphicon glyphicon-flag";
       }else if(status == "FHTV_SUBMITTED" || status == "SUBMITTED"){
          return "glyphicon glyphicon-ok";
       }else{
        return "";
       }
    };*/

    /*$scope.getStatusColor = function (status) {
       var style = {};
        if(status == 'SAVED'){
          style = {'background-color':'#103170','color':'white'};
       }else if(status == "REJECTED" || status == "FHTV_REJECTED "){
           style = {'background-color':'#a91304','color':'white'};
       }else if(status == "FHTV_APPROVED" || status == "APPROVED"){
          style = {'background-color':'#097c3a','color':'white'};
       }else if(status == "FHTV_SUBMITTED" || status == "SUBMITTED"){
          style = {'background-color':'#103170','color':'white'};
       }else{
        
       }

       return style;
    };*/

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
      $rootScope.state == "FTH";
			$state.transitionTo('home.request');
			//$state.go('home.request');
		}, function myError(response) {
			alert("Error loading request details");
		});

  }

  $scope.init();
}