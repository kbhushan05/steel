angular.module('vi-app.auth', []);
angular.module('vi-app.home', []);
angular.module('vi-app.form', []);
angular.module('vi-app.frmDetails', []);
angular.module('vi-app.records',[])
var app =  angular.module('vi-app', ['ui.router','ngCookies','vi-app.auth','vi-app.home','vi-app.form','vi-app.frmDetails','vi-app.records']);

app.directive("restricted", function($cookieStore,$location) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            // Some auth check function
           var user = $cookieStore.get('viApp');
           if(user != undefined && user.token != ""){
               if( user.type != "Supplier"){
                 element.css('display', 'none');
                 element.css('pointer-events', 'none');
               }
            }
            if(user == undefined ){
               $location.path("/");
            }
                
           
        }
    }
});

app.directive("supplierOnly", function($cookieStore,$location) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            // Some auth check function
           var user = $cookieStore.get('viApp');
           if(user != undefined && user.type != "Supplier"){
                 element.css('display', 'none');
                 element.css('pointer-events', 'none');
            }
            if(user == undefined ){
               $location.path("/");
            }
                
           
        }
    }
});

app.directive("adminOnly", function($cookieStore,$location) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            // Some auth check function
           var user = $cookieStore.get('viApp');
           if(user != undefined && user.type != "Admin"){
                 element.css('display', 'none');
                 element.css('pointer-events', 'none');
            }
            if(user == undefined ){
               $location.path("/");
            }
                
           
        }
    }
});

app.service('userService', function($cookieStore,$rootScope){
  this.user = {};

  this.setUser = function(){
    var user = $cookieStore.get('viApp');
    if(user != undefined && user.token != ""){
      $rootScope.loggedInUser = user;
      this.user = user;
    }
  }

  this.getUser = function(){
     return this.user;
  }

  this.getRole = function(){
     return this.user.details.role;
  }
  this.isSupplier = function(){
    return (this.user.details.role == "SUPPLIER"?true:false)
  }
  this.isAdmin = function(){
     return (this.user.details.role == "ADMIN"?true:false)
  }
  this.getSuppliername = function(){
    return this.user.details.supplierName;
  }
  this.getUsername = function(){
    return this.user.details.username;
  }
});

