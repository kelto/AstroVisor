(function() {
  'use strict';

  angular
    .module('yo')
    .config(routerConfig);

  /** @ngInject */
  function routerConfig($stateProvider, $urlRouterProvider, modalStateProvider) {

    $stateProvider
      .state('home', {
        url: '/',
        templateUrl: 'app/main/main.html',
        controller: 'MainController',
        controllerAs: 'main'
      });

    modalStateProvider
      .state('home.login', {
        url: 'login',
        animation: true,
        templateUrl: 'app/components/modalView/login/login_modal.html',
        controller: 'LoginController as login'

      })
      .state('home.signup', {
        url: 'signup',
        animation: true,
        templateUrl: 'app/components/modalView/signup/signup_modal.html',
        controller: 'SignupController as signup'
      })
      .state('home.planet', {
        url: 'planet/{id:int}',
        animation: true,
        templateUrl: 'app/components/modalView/planet/planet_modal.html',
        controller: 'PlanetController as vm',
        bindToController:true
      })
      .state('home.me', {
        url: 'me',
        animation: true,
        templateUrl: 'app/components/user/user_modal.html',
        controller: 'UserController as user'
      });


    $urlRouterProvider.otherwise('/');
  }

})();
