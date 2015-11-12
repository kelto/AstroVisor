(function() {
  'use strict';

  angular.module('yo').service('userService', userService);

  /** @ngInject */
  function userService($http) {
      this.update = function(user, password) {
        var data = {user: user, password: password};
        return $http.put("/api/users",data);
      };

    this.info = function() {
      return $http.get("/api/me");
    }
  }
})();
