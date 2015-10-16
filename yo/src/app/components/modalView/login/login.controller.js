angular.module('yo').controller('LoginController', function ($scope, $modal, $log, $auth) {
  /* jshint validthis: true */
  var vm = this;

  vm.login = function() {
    $auth.login({ username: 'test', password: 'test'});
  };

  vm.close = function() {

  };

});

