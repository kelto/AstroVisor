angular.module('yo').controller('CreateTradeController', function ($scope, $modalInstance, $http, $stateParams, toastr, systems, planets) {
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
    $http.post('/api/trades', vm.trade).then(function () {
      console.log("trade on planet id=" + vm.trade.planet.id + " and name=" + vm.trade.name + " created !");
    })
      .catch(function () {
        toastr.error('Something gone wrong.', 'Failed to create a trade!');
      });

    planets.planet = systems.getPlanetById($stateParams.id);
    planets.fetchTrades();
    console.log(planets.trades);
    var tr = planets.getTradeByName(vm.trade.name);
    vm.description.trade.id = tr.id;
    console.log("id of the last trade = " + tr.id);
    $http.post('/api/descriptions', vm.description).then(function () {
        console.log("desc text=" + vm.description.text+ " on trade id=" + vm.description.trade.id + " created !");
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

