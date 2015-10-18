(function() {
  'use strict';

  angular
      .module('yo')
      .directive('map', map);

  /** @ngInject */
  function map($log, $timeout, planetService, sphereService) {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/map/map.html',
      link: linkFunc,
      controller: MapController,
      controllerAs: 'vm',
      bindToController: true
    };

    return directive;

    function linkFunc(scope, el, attr, vm) {
      var watcher;

      watcher = scope.$watch('vm.planets', function(){
        for(var i=0; i<vm.planets.length; i++){
          var system = vm.planets[i];
          var stellarSystem = angular.element(' <div class="section stellar-system"></div>');
          stellarSystem.attr('id', system.name);
          el.find('#map-contents').append(stellarSystem);

          for(var j=0; j<system.planets.length; j++){
            var planet = system.planets[j];
            var planetWrapper = angular.element('<div class="slide" data-anchor="'+planet.name+'"></div>');
            planetWrapper.append(renderPlanet(planet));
            stellarSystem.append(planetWrapper);
          }
        }

        el.find('#map-contents').fullpage();
      });

      scope.$on('$destroy', function () {
        watcher();
      });
    }

    function renderPlanet(planet){
      var sphere = sphereService.renderPlanet(planet);
      angular.element(sphere).attr('class', 'planet');
      angular.element(sphere).attr('id', planet.code_name);
      angular.element(sphere).click(function(){
        alert(planet.name);
      });

      return sphere;
    }

    /** @ngInject */
    function MapController() {
      var vm = this;
      vm.planets = [];

      activate();

      function activate(){
        return getPlanets().then(function(){
        });
      }

      function getPlanets() {
        return planetService.getPlanets().then(function(data){
          vm.planets = data;
          return vm.planets;
        });
      }
    }
  }

})();
