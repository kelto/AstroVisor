(function() {
  'use strict';

  angular.module('yo').factory('planetService', planetService);

  /** @ngInject */
  function planetService($http, $q) {
    function service(_uri_){
      var uri = _uri_;
      var planets = null;

      service.prototype.fetchPlanets = function(){
        return $q(function(resolve, reject){
          return $http.get(uri).then(fetchPlanetsComplete).catch(fetchPlanetsFailed);

          function fetchPlanetsComplete(response){
            if(response.data.length > 0){
              planets = response.data;
              resolve(response.data);
            }
            else{
              reject('No planets found.');
            }
          }

          function fetchPlanetsFailed(error){
            reject('XHR Failed for getPlanets.\n' + angular.toJson(error.data, true));
          }
        });
      };

      service.prototype.getPlanets = function(){
        if(planets != null){
          return $q(function(resolve){
            resolve(planets);
          });
        }
        else{
          return this.fetchPlanets();
        }
      };

      service.prototype.getUri = function(){
        return uri;
      };
    }

    return new service('/api/planets');
  }
})();
