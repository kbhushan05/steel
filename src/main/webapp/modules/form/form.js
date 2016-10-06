//related code for authentication handling
'use strict';

angular.module('vi-app.form')

.controller('formCtrl', formCtrl);

// Inject my dependencies
authCtrl.$inject = [ '$scope', '$location', '$rootScope',
		'$http','$cookieStore','$state','userService'];


function formCtrl($scope, $location, $rootScope, $http,$cookieStore,$state,userService) {
	$scope.parts = [];
	$scope.part = {};
	$scope.dataLoaded = false;
    $scope.requestDeviation = false;
    $scope.isAttmentDisable = false;
    
	 var config = {
                headers : {
                    'Content-Type': 'application/json;'
                }
            }

	$scope.init = function(){
      $scope.parts = {};
      $scope.data = $rootScope.formData;
      $scope.isDisabled = false;
      $scope.isAttmentDisable = false;
      var data = $scope.data;
      if(data.status == 'REJECTED' || data.status == 'APPROVED' || data.status == 'SUBMITTED' || data.status == 'FHTV_SUBMITTED' ){
        $scope.isDisabled = true;
      }

      if(data.status == 'REJECTED' || data.status == 'APPROVED' || data.status == 'SUBMITTED' || data.status == 'FHTV_SUBMITTED' ){
         if($rootScope.isFTH == true){
            $scope.isAttmentDisable = false;
        }else{
            $scope.isAttmentDisable = true;
        }
      }
    
	}

    $scope.requestDeviationHandler = function(){
        $scope.requestDeviation = !$scope.requestDeviation;
    }

	$scope.save = function(){
       var det = angular.copy($scope.data);
        $http.post('api/orders/', det, config)
            .success(function (data, status, headers, config) {
             
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
	        $http.post('api/orders/', det, config)
	            .success(function (data, status, headers, config) {
	             
	             $scope.gotoHome();
	         })
	         .error(function (data, status, header, config) {
	         	alert("fail to submit request");
	         	$scope.gotoHome();
	        });
		}

	$scope.update = function(){
	  var det = angular.copy($rootScope.formData);
        $http.put('api/orders/', det, config)
            .success(function (data, status, headers, config) {
            
            $scope.gotoHome();
         })
         .error(function (data, status, header, config) {
         	alert("fail to update request");
         	$scope.gotoHome();
        });	
	}

	$scope.reject = function(){
	   var det = angular.copy($rootScope.formData);
	   if(det.status == 'FHTV_SUBMITTED'){
		   alert("Flow ends here");
		   return;
	   }
        $http.put('api/orders/'+ det.orderId + '?action=reject', det, config)
            .success(function (data, status, headers, config) {
            
            $scope.gotoHome();
         })
         .error(function (data, status, header, config) {
         	
         	$scope.gotoHome();
        });
	}

	$scope.approve = function(){
		var det = angular.copy($rootScope.formData);
		 if(det.status == 'FHTV_SUBMITTED'){
			   alert("Flow ends here");
			   return;
		   }
        $http.put('api/orders/'+ det.orderId + '?action=approve' ,det, config)
            .success(function (data, status, headers, config) {
            
            $scope.gotoHome();
         })
         .error(function (data, status, header, config) {
         	alert("fail to update approve");
         	$scope.gotoHome();
        });
	}

    $scope.ftTreatment = function(){
        var det = angular.copy($rootScope.formData);
        $http.post('api/orders/'+ det.orderId + '/fht' ,det, config)
            .success(function (data, status, headers, config) {
            $scope.gotoHome();
         })
         .error(function (data, status, header, config) {
            alert("fail save details");
            $scope.gotoHome();
        });
    }

	$scope.gotoHome = function(){
      $state.go('home.details');
	}

    $scope.onRequestForDeviation = function(){
        $state.go('home.deviation');
    }

	$scope.init();
}