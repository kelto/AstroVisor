(function() {
  'use strict';

  angular
    .module('yo')
    .config(config);

  /** @ngInject */
  function config($logProvider, toastrConfig, $authProvider, $stateProvider) {
    // Enable log
    $logProvider.debugEnabled(true);

    // Set options third-party lib
    toastrConfig.allowHtml = true;
    toastrConfig.timeOut = 3000;
    toastrConfig.positionClass = 'toast-top-right';
    toastrConfig.preventDuplicates = true;
    toastrConfig.progressBar = true;

    //TODO: make it so that it use different uri on dev or prod
    var baseUrl = 'http://localhost:3000/api';
    $authProvider.loginUrl = baseUrl + '/login';
    $authProvider.signupUrl = baseUrl + '/users';
    $authProvider.tokenName = 'access_token';

    $stateProvider
      /*.state('home', {
      url: '/',
      templateUrl: '/main/main.html',
      controller: 'MainController as main'
    })*/
      .state('home.login', {
        url: ':path/'
        //templateUrl: 'app/components/modalView/login/login_modal.html',
        //controller: 'LoginController as login'
      })
  }

})();
