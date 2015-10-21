(function() {
  'use strict';

  angular
    .module('yo')
    .config(routerConfig);

  /** @ngInject */
  function routerConfig($stateProvider, $urlRouterProvider) {
    $stateProvider
      .state('home', {
        url: '/',
        templateUrl: 'app/main/main.html',
        controller: 'MainController',
        controllerAs: 'main'
      });

    $stateProvider
      .state('home.login', {
        url: ':path/'
        //templateUrl: 'app/components/modalView/login/login_modal.html',
        //controller: 'LoginController as login'
      })
      .state('home.signup', {
        url: ':path/'
      });

    $urlRouterProvider.otherwise('/');
  }

})();
