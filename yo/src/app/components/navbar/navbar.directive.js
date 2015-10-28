(function() {
  'use strict';

  angular
    .module('yo')
    .directive('navbar', navbar);

  /** @ngInject */
  function navbar() {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/navbar/navbar.html',
      controller: 'NavbarController',
      controllerAs: 'navbar'
    };

    return directive;
  }

})();
