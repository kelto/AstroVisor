(function() {
  'use strict';

  describe('test a sphereService', function() {
    var sphereService;

    beforeEach(module('yo'));
    beforeEach(inject(function(_sphereService_) {
      sphereService = _sphereService_;
    }));

    it('should be registered', function() {
      expect(sphereService).not.toEqual(null);
    });
  });
})();
