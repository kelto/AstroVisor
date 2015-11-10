angular.module('yo').controller('CreateTradeController', function ($scope, $modalInstance, $http, $stateParams, toastr, trades) {
  /* jshint validthis: true */
  var vm = this;
  vm.trade = {
    name: "",
    planet: {
      id: $stateParams.id
    }
  };
  vm.description = {
    text: "",
    trade: {
      id: ""
    }
  }

  vm.createTrade = function () {
    $http.post('/api/trades/' + $stateParams.id, vm.trade).then(function () {
      //console.log("here : " + vm.trade.id);
    })
      .catch(function () {
        toastr.error('Something gone wrong.', 'Failed to create a trade!');
      });

    vm.description.trade.id = trades.getTrades().length;
    $http.post('/api/descriptions', vm.description).then(function () {
      toastr.success('Trade "' + vm.trade.name + '" successfuly created!');
      $modalInstance.close();
    })
      .catch(function () {
        toastr.error('Something gone wrong.', 'Failed to create a description!');
      });
  };

  vm.close = function() {
    $modalInstance.dismiss('cancel');
  };

});

