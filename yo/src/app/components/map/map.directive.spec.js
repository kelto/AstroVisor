(function() {
  'use strict';

  /**
   * @todo Complete the test
   * This example is not perfect.
   * The `link` function is not tested.
   * (malarkey usage, addClass, $watch, $destroy)
   */
  describe('directive map', function() {
    var vm;
    var el;
    var $scope;

    beforeEach(module('yo'));
    beforeEach(inject(function(_$compile_, _$rootScope_, _systems_, $q) {
      $scope = _$rootScope_;
      spyOn(_systems_, 'getSystems').and.callFake(function() {
        return $q.when([{name:'sys1', planets:[]}, {name:'sys2', planets:[]}, {name:'sys3', planets:[]}, {name:'sys4', planets:[]}]);
      });

      el = angular.element('<as-map></as-map>');

      _$compile_(el)($scope.$new());
      $scope.$digest();
      vm = el.scope().vm;

    }));

    it('should be compiled', function() {
      expect(el.html()).not.toEqual(null);
    });

    it('should have scope object with instanciate members', function() {
      expect(vm).toEqual(jasmine.any(Object));

      expect(vm.systems).toEqual(jasmine.any(Array));
      expect(vm.systems.length).toEqual(4);
    });

    it('test using $digest', function() {
      // make an initial selection
      vm.rendered = 4;
      vm.totalPlanets = 4;
      $scope.$digest();

      expect(vm.rendered).toEqual(4);
    });

  });
})();
