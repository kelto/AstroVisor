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

        var predicate = function(value, index, array){
          var res = false;
          console.log(code);
          console.log(value);
          console.log(index);
          console.log(array);
          value.planets.forEach(function(planet){
            if(planet.code_name == code){
              console.log('OKAY');
              //console.log(planet.code_name);
              //console.log(code);
              res = true;
            }
          });
          return res;
        };

        var res = $filter('filter')(planets, predicate, true);
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
