(function() {
  'use strict';

  angular.module('yo').factory('systems', systems);

  /** @ngInject */
  function systems($http, $q) {
    var uri = '/api/systems';

    var service = {
      uri:uri,
      systems:null,
      fetchSystems:fetchSystems,
      getSystems:getSystems,
      getPlanetByCodeName:getPlanetByCodeName
    };

    return service;

    function fetchSystems(){
      return $q(function(resolve, reject){
        return $http.get(uri).then(fetchSystemsComplete).catch(fetchSystemsFailed);

        function fetchSystemsComplete(response){
          if(response.data.length > 0){
            var tmp = [];
            response.data.forEach(function(system){
              if(system.planets.length > 0){
                tmp.push(system);
              }
            });

            if(tmp.length > 0){
              service.systems = tmp;
              resolve(tmp);
            }
            else{
              reject('No planets found.');
            }
          }
          else{
            reject('No systems found.');
          }
        }

        function fetchSystemsFailed(error){
          reject('XHR Failed for getSystems.\n' + angular.toJson(error.data, true));
        }
      });
    }

    function getSystems(){
      if(service.systems != null){
        return $q(function(resolve){
          resolve(service.systems);
        });
      }
      else{
        return this.fetchSystems();
      }
    }

    function getPlanetByCodeName(code){
      if(service.systems == null || service.systems.length < 1){
        throw 'No systems available.';
      }

      var res = null;
      for(var i=0; i<service.systems.length; i++){
        var system = service.systems[i];
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
