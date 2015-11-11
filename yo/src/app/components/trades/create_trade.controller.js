angular.module('yo').controller('CreateTradeController', function ($scope, $modalInstance, $http, $stateParams, toastr, systems, trades) {
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
    /* We create a trade at first */
    $http.post('/api/trades', vm.trade).success(function(data){
      trades.planet = systems.getPlanetById($stateParams.id);

      vm.description.trade.id = data.id;

      $http.post('/api/descriptions', vm.description).then(function () {
        toastr.success('Trade "' + vm.trade.name + '" successfuly created!');
        $modalInstance.close();
      })
        .catch(function () {
          toastr.error('Something gone wrong.', 'Failed to create a description!');
        });
    })
      .catch(function () {
        toastr.error('Something gone wrong.', 'Failed to create a trade!');
      });
  };

  vm.close = function() {
    $modalInstance.dismiss('cancel');
  };

});

