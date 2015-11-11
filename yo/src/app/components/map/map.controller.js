(function(){
  'use strict';

  angular.module('yo').controller('MapController', MapController);

  /** @ngInject */
  function MapController(systems, $state, $scope, $rootScope) {
    var vm = this;
    var watcher;

    vm.systems = [];
    vm.totalPlanets = 0;
    vm.rendered = 0;
    vm.controllable = true;
    vm.$state = $state;
    vm.newRenderedPlanet = function(){
      vm.rendered++;
    };

    vm.openPlanet = function(id) {
      vm.controllable = false;
      $state.go('.planet', {id: id});
    };

    watcher = $rootScope.$on('planet.closed', function(){
      vm.controllable = true;
    });

    $scope.$on('$destroy', function () {
      watcher();
    });

    activate();

    function activate(){
      return getSystems();
    }

    function getSystems() {
      return systems.getSystems().then(function(data){
        vm.systems = data;
        for(var i=0; i<vm.systems.length; i++){
          vm.totalPlanets += vm.systems[i].planets.length;
        }
        return vm.systems;
      });
    }
  }
})();
