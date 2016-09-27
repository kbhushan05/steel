//related code for authentication handling
'use strict';

angular.module('vi-app.form')

.controller('formCtrl', formCtrl);

// Inject my dependencies
authCtrl.$inject = [ '$scope', '$location', '$rootScope',
		'$http','$cookieStore','$state' ];


function formCtrl($scope, $location, $rootScope, $http,$cookieStore,$state) {
	$scope.parts = [];
	$scope.part = {};
	$scope.dataLoaded = false;

	 var config = {
                headers : {
                    'Content-Type': 'application/json;'
                }
            }

	$scope.init = function(){
      $scope.parts = {};
      $scope.data = $rootScope.formData;
	}

	$scope.save = function(){
       var det = angular.copy($scope.data);
        $http.post('http://localhost:8080/steel/api/orders/', det, config)
            .success(function (data, status, headers, config) {
             alert("New request saved");
             $scope.gotoHome();
         })
         .error(function (data, status, header, config) {
         	alert("fail to save new request");
         	$scope.gotoHome();
        });
	}
	
	$scope.submit = function(){
	       var det = angular.copy($scope.data);
	       det.status = "SUBMITTED";
	        $http.post('http://localhost:8080/steel/api/orders/', det, config)
	            .success(function (data, status, headers, config) {
	             alert("New request submitted");
	             $scope.gotoHome();
	         })
	         .error(function (data, status, header, config) {
	         	alert("fail to save new request");
	         	$scope.gotoHome();
	        });
		}

	$scope.update = function(){
	  var det = angular.copy($rootScope.formData);
        $http.put('http://localhost:8080/steel/api/orders/', det, config)
            .success(function (data, status, headers, config) {
            alert("request updated");
            $scope.gotoHome();
         })
         .error(function (data, status, header, config) {
         	alert("fail to update request");
         	$scope.gotoHome();
        });	
	}

	$scope.reject = function(){
	   var det = angular.copy($rootScope.formData);
        $http.put('http://localhost:8080/steel/api/orders/'+ det.orderId + '?action=reject', det, config)
            .success(function (data, status, headers, config) {
            alert("request rejected");
            $scope.gotoHome();
         })
         .error(function (data, status, header, config) {
         	alert("fail to update request");
         	$scope.gotoHome();
        });
	}

	$scope.approve = function(){
		var det = angular.copy($rootScope.formData);
        $http.put('http://localhost:8080/steel/api/orders/'+ det.orderId + '?action=approve' ,det, config)
            .success(function (data, status, headers, config) {
            alert("request updated");
            $scope.gotoHome();
         })
         .error(function (data, status, header, config) {
         	alert("fail to update approve");
         	$scope.gotoHome();
        });
	}

	$scope.gotoHome = function(){
      $state.go('home.details');
	}

	$scope.init();
}