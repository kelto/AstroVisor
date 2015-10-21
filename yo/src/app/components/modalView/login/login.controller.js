angular.module('yo').controller('LoginController', function ($scope, $modalInstance, $log, $auth, $state/*, newPath, oldPath*/) {
  /* jshint validthis: true */
  var vm = this;
  vm.user = {
    username: "",
    password: ""
  };

  vm.login = function() {

    $auth.login(vm.user)
      .then(function() {
        $state.go('home');
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
    $state.go('home.login');
  };

});

