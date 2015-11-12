(function() {
  'use strict';

  describe('test a descriptions service', function() {
    var descriptions;
    var $httpBackend;

    beforeEach(module('yo'));
    beforeEach(inject(function(_descriptions_, _$httpBackend_) {
      descriptions = _descriptions_;
      $httpBackend = _$httpBackend_;
    }));

    it('should be registered', function() {
      expect(descriptions).not.toEqual(null);
    });

    describe('sendNewDescription function', function() {
      it('should exist', function () {
        expect(descriptions.sendNewDescription).not.toEqual(null);
      });

      it('should accept post data', function(){
        $httpBackend.when('POST', descriptions.uri).respond(201, 'OK');
        descriptions.sendNewDescription().then(function(res){
          expect(res).not.toEqual(null);
          expect(true).toBeTruthy();
        }).catch(function(){
          expect(false).toBeTruthy();
        });
      });
    });

    describe('updateDescription function', function() {
      it('should exist', function () {
        expect(descriptions.updateDescription).not.toEqual(null);
      });

      it('should accept update data', function(){
        $httpBackend.when('PUT', descriptions.uri).respond(201, 'OK');
        descriptions.updateDescription().then(function(res){
          expect(res).not.toEqual(null);
          expect(true).toBeTruthy();
        }).catch(function(){
            expect(false).toBeTruthy();
        });
      });
    });
  });
})();
