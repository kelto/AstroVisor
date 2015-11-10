(function(){
  'use strict';

  angular.module('yo').factory('trades', trades);

  /** @ngInject */
  function trades($http, $q) {
    var uri = 'api/trades';

    var service = {
      uri: uri,
      trades:null,
      fetchTrades:fetchTrades,
      getTrades:getTrades,
      getTradeById:getTradeById
    };

    return service;

    function fetchTrades(){
      return $q(function(resolve, reject){
        return $http.get(fetchTradesComplete).catch(fetchTradesFailed);

        function fetchTradesComplete(response){
          if(response.data.length > 0){
            var tmp = [];
            response.data.forEach(function(planet){
              if(planet.trades.length > 0 ){
                tmp.push(planet);
              }
            });

            if (tmp.length > 0){
              service.trades = tmp;
              resolve(tmp);
            }
            else{
              reject('Trades were empty.');
            }
          }
          else{
            reject('No trades found.');
          }
        }

        function fetchTradesFailed(error){
          reject('XHR failed for getTrades.\n' + angular.toJson(error.data, true));
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

    function getTradeById(id){
      if(service.trades == null || service.trades.length < 1){
        throw 'No trades available.';
      }

      var res = null;
      for(var i=0; i<service.trades.length;i++){
        var trade = system.trades[i];
        if(trade.id == id){
          res = trade;
          break;
        }
      }

      if(res == null){
        throw 'Trade not found.';
      }

      return res;
    }
  }

})();
