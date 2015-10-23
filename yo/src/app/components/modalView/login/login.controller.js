angular.module('yo').controller('LoginController', function ($scope, $modalInstance, $auth, toastr) {
  /* jshint validthis: true */
  var vm = this;
  vm.user = {
    username: "",
    password: ""
  };

  vm.login = function() {

    $auth.login(vm.user)
      .then(function() {
        toastr.success('Login success');
        $modalInstance.close();
      })
      .catch(function() {
        toastr.error('Either the user does not exist or the password does not fit.','Failed to login');
      });
  };

  vm.close = function() {
    $modalInstance.dismiss('cancel');
  };

});

