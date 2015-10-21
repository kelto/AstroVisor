(function() {
  'use strict';

  angular.module('yo').directive('asMap', map);

  /** @ngInject */
  function map() {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/map/map.html',
      link: linkFunc,
      controller:'MapController',
      controllerAs: 'vm'
    };

    return directive;

    function linkFunc(scope, el, attr, vm) {
      var watcher;

      watcher = scope.$watch('vm.rendered', function(){
        if(vm.rendered > 0 && vm.rendered === vm.totalPlanets){
          el.find('#map-contents').fullpage();
        }
      });

      scope.$on('$destroy', function () {
        watcher();
      });
    }
  }

})();
