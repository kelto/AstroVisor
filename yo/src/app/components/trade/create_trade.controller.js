angular.module('yo').controller('CreateTradeController', function ($scope, $modalInstance, $auth,toastr) {
  /* jshint validthis: true */
  var vm = this;
  vm.trade = {
    name: "",
    description: ""
  };

  vm.createTreade = function() {

  };

  vm.close = function() {
    $modalInstance.dismiss('cancel');
  };

});

