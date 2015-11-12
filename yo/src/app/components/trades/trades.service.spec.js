(function() {
  'use strict';

  describe('test a trades service', function() {
    var trades;
    var planet;
    var $httpBackend;

    beforeEach(module('yo'));
    beforeEach(inject(function(_trades_, _planetService_, _$httpBackend_) {
      trades = _trades_;
      planet = _planetService_;
      trades.planet = planet.getPlanet(1);
      $httpBackend = _$httpBackend_;
    }));

    it('should be registered', function() {
      expect(trades).not.toEqual(null);
    });

    describe('fetchTrades function', function() {
      it('should exist', function () {
        expect(trades.fetchTrades).not.toEqual(null);
      });

      it('should return data', function() {
        $httpBackend.when('GET', trades.uri).respond(200, [
          {
            "class": "com.astrovisor.Trade",
            "id": 1,
            "descriptions": [],
            "name": "tradeBoot",
            "planet": {
              "class": "com.astrovisor.Planet",
              "id": 1
            }
          },
          {
            "class": "com.astrovisor.Trade",
            "id": 2,
            "descriptions": [],
            "name": "TradeBootaaaa",
            "planet": {
              "class": "com.astrovisor.Planet",
              "id": 1
            }
          }
        ]);
        var data;
        trades.fetchTrades().then(function(fetchedData) {
          data = fetchedData;
        });

        $httpBackend.flush();
        expect(data).toEqual(jasmine.any(Array));
        expect(data.length === 2).toBeTruthy();
        expect(data[0]).toEqual(jasmine.any(Object));
      });

      it('should return an error', function() {
        $httpBackend.when('GET',  trades.uri).respond(500);
        trades.fetchTrades().catch(function(error){
          expect(error).toEqual(jasmine.stringMatching('XHR Failed for'));
        });
        $httpBackend.flush();
      });

      it('should return an error', function() {
        $httpBackend.when('GET',  trades.uri).respond(200, []);
        trades.fetchTrades().catch(function(error){
          expect(error).toEqual(jasmine.stringMatching('No trades found.'));
        });
        $httpBackend.flush();
      });

      it('should return an error', function() {
        $httpBackend.when('GET',  trades.uri).respond(200, [{name:'Sol', planets:[]}]);
        trades.fetchTrades().catch(function(error){
          expect(error).toEqual(jasmine.stringMatching('planets were empty.'));
        });
        $httpBackend.flush();
      });
    });

    describe('getTrades function', function() {
      it('should exist', function() {
        expect(trades.getTrades).not.toEqual(null);
      });

      it('should return data', function() {
        $httpBackend.when('GET',  trades.uri).respond(200, [
          {
            "class": "com.astrovisor.Trade",
            "id": 1,
            "descriptions": [],
            "name": "tradeBoot",
            "planet": {
              "class": "com.astrovisor.Planet",
              "id": 1
            }
          },
          {
            "class": "com.astrovisor.Trade",
            "id": 2,
            "descriptions": [],
            "name": "TradeBootaaaa",
            "planet": {
              "class": "com.astrovisor.Planet",
              "id": 1
            }
          }
        ]);
        var data1, data2;
        trades.getTrades().then(function(fetchedData1) {
          data1 = fetchedData1;
          trades.getTrades().then(function(fetchedData2) {
            data2 = fetchedData2;
            expect(data2).toEqual(data1);
          });
        });

        $httpBackend.flush();
        expect(data1).toEqual(jasmine.any(Array));
        expect(data1.length === 2).toBeTruthy();
        expect(data1[0]).toEqual(jasmine.any(Object));

        expect(data2).toEqual(jasmine.any(Array));
        expect(data2.length === 2).toBeTruthy();
        expect(data2[0]).toEqual(jasmine.any(Object));
      });
    });

  });
})();
