(function() {
  'use strict';

  angular
    .module('yo')
    .config(config);

  /** @ngInject */
  function config($logProvider, toastrConfig, $authProvider) {
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
    $authProvider.oauth1({
      name: 'astrovisor',
      url: 'http://localhost:3000/api/login',
      type: null,
      popupOptions: null
    });
  }

})();
