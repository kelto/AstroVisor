(function() {
  'use strict';

  describe('test a systems service', function() {
    var systems;
    var $httpBackend;

    beforeEach(module('yo'));
    beforeEach(inject(function(_systems_, _$httpBackend_) {
      systems = _systems_;
      $httpBackend = _$httpBackend_;
    }));

    it('should be registered', function() {
      expect(systems).not.toEqual(null);
    });

    describe('fetchSystems function', function() {
      it('should exist', function () {
        expect(systems.fetchSystems).not.toEqual(null);
      });

      it('should return data', function() {
        $httpBackend.when('GET',  systems.uri).respond(200, [{name:'Alpha Centauri', planets:[{}, {}]}, {name:'Proxima Centaure', planets:[{}, {}]}, {name:'Sol', planets:[]}]);
        var data;
        systems.fetchSystems().then(function(fetchedData) {
          data = fetchedData;
        });

        $httpBackend.flush();
        expect(data).toEqual(jasmine.any(Array));
        expect(data.length === 2).toBeTruthy();
        expect(data[0]).toEqual(jasmine.any(Object));
      });

      it('should return an error', function() {
        $httpBackend.when('GET',  systems.uri).respond(500);
        systems.fetchSystems().catch(function(error){
          expect(error).toEqual(jasmine.stringMatching('XHR Failed for'));
        });
        $httpBackend.flush();
      });

      it('should return an error', function() {
        $httpBackend.when('GET',  systems.uri).respond(200, []);
        systems.fetchSystems().catch(function(error){
          expect(error).toEqual(jasmine.stringMatching('No systems found.'));
        });
        $httpBackend.flush();
      });

      it('should return an error', function() {
        $httpBackend.when('GET',  systems.uri).respond(200, [{name:'Sol', planets:[]}]);
        systems.fetchSystems().catch(function(error){
          expect(error).toEqual(jasmine.stringMatching('Systems were empty.'));
        });
        $httpBackend.flush();
      });
    });

    describe('getSystems function', function() {
      it('should exist', function() {
        expect(systems.getSystems).not.toEqual(null);
      });

      it('should return data', function() {
        $httpBackend.when('GET',  systems.uri).respond(200, [{name:'Alpha Centauri', planets:[{}, {}]}, {name:'Proxima Centaure', planets:[{}, {}]}, {name:'Sol', planets:[]}]);
        var data1, data2;
        systems.getSystems().then(function(fetchedData1) {
          data1 = fetchedData1;
          systems.getSystems().then(function(fetchedData2) {
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

    describe('get a planet by id', function(){
      it('should exist', function(){
        expect(systems.getPlanetById).not.toEqual(null);
      });

      var data = [{
        "name": "Alpha Centauri",
        "planets": [
          {
            "id":1,
            "code_name": "ae3ede123swwwwwqd",
            "name": "Pandora",
            "type": "gas",
            "age": 4000000000,
            "texture": "neptune",
            "rings": false,
            "atmosphere": false,
            "orbital_speed": 6.0,
            "size": "XXL"
          },{
            "id":2,
            "code_name":"ae3ede123sqd",
            "name":"Cérès",
            "type":"telluric",
            "age":4000000000,
            "texture":"MARS",
            "rings":false,
            "atmosphere":true,
            "orbital_speed":10.5,
            "size":"NORMAL"
          }
        ]
      }];

      it('it should return data', function(){
        $httpBackend.when('GET',  systems.uri).respond(200, data);
        systems.fetchSystems().then(function(){
          var code = 1;
          var res = systems.getPlanetById(code);
          expect(res).toEqual(jasmine.any(Object));
          expect(res['id']).toEqual(code);
        });

        $httpBackend.flush();
      });

      it('it should return a not found exception', function(){
        $httpBackend.when('GET',  systems.uri).respond(200, data);
        systems.fetchSystems().then(function(){
          expect(function(){
            systems.getPlanetById('test');
          }).toThrow('Planet not found.');
        });

        $httpBackend.flush();
      });

      it('it should return a not found exception', function(){
        expect(function(){
          systems.getPlanetById('test');
        }).toThrow('No systems available.');
      });
    });
  });
})();
