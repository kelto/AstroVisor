(function () {

  angular.module('yo').controller('NavbarController', function ($scope, $modal, $auth, $state) {
    /* jshint validthis: true */
    var vm = this;

    vm.login = function () {
      $modal.open({
        animation: true,
        templateUrl: 'app/components/modalView/login/login_modal.html',
        /*resolve: {
          newPath: function(){
            return 'home.login'
          },
          oldPath: function(){
            return 'home'
          }
        },*/
        controller: 'LoginController as login'
      }).result.then(function() {
        }, function(){
          $state.go('home')
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

  });
})();
