angular.module('vi-app.auth', []);
angular.module('vi-app.home', []);
angular.module('vi-app.form', []);
angular.module('vi-app.frmDetails', []);
var app =  angular.module('vi-app', ['ui.router','ngCookies','vi-app.auth','vi-app.home','vi-app.form','vi-app.frmDetails']);

