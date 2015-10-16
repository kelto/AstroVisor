angular.module('yo').controller('SignupController', function ($scope, $modalInstance, $log, $auth) {
  /* jshint validthis: true */
  var vm = this;
  vm.user = {
    username: "",
    password: ""
  };
  vm.confirmPassword = "";

  vm.signup = function() {
    $auth.signup(vm.user)
      .then(function() {
        $modalInstance.close();
      })
      .catch(function(response) {
        //TODO: Should send a warning to the user
        $log.log('failed to signup');
        $log.log(response);
      });
  };

  vm.close = function() {
    $modalInstance.dismiss('cancel');
  };

});

