(function () {
  angular.module('yo').controller('UserController', function ($scope, toastr,userService,$modalInstance) {
    /* jshint validthis: true */
    var vm = this;
    vm.user = {password: ''};
    vm.password = '';

    userService.info().then(function(response){
      vm.user = angular.extend(response.data,vm.user);
    });

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

