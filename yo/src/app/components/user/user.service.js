(function() {
  'use strict';

  angular.module('yo').service('userService', userService);

  /** @ngInject */
  function userService($http, $auth) {
      this.update = function(user, password) {
        var data = {user: user, password: password};
        return $http.put("/api/users",data);
      };

    this.getUsername = function() {
      return $auth.getPayload().sub;
    }

    this.info = function() {
      return $http.get("/api/me");
    }
  }
})();
