angular.module('yo').controller('CreateTradeController', function ($scope, $modalInstance, $http, $stateParams, toastr) {
  /* jshint validthis: true */
  var vm = this;
  vm.trade = {
    name: "",
    description: ""
  };

  vm.createTreade = function() {
    $http.post('/api/trades/'+$stateParams.id, vm.trade).then(function() {
      toastr.success('Trade "' + vm.trade.name +'" successfuly created!')
      $modalInstance.close();
    })
      .catch(function() {
        toastr.error('This username is probably used already.','Failed to signup');
      });
  };

  vm.close = function() {
    $modalInstance.dismiss('cancel');
  };

});

