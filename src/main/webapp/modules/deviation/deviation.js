//related code for authentication handling
'use strict';

angular.module('vi-app.deviation')

.controller('deviationCtrl', deviationCtrl);

// Inject my dependencies
authCtrl.$inject = [ '$scope', '$location', '$rootScope',
		'$http','$cookieStore','$state' ];


function deviationCtrl($scope, $location, $rootScope, $http,$cookieStore,$state) {
	
}