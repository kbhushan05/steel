 app.directive('fileModel', ['$parse', function ($parse) {
   return {
      restrict: 'A',
      link: function(scope, element, attrs) {
         var model = $parse(attrs.fileModel);
         var modelSetter = model.assign;

         element.bind('change', function(event){
            scope.$apply(function(){
               scope.fileToBeUploaded = event.target.files[0];
               modelSetter(scope, element[0].files[0]);
            });
         });
      }
   };
}]);

app.service('fileUpload', ['$http','$rootScope', function ($http,$rootScope) {
   this.uploadFileToUrl = function(file, uploadUrl){
      var fd = new FormData();
      fd.append('mFile', file);

      $http.post(uploadUrl, fd, {
         transformRequest: angular.identity,
         headers:{'Content-Type': undefined
      }                   
    })
      .success(function(){
         $rootScope.$broadcast('uploadFile', "");
      })
      .error(function(){
        $rootScope.$broadcast('uploadFile', "");
      });
   }
}]);