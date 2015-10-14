(function() {
  //'use strict';

  angular
      .module('yo')
      .directive('map', map);

  /** @ngInject */
  function map($log, planetService, sphereService) {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/map/map.html',
      scope: {
        creationDate: '='
      },
      link: linkFunc,
      controller: MapController,
      controllerAs: 'vm',
      bindToController: true
    };

    return directive;

    function linkFunc(scope, el, attr, vm) {
      var watcher;
      $log.debug(el);

      watcher = scope.$watch('vm.planets', function() {
        angular.forEach(vm.planets, function(planet) {
          var sphere = sphereService.renderPlanet(planet);
          angular.element(sphere).attr('id', planet.code_name);
          angular.element(sphere).click(function(){
            alert(planet.name);
          });
          el.find('#map-contents').append(sphere);
        });
      });

      scope.$on('$destroy', function () {
        watcher();
      });
    }

    /** @ngInject */
    function MapController() {
      var vm = this;
      vm.planets = [];

      activate();

      function activate(){
        return getPlanets().then(function(){
          $log.info('Planets fetched.');
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
