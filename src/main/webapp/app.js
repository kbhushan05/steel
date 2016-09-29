angular.module('vi-app.auth', []);
angular.module('vi-app.home', []);
angular.module('vi-app.form', []);
angular.module('vi-app.frmDetails', []);
var app =  angular.module('vi-app', ['ui.router','ngCookies','vi-app.auth','vi-app.home','vi-app.form','vi-app.frmDetails']);

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

