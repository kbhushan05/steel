/**
*  Module
*
* Description
*/
angular.module('steel.core', [])

.factory('User', function(){

	var User = function User(){
		this.username ;
		this.password ;
		this.authToken;
	}

	return User;
})

.service('Api', function(){
	this.host = 'localhost';
	this.port = '8080';
	this.baseUrl = 'http://'+this.host+':'+this.port+'/steel/api';

})