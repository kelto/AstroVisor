(function() {
  'use strict';

  describe('controller map', function(){
    var vm;
    var $scope;
    var deferred;

    beforeEach(module('yo'));
    beforeEach(inject(function(_$controller_, _$rootScope_, _planets_, _$q_) {
      $scope = _$rootScope_;

      deferred = _$q_.defer();
      spyOn(_planets_, 'getPlanets').and.returnValue(deferred.promise);

      vm = _$controller_('MapController', {
        planets:_planets_
      });
    }));

    it('should define planets', function(){
      deferred.resolve([{name:'sys1', planets:[]}, {name:'sys2', planets:[]}, {name:'sys3', planets:[]}, {name:'sys4', planets:[]}]);
      $scope.$apply();

      expect(angular.isArray(vm.planets)).toBeTruthy();
      expect(vm.planets.length === 4).toBeTruthy();
    });

    it('should count rendered planets', function(){
      vm.newRenderedPlanet();
      expect(vm.rendered).toEqual(1);
    });
  });
})();
