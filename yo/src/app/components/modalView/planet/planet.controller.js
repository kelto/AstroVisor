(function(){
  'use strict';

  angular.module('yo').controller('PlanetController', PlanetController);

  /** @ngInject */
  function PlanetController($log, $rootScope, $scope, $stateParams, $filter, $auth, systems, descriptions, toastr) {
    var vm = this;
    vm.descriptions = [];
    vm.nbDesc = 0;
    vm.currentDesc = 1;
    vm.planet = systems.getPlanetById($stateParams.id);
    vm.trades = vm.planet.trades;
    vm.nbTrades = 0;
    vm.descEditor = '';
    vm.nbTrades = vm.trades.length;
    vm.currentTr = 1;
    vm.currentTrDesc = null;

    vm.currentDescription = function() {
      return vm.descriptions ? vm.descriptions[vm.currentDesc - 1] : '';
    };

    vm.upvote = function(){
      if($auth.isAuthenticated()){
        var desc = vm.descriptions[vm.currentDesc - 1];
        descriptions.updateDescription(desc.id, desc.text, desc.upvotes+1, desc.downvotes, vm.planet.id).then(function(e){
          $log.debug(e);
          refresh();
          toastr.success('Like enregistré');
        }).catch(function(e){
          $log.error(e);
          toastr.error('Impossible d\'enregistrer le like');
        });
      }
      else{
        toastr.error('Vous n\'êtes pas connecté');
      }
    };

    vm.downvote = function(){
      if($auth.isAuthenticated()){
        var desc = vm.descriptions[vm.currentDesc - 1];
        descriptions.updateDescription(desc.id, desc.text, desc.upvotes, desc.downvotes+1, vm.planet.id).then(function(e){
          $log.debug(e);
          refresh();
          toastr.success('Dislike enregistré');
        }).catch(function(e){
          $log.error(e);
          toastr.error('Impossible d\'enregistrer le dislike');
        });
      }
      else{
        toastr.error('Vous n\'êtes pas connecté');
      }
    };

    vm.sendDescEditorContent = function(){
      if($auth.isAuthenticated()){
        descriptions.sendNewDescription(vm.descEditor, vm.planet.id).then(function(e){
          $log.debug(e);
          vm.clearDescEditorContent();
          refresh();
          toastr.success('Description enregistrée');
        }).catch(function(e){
          $log.error(e);
          toastr.error('Impossible d\'enregistrer la description');
        });
      }
      else{
        toastr.error('Vous n\'êtes pas connecté');
      }
    };

    vm.clearDescEditorContent = function(){
      vm.descEditor = '';
    };

    $scope.$on('modal.closing', function(){
      $rootScope.$broadcast('planet.closed');
    });

    vm.currentTrDescription = function() {
      descriptions.planet = vm.planet;
      descriptions.getDescByIdTrade(vm.currentTrade().id).then(function (data) {
        console.log(data.text);
        vm.currentTrDesc = data;
        return data;
      });
    }

    vm.currentTrade = function(){
      return vm.trades ? vm.trades[vm.currentTr - 1] : '';
    };

    activate();

    function activate(){
      try{
        vm.planet = systems.getPlanetById($stateParams.id);
        vm.descriptions = $filter('orderBy')(vm.planet.descriptions, '+id');
        vm.nbDesc = vm.descriptions.length;
        vm.trades = vm.planet.trades;
        vm.nbTrades = vm.trades.length;
     }
      catch(err){
        $log.error(err);
      }
    }

    function refresh(){
      systems.fetchSystems().then(function(){
        activate();
      });
    }
  }
})();

