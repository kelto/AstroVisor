(function(){
  'use strict';

  angular.module('yo').controller('SignupController', function ($scope, $modalInstance, $auth, toastr) {
    var vm = this;
    vm.user = {
      username: "",
      password: ""
    };
    vm.confirmPassword = "";

    vm.signup = function() {
      $auth.signup(vm.user)
        .then(function() {
          toastr.success('Signup success')
          $modalInstance.close();
        })
        .catch(function() {
          toastr.error('This username is probably used already.', 'Failed to signup');
        });
    };

    vm.close = function() {
      $modalInstance.dismiss('cancel');
    };

  });
})();
