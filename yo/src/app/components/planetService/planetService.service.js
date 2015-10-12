(function() {
  'use strict';

  angular.module('yo').factory('planetService', planetService);

  /** @ngInject */
  function planetService($http) {
    var uri = '/api/planets';

    function service(){
      //var self = this;
      var planets = null;

      service.prototype.fetchPlanets = function(){
        return new Promise(function(resolve, reject){
          return $http.get(uri).then(getPlanetsComplete).catch(getPlanetsFailed);

          function getPlanetsComplete(response){
            planets = response.data;
            resolve(response.data);
          }

          function getPlanetsFailed(error){
            reject('XHR Failed for getPlanets.\n' + angular.toJson(error.data, true));
          }
        });
      };

      service.prototype.getPlanets = function(){
        if(planets != null){
          return new Promise(function(resolve, reject){
            resolve(planets);
          });
        }
        else{
          return this.fetchPlanets();
        }
      };
    }

    return new service();
  }
})();
