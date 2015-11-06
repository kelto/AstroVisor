(function() {
  'use strict';
  angular.module('yo').controller('PlanetController', function ($scope, $stateParams, planetService) {
    /* jshint validthis: true */
    var vm = this;
    vm.currentDesc = 1;

    planetService.getPlanet($stateParams.id).then(function(response) {
      vm.planet = response.data;
    });

    planetService.getDescriptionOfPlanet($stateParams.id).then(function(response) {
      vm.descriptions = response.data;
      vm.nbDesc = vm.descriptions.length;
    });

    planetService.getTradesOfPlanet($stateParams.id).then(function(response) {
      vm.trades = response.data;
    });

    vm.currentDescription = function () {
      return vm.descriptions ? vm.descriptions[vm.currentDesc - 1] : '';
    };

  })
})();

