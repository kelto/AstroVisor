(function(){
  'use strict';

  angular.module('yo').factory('descriptions', descriptions);

  /** @ngInject */
  function descriptions($http, $q) {
    var uri = '/api/descriptions';

    var service = {
      uri: uri,
      sendNewDescription:sendNewDescription
    };

    return service;

    function sendNewDescription(text, planetId){
      return $q(function(resolve, reject){
        var data = {text:text, planet:{id:planetId}};
        return $http.post(uri, data).then(sendNewDescriptionComplete).catch(sendNewDescriptionFailed);

        function sendNewDescriptionComplete(e){
          resolve(e);
        }

        function sendNewDescriptionFailed(e){
          reject(e);
        }
      });
    }
  }
})();
