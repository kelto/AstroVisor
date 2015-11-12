(function() {
  'use strict';

  describe('controller user', function(){
    var vm;
    var $rootScope;
    var modalInstance;
    var deferred;

    beforeEach(module('yo'));
    beforeEach(inject(function(_$controller_, _$rootScope_, _toastr_, _userService_, _$q_) {
      $rootScope = _$rootScope_;

      deferred = _$q_.defer();
      spyOn(_userService_, 'info').and.returnValue(deferred.promise);

      //Create a mock object using spies
      modalInstance = {
        close: jasmine.createSpy('modalInstance.close'),
        dismiss: jasmine.createSpy('modalInstance.dismiss'),
        result: {
          then: jasmine.createSpy('modalInstance.result.then')
        }
      };

      vm = _$controller_('UserController', {
        $scope:$rootScope.$new(),
        toastr:_toastr_,
        userService:_userService_,
        $modalInstance:modalInstance
      });
    }));

    it('should define close method', function(){
      expect(vm.close).not.toEqual(null);
    });

    it('should define update method', function(){
      expect(vm.update).not.toEqual(null);
    });

    //it('should define planets', function(){
    //  deferred.resolve([{name:'sys1', planets:[]}, {name:'sys2', planets:[]}, {name:'sys3', planets:[]}, {name:'sys4', planets:[]}]);
    //  $rootScope.$apply();
    //
    //  expect(angular.isArray(vm.systems)).toBeTruthy();
    //  expect(vm.systems.length === 4).toBeTruthy();
    //});

    //it('should count rendered planets', function(){
    //  vm.newRenderedPlanet();
    //  expect(vm.rendered).toEqual(1);
    //});
  });
})();
