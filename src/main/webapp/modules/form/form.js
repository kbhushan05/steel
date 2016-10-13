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
  $scope.isCILEditable = true;
  $scope.enableCILComments = true;
  $scope.showCourier = false;
  $scope.disableCourier = false;
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
    $scope.isDeviationDisable = true;
    if($scope.data.status == 'NEW' || $scope.data.status == 'APPROVED'){
      var value = new Date();
      $scope.data.requestDate = value;
    }else{
      var value = new Date($scope.data.requestDate);
      $scope.data.requestDate = value;
    }
    var data = $scope.data;
    if(data.status == 'REJECTED' || data.status == 'APPROVED' || data.status == 'SUBMITTED' || data.status == 'FHTV_SUBMITTED' || data.status == 'FHTV_APPROVED' || data.status == 'FHTV_REJECTED'){
      $scope.isDisabled = true;
    }

    if(data.status == 'REJECTED' || data.status == 'APPROVED' || data.status == 'SUBMITTED' || data.status == 'FHTV_SUBMITTED' || data.status == 'FHTV_APPROVED' || data.status == 'FHTV_REJECTED'){
     if($rootScope.isFTH == true){
      $scope.isAttmentDisable = false;
    }else{
      $scope.isAttmentDisable = true;
    }
  }
  if(userService.getRole == 'SUPPLIER'){
    if(data.status == 'NEW' || data.status == 'SAVED'){
     $scope.isDeviationDisable = false;
   }else if(data.status == 'FHTV_NEW'){
     $scope.isDeviationDisable = false;
   }else{
     $scope.isDeviationDisable = true;
   }
 }

 if(userService.getRole == 'ADMIN'){
   if(data.status == 'SUBMITTED' || data.status == 'FHTV_SUBMITTED'){
     $scope.isCILEditable = false;
   }    
 }else{
  $scope.isCILEditable = true;
}
if(userService.getRole() == 'ADMIN'){
  if(data.status == 'SUBMITTED' || data.status == 'FHTV_SUBMITTED'){
   $scope.enableCILComments = false;
 }
}
if(data.status == 'FHTV_NEW' || data.status == 'FHTV_SUBMITTED' || data.status == 'FHTV_APPROVED' || data.status == 'FHTV_REJECTED' ){
  $scope.showCourier = true;
}

if(data.status != 'FHTV_NEW'){
	  $scope.disableCourier = true;
}

$scope.getTotalSteelTonnage();    
}

$scope.requestDeviationHandler = function(){
  $scope.requestDeviation = !$scope.requestDeviation;
}

$scope.getTotalSteelTonnage = function(){
  $scope.data.steelTonage = $scope.data.alreadyAvailableSteelTonage + $scope.data.newSteelToBuy;
}



$scope.save = function(){
 if(!$scope.validateForm()){
  return;
}
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
  if(!$scope.validateForm()){
    return;
  }
  var det = angular.copy($scope.data);
  det.status = "SUBMITTED";
  $http.post('api/orders/', det, config)
  .success(function (data, status, headers, config) {

    $scope.gotoHome();
  })
  .error(function (data, status, header, config, statusText) {
   alert("Email server is not accessible");
   $scope.gotoHome();
 });
}

$scope.update = function(){
  if(!$scope.validateForm()){
    return;
  }
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
  var url = 'api/orders/'+ det.orderId + '?action=reject';
  if(det.status == 'FHTV_SUBMITTED'){
    url = 'api/orders/'+ det.orderId +'/fht/reject';
  }
  $http.put(url, det, config)
  .success(function (data, status, headers, config) {

    $scope.gotoHome();
  })
  .error(function (data, status, header, config) {

    $scope.gotoHome();
  });
}

$scope.approve = function(){
  var det = angular.copy($rootScope.formData);
  var url = 'api/orders/'+ det.orderId + '?action=approve';
  if(det.status == 'FHTV_SUBMITTED'){
    url = 'api/orders/'+ det.orderId +'/fht/approve';
  }
  $http.put( url,det, config)
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

$scope.validateForm = function(){
  var message = '';
  var partWeight = 0;
  angular.forEach($scope.data.partDetails, function(value, key){
    if(!isNaN(value.noOfParts)){
        partWeight += value.weight;
        partWeight += value.cutWeight;  
    }
  });
  var totalSteelAllowed = $scope.data.steelTonage * 1000;
  if(partWeight > totalSteelAllowed){
    message += 'Part weight should not excced Total Steel Tonnage. \n'
  }  

  if($scope.data.poNumber == ''){
 //   isValid = false;
    message += 'Enter PO Number. \n'
  }
  if($scope.data.steelHeatNumber == '' || $scope.data.steelHeatNumber == null){
  //  isValid = false;
    message += 'Enter Steel Heat Number. \n';
  }
  /*removed as per request*/
  /*if($scope.data.alreadyAvailableSteelTonage == ''){
      isValid = false;
      message += 'Enter Available Steel. \n';
    }*/
    if($scope.data.newSteelToBuy == ''){
      message += 'Enter new steel to buy. \n';
    }
    if($scope.data.refStandard == '' || $scope.data.refStandard == null){
      message += 'Enter Ref. Standard. \n';
    }
    if($scope.data.forgerSupplierCode == ''|| $scope.data.forgerSupplierCode == null){
      message += 'Enter Ref. forger supplier code. \n';
    }
    if($scope.data.steelMill == ''){
      message += 'Select steel mill. \n';
    }

    if($scope.showCourier){
     if(data.courierReceipt=='' || data.courierReceipt || data.courierReceipt){
      message += 'Enter courier details. \n';
    }
  }

  if(message.length > 5){
    alert(message);
    return false;
  }
  return true;
}

$scope.init();
}