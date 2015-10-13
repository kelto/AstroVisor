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

    describe('get a planet by code name', function(){
      it('should exist', function(){
        expect(planetService.getPlanetByCodeName).not.toEqual(null);
      });

      var data = [{
        'code_name':'ae3ede123sqd',
        'name':'Vénus',
        'age':4000000000,
        'texture':'',
        'rings':false,
        'orbital_speed':2.0,
        'size':'SMALL'
      },{
        'code_name':'az123sqd',
        'name':'Pluton',
        'age':4000000000,
        'texture':'',
        'rings':false,
        'orbital_speed':2.0,
        'size':'SMALL'
      }];

      it('it should return data', function(){
        $httpBackend.when('GET',  planetService.getUri()).respond(200, data);
        planetService.fetchPlanets().then(function(){
          var code = 'az123sqd';
          var res = planetService.getPlanetByCodeName(code);
          expect(res).toEqual(jasmine.any(Object));
          expect(res['code_name']).toEqual(code);
        });

        $httpBackend.flush();
      });

      it('it should return a not found exception', function(){
        $httpBackend.when('GET',  planetService.getUri()).respond(200, data);
        planetService.fetchPlanets().then(function(){
          expect(function(){
            planetService.getPlanetByCodeName('test');
          }).toThrow('Planet not found.');
        });

        $httpBackend.flush();
      });

      it('it should return a not found exception', function(){
        expect(function(){
          planetService.getPlanetByCodeName('test');
        }).toThrow('No planets available.');
      });
    });
  });
})();
