(function() {
  'use strict';

  angular.module('yo').factory('planets', planets);

  /** @ngInject */
  function planets($http, $q) {
    var uri = '/api/planets';

    var service = {
      uri:uri,
      planets:null,
      fetchPlanets:fetchPlanets,
      getPlanets:getPlanets,
      getPlanetByCodeName:getPlanetByCodeName
    };

    return service;

    function fetchPlanets(){
      return $q(function(resolve, reject){
        return $http.get(uri).then(fetchPlanetsComplete).catch(fetchPlanetsFailed);

        function fetchPlanetsComplete(response){
          if(response.data.length > 0){
            service.planets = response.data;
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
    }

    function getPlanets(){
      if(service.planets != null){
        return $q(function(resolve){
          resolve(service.planets);
        });
      }
      else{
        return this.fetchPlanets();
      }
    }

    function getPlanetByCodeName(code){
      if(service.planets == null || service.planets.length < 1){
        throw 'No planets available.';
      }

      var res = null;
      for(var i=0; i<service.planets.length; i++){
        var system = service.planets[i];
        for(var j=0; j<system.planets.length; j++){
          var planet = system.planets[j];
          if(planet.code_name === code){
            res = planet;
            break;
          }
        }

        if(res != null){
          break;
        }
      }

      if(res === null){
        throw 'Planet not found.';
      }

      return res;
    }
  }
})();
