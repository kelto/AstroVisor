(function(){
  'use strict';

  angular.module('AstroVisor').controller('descEditorController', descEditorController);

  //descEditorController.$inject = ['textAngular', 'angular-sanitize'];
  /** @ngInject */
  function descEditorController() {
    var vm = this;
    vm.greeting = 'TROLL';
  }

  //angular.module('AstroVisor').controller('descEditorController', ['$scope', function($scope) {
  //  $scope.greeting = 'TROLL';
  //}]);
})();
