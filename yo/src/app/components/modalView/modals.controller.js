angular.module('yo').controller('ModalsController', function ($scope, $modal, $log) {
  /* jshint validthis: true */
  var vm = this;

  vm.login = function() {
     $modal.open({
       animation: true,
       templateUrl: 'app/components/modalView/login/login_modal.html',
       controller: 'LoginController as login'
     });
  };

  vm.signup = function() {
    $modal.open({
      animation: true,
      templateUrl: 'app/components/modalView/signup/signup_modal.html',
      controller: 'SignupController as signup'
    });
  };

});
