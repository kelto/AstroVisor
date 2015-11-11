(function(){
  'use strict';

  angular.module('yo').factory('trades', trades);

  /** @ngInject */
  function trades($http, $q) {
    var uri = 'api/trades';
    var service = {
      uri: uri,
      planet:null,
      trades:[],
      fetchTrades:fetchTrades,
      getTrades:getTrades
    };

    return service;

    function fetchTrades(){
      return $q(function(resolve, reject){
        return $http.get(uri).then(fetchTradesComplete).catch(fetchTradesFailed);

        function fetchTradesComplete(response){
          if(response.data.length > 0){
            var tmp = [];
            response.data.forEach(function(trade){
              if(trade.planet.id == service.planet.id){
                tmp.push(trade);
              }
            });

            if (tmp.length > 0){
              service.trades = tmp;
              resolve(tmp);
            }
            else{
              reject('Planets were empty.');
            }
          }
          else{
            reject('No trades found.');
          }
        }

        function fetchTradesFailed(error){
          reject('XHR failed for getPlanets.\n' + angular.toJson(error.data, true));
        }
      });
    }

    function getTrades(){
      if(service.trades != null){
        return $q(function(resolve){
          resolve(service.trades);
        });
      }
      else{
        return this.fetchTrades();
      }
    }

  }

})();
