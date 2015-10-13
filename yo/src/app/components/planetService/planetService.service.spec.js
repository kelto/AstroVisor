(function() {
  'use strict';

  describe('test a planetService', function() {
    var planetService;
    var $httpBackend;
    var $log;

    beforeEach(module('yo'));
    beforeEach(inject(function(_planetService_, _$httpBackend_, _$log_) {
      planetService = _planetService_;
      $httpBackend = _$httpBackend_;
      $log = _$log_;
    }));

    it('should be registered', function() {
      expect(planetService).not.toEqual(null);
    });

    describe('fetchPlanets function', function() {
      it('should exist', function () {
        expect(planetService.fetchPlanets).not.toEqual(null);
      });

      it('should return data', function() {
        $httpBackend.when('GET',  planetService.getUri()).respond(200, [{pprt: 'value'}]);
        var data;
        planetService.fetchPlanets().then(function(fetchedData) {
          data = fetchedData;
        });

        $httpBackend.flush();
        expect(data).toEqual(jasmine.any(Array));
        expect(data.length === 1).toBeTruthy();
        expect(data[0]).toEqual(jasmine.any(Object));
      });

      it('should return an error', function() {
        $httpBackend.when('GET',  planetService.getUri()).respond(500);
        planetService.fetchPlanets().catch(function(error){
          expect(error).toEqual(jasmine.stringMatching('XHR Failed for'));
        });
        $httpBackend.flush();
      });

      it('should return an error', function() {
        $httpBackend.when('GET',  planetService.getUri()).respond(200, []);
        planetService.fetchPlanets().catch(function(error){
          expect(error).toEqual(jasmine.stringMatching('No planets found.'));
        });
        $httpBackend.flush();
      });
    });

    describe('getPlanets function', function() {
      it('should exist', function() {
        expect(planetService.getPlanets).not.toEqual(null);
      });

      it('should return data', function() {
        $httpBackend.when('GET',  planetService.getUri()).respond(200, [{pprt: 'value'}]);
        var data1, data2;
        planetService.getPlanets().then(function(fetchedData1) {
          data1 = fetchedData1;
          planetService.getPlanets().then(function(fetchedData2) {
            data2 = fetchedData2;
            expect(data2).toEqual(data1);
          });
        });

        $httpBackend.flush();
        expect(data1).toEqual(jasmine.any(Array));
        expect(data1.length === 1).toBeTruthy();
        expect(data1[0]).toEqual(jasmine.any(Object));

        expect(data2).toEqual(jasmine.any(Array));
        expect(data2.length === 1).toBeTruthy();
        expect(data2[0]).toEqual(jasmine.any(Object));
      });
    });
  });
})();
