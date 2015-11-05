(function () {
  angular.module('yo').controller('UserController', function ($scope, toastr,userService,$modalInstance) {
    /* jshint validthis: true */
    var vm = this;
    vm.user = {username: userService.getUsername(), password: ''};
    vm.password = '';

    vm.update = function() {
      userService.update(vm.user,vm.password).then(function() {
        toastr.success("Update");
        $modalInstance.close();
      }).catch( function() {
        toastr.error('Failed to update');
      });
    };

    vm.close = function() {
      $modalInstance.dismiss('cancel');
    }


  });
})();

