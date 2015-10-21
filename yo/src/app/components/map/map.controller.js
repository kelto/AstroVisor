(function(){
  'use strict';

  angular.module('yo').controller('MapController', MapController);

  MapController.$inject = ['planets'];
  function MapController(planets) {
    var vm = this;
    vm.planets = [];
    vm.totalPlanets = 0;
    vm.rendered = 0;

    vm.newRenderedPlanet = function(){
      vm.rendered++;
    };

    activate();

    function activate(){
      return getPlanets();
    }

    function getPlanets() {
      return planets.getPlanets().then(function(data){
        vm.planets = data;
        for(var i=0; i<vm.planets.length; i++){
          vm.totalPlanets += vm.planets[i].planets.length;
        }
        return vm.planets;
      });
    }
  }
})();
