(function () {

  angular.module('yo').controller('NavbarController', function ($scope, $modal, $auth, $state) {
    /* jshint validthis: true */
    var vm = this;

    vm.login = function () {
      $state.go('home.login');
      $modal.open({
        animation: true,
        templateUrl: 'app/components/modalView/login/login_modal.html',
        controller: 'LoginController as login'
      }).result.then(function() {
          $state.go('home.logged');
        }, function(){
          $state.go('home');
        });
    };

    vm.signup = function () {
      $state.go('home.signup');
      $modal.open({
        animation: true,
        templateUrl: 'app/components/modalView/signup/signup_modal.html',
        controller: 'SignupController as signup'
      }).result.then(function() {
          $state.go('home');
        }, function(){
          $state.go('home');
        });
    };

    vm.planets = function() {
      $state.go('home.planets');
      $modal.open({
        animation : true,
        templateUrl: 'app/components/modalView/planet/planet_modal.html',
        controller: 'PlanetController as planet'
      }).result.then(function() {
          //DO Something ...
        }, function(){
          $state.go('home');
        });
    };

    vm.isAuthenticated = function () {
      return $auth.isAuthenticated();
    };

    vm.logout = function () {
      $state.go('home');
      $auth.logout();
    };

    vm.user = function () {
    };

  });
})();
