angular.module('yo').controller('SignupController', function ($scope, $modal, $log, $auth) {
  /* jshint validthis: true */
  var vm = this;

  vm.login = function() {
    $auth.signup({ username: 'test', password: 'test'});
  };

  vm.close = function() {
    $log.log('youla');
  };

});

