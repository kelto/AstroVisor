angular.module('yo').controller('LoginController', function ($scope, $modalInstance, $log, $auth) {
  /* jshint validthis: true */
  var vm = this;
  vm.user = {
    username: "",
    password: ""
  };

  vm.planets = function() {
    $auth.login(vm.user)
      .then(function() {
        $modalInstance.close();
      })
      .catch(function(response) {
        //TODO: Should send a warning to the user
        $log.log('failed to login');
        $log.log(response);
      });
  };

  vm.close = function() {
    $modalInstance.dismiss('cancel');
  };

});

