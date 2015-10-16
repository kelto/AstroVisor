angular.module('yo').directive('confirmPassword', function(){
  return{
    require:'ngModel',
    scope : {
      confirmPassword: '='
    },
    link: function(scope, elem, attrs, ctrl){

      ctrl.$parsers.unshift(confirmPassword);
      function confirmPassword(viewValue){
        var valid = scope.confirmPassword === viewValue;
        ctrl.$setValidity('confirm-password',valid);

        return viewValue;
      }

    }
  };
});
