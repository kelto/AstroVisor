(function() {
    'use strict';

    angular.module('yo').factory('planetService', planetService);

    /** @ngInject */
    function planetService($log, $http) {
        var uri = '/api/planets';
        var service = {
            resourceName: uri,
            getPlanets: getPlanets
        };

        return service;

        function getPlanets(){
            return $http.get(uri).then(getPlanetsComplete).catch(getPlanetsFailed);

            function getPlanetsComplete(response){
                return response.data;
            }

            function getPlanetsFailed(error){
                $log.error('XHR Failed for getPlanets.\n' + angular.toJson(error.data, true));
            }
        }
    }
})();
