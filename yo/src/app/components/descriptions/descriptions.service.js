(function(){
  'use strict';

  angular.module('yo').factory('descriptions', descriptions);

  /** @ngInject */
  function descriptions($http, $q) {
    var uri = '/api/descriptions';

    var service = {
      uri: uri,
      descTr:null,
      getDescByIdTrade:getDescByIdTrade,
      sendNewDescription:sendNewDescription,
      updateDescription:updateDescription
    };

    return service;

    function getDescByIdTrade(id) {

      return $q(function (resolve, reject) {
        $http.get(uri).then(getDescComplete).catch(getDescFailed);

        function getDescComplete(response) {
          if (response.data.length > 0) {
            var tmp = null;
            response.data.forEach(function (desc) {
              if (desc.trade != null && desc.trade.id == id) {
                tmp = desc
              }
            });
            if (tmp != null) {
              service.descTr = tmp;
              resolve(tmp);
            }
            else {
              reject('Trade is empty.');
            }
          }
          else {
            reject('No desc found.');
          }
        }

        function getDescFailed(error) {
          reject('XHR failed for getDesc.\n' + angular.toJson(error.data, true));
        }
      });
    }

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

    function updateDescription(descId, text, upvotes, downvotes, planetId){
      return $q(function(resolve, reject){
        var data = {text:text, planet:{id:planetId}, upvotes:upvotes, downvotes:downvotes};
        return $http.put(uri+'/'+descId, data).then(updateDescriptionComplete).catch(updateDescriptionFailed);

        function updateDescriptionComplete(e){
          resolve(e);
        }

        function updateDescriptionFailed(e){
          reject(e);
        }
      });
    }
  }
})();
