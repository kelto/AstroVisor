(function() {
  'use strict';

  describe('directive navbar', function() {
    var el;
    var $rootScope;

    beforeEach(module('yo'));
    beforeEach(inject(function(_$compile_, _$rootScope_) {
      $rootScope = _$rootScope_;

      el = angular.element('<navbar></navbar>');

      _$compile_(el)($rootScope.$new());
      $rootScope.$digest();
    }));

    it('should be compiled', function() {
      expect(el.html()).not.toEqual(null);
    });
  });
})();
