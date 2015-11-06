(function() {
  'use strict';

  angular.module('yo').directive('asMap', map);

  /** @ngInject */
  function map($timeout) {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/map/map.html',
      link: linkFunc,
      controller:'MapController',
      controllerAs: 'vm'
    };

    return directive;

    function linkFunc(scope, el, attr, vm) {
      var planetWatcher;
      var commandWatcher;
      var fullpageReady = false;

      planetWatcher = scope.$watch('vm.rendered', function(){
        if(vm.rendered > 0 && vm.rendered === vm.totalPlanets){
          el.find('#map-contents').fullpage();
          fullpageReady = true;
          setControls(true);
        }
      });

      commandWatcher = scope.$watch('vm.controllable', function(){
        if(fullpageReady) {
          if (vm.controllable) {
            setControls(true);
          }
          else {
            setControls(false);
          }
        }
      });

      scope.$on('$destroy', function () {
        planetWatcher();
        commandWatcher();
      });

      var setControls = function(value){
        $.fn.fullpage.setAllowScrolling(value);
        $.fn.fullpage.setKeyboardScrolling(value);
      }
    }
  }

})();
