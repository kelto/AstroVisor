(function(){
  'use strict';

  angular.module('yo').controller('PlanetController', PlanetController);

  /** @ngInject */
  function PlanetController($log, $rootScope, $scope, $stateParams, systems, descriptions, toastr) {
    var vm = this;
    vm.planet;
    vm.descriptions = [];
    vm.nbDesc = 0;
    vm.currentDesc = 1;
    vm.trades = [];
    vm.nbTrades = 0;
    vm.descEditor = '';

    vm.currentDescription = function() {
      return vm.descriptions ? vm.descriptions[vm.currentDesc - 1] : '';
    };

    vm.upvote = function(){
      alert('upvoted');
    };

    vm.downvote = function(){
      alert('downvote');
    };

    vm.sendDescEditorContent = function(){
      descriptions.sendNewDescription(vm.descEditor, vm.planet.id).then(function(e){
        $log.debug(e);
        vm.clearDescEditorContent();
        toastr.success('Description enregistrée');
      }).catch(function(e){
        $log.error(e);
        toastr.error('Impossible d\'enregistrer la description');
      });
    };

    vm.clearDescEditorContent = function(){
      vm.descEditor = '';
    };

    $scope.$on('modal.closing', function(){
      $rootScope.$broadcast('planet.closed');
    });

    activate();

    function activate(){
      try{
        vm.planet = systems.getPlanetById($stateParams.id);
        vm.descriptions = vm.planet.descriptions;
        vm.nbDesc = vm.descriptions.length;
        vm.trades = vm.planet.trades;
        vm.nbTrades = vm.trades.length;
      }
      catch(err){
        $log.error(err);
      }
    }
  }
})();

