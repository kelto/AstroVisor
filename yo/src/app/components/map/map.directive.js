(function() {
  'use strict';

  angular
      .module('yo')
      .directive('map', map);

  /** @ngInject */
  function map($log, planetService) {
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
          $log.debug(planet);
          el.find('ul').append('<li>'+planet.name+'</li>');
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
