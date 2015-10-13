(function() {
  'use strict';

  angular.module('yo').factory('planetService', planetService);

  /** @ngInject */
  function planetService($http, $q, $filter) {
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

      service.prototype.getPlanetByCodeName = function(code){
        if(planets == null || planets.length < 1){
          throw 'No planets available.';
        }

        var res = $filter('filter')(planets, {'code_name':code}, true);
        if(res.length === 0){
          throw 'Planet not found.';
        }

        return res[0];
      };

      service.prototype.getUri = function(){
        return uri;
      };
    }

    return new service('/api/planets');
  }
})();
