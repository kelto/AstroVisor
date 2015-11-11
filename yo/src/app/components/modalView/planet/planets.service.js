(function(){
  'use strict';

  angular.module('yo').factory('planets', planets);

  /** @ngInject */
  function planets($http, $q) {
    var uri = 'api/trades';
    var service = {
      uri: uri,
      planet:null,
      trades:null,
      fetchTrades:fetchTrades,
      getTrades:getTrades,
      getTradeByName:getTradeByName
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

    function getTradeByName(name){
      if(service.trades.length < 1){
        throw 'No trades available.';
      }

      var res = null;
      if (service.trades != null){
        for(var i=0; i<service.trades.length;i++){
          var trade = service.trades[i];
          if(trade.name == name){
            res = trade;
            break;
          }
        }

        if(res == null){
          throw 'Trade not found.';
        }
      }
      else {
        throw 'No trade.';
      }
      return res;
    }
  }

})();
