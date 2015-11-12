(function() {
  'use strict';

  describe('controller login', function(){
    var vm;
    var $rootScope;
    var modalInstance;
    var toastr;
    var deferred;

    beforeEach(module('yo'));
    beforeEach(inject(function(_$controller_, _$rootScope_, _$auth_, _$q_) {
      $rootScope = _$rootScope_;

      deferred = _$q_.defer();
      spyOn(_$auth_, 'login').and.returnValue(deferred.promise);

      modalInstance = {
        close:function(){},
        dismiss:function(){}
      };

      spyOn(modalInstance, 'close');
      spyOn(modalInstance, 'dismiss');

      toastr = {
        success: function () {},
        error: function (){}
      };

      spyOn(toastr, 'success');
      spyOn(toastr, 'error');

      vm = _$controller_('LoginController', {
        $scope:$rootScope.$new(),
        $modalInstance:modalInstance,
        $auth:_$auth_,
        toastr:toastr
      });
    }));

    it('should define login method', function(){
      expect(vm.login).not.toEqual(null);
    });

    it('login method success should process correctly', function(){
      vm.login();
      deferred.resolve();
      $rootScope.$apply();
      expect(toastr.success).toHaveBeenCalled();
      expect(modalInstance.close).toHaveBeenCalled();
    });

    it('login method error should process correctly', function(){
      vm.login();
      deferred.reject();
      $rootScope.$apply();
      expect(toastr.error).toHaveBeenCalled();
    });

    it('should define valid close method', function(){
      expect(vm.close).not.toEqual(null);
    });

    it('close method should call modalInstance', function(){
      vm.close();
      expect(modalInstance.dismiss).toHaveBeenCalled();
    });
  });
})();
