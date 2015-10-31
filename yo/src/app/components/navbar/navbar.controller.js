(function () {

  angular.module('yo').controller('NavbarController', function ($scope, $modal, $auth) {
    /* jshint validthis: true */
    var vm = this;

    vm.login = function () {
      $modal.open({
        animation: true,
        templateUrl: 'app/components/modalView/login/login_modal.html',
        controller: 'LoginController as login'
      });
    };

    vm.signup = function () {
      $modal.open({
        animation: true,
        templateUrl: 'app/components/modalView/signup/signup_modal.html',
        controller: 'SignupController as signup'
      });
    };

    vm.isAuthenticated = function () {
      return $auth.isAuthenticated();
    };

    vm.logout = function () {
      $auth.logout();
    };

    vm.user = function () {
    };

    vm.username = function () {
      if(vm.isAuthenticated()) {
        return $auth.getPayload().sub;
      }
    };

  });
})();
