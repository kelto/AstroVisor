(function(){
  'use strict';

  angular.module('yo').controller('MapController', MapController);

  /** @ngInject */
  function MapController(systems, $state) {
    var vm = this;
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
