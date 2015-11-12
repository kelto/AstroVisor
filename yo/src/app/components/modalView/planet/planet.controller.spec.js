(function() {
  'use strict';

  describe('controller planet', function(){
    var vm;
    var $rootScope;
    var $auth;
    var modalInstance;
    var toastr;
    var systems;
    var deferredDesc;
    var deferredDesc2;
    var deferredSys;

    beforeEach(module('yo'));
    beforeEach(inject(function(_$controller_, _$rootScope_, _$auth_, _descriptions_, _systems_, _$q_) {
      $rootScope = _$rootScope_;
      $auth = _$auth_;
      systems = _systems_;

      deferredDesc = _$q_.defer();
      deferredDesc2 = _$q_.defer();
      deferredSys = _$q_.defer();

      spyOn(_descriptions_, 'updateDescription').and.returnValue(deferredDesc.promise);
      spyOn(_descriptions_, 'sendNewDescription').and.returnValue(deferredDesc2.promise);
      spyOn(_systems_, 'fetchSystems').and.returnValue(deferredSys.promise);

      deferredSys.resolve([{name:'sys1', planets:[]}, {name:'sys2', planets:[]}, {name:'sys3', planets:[]}, {name:'sys4', planets:[]}]);

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

      vm = _$controller_('PlanetController', {
        $rootScope:$rootScope,
        $scope:$rootScope.$new(),
        $auth:$auth,
        systems:systems,
        descriptions:_descriptions_,
        toastr:toastr
      });
    }));

    //it('should define a planet', function(){
    //  var planet = {
    //    descriptions:[{'id':1}, {'id':2}, {'id':3}],
    //    trades:[{'id':1}, {'id':2}, {'id':3}]
    //  };
    //  spyOn(systems, 'getPlanetById').and.returnValue(planet);
    //  //$rootScope.$digest();
    //  vm.activate();
    //  expect(vm.planet).toEqual(jasmine.any(Object));
    //  expect(vm.descriptions).toEqual(jasmine.any(Array));
    //});

    it('test currentDescription method', function(){
      expect(vm.currentDescription).not.toEqual(null);
      expect(vm.currentDescription()).toEqual('');

      vm.descriptions.push({});
      expect(vm.currentDescription()).toEqual(jasmine.any(Object));
    });

    it('test upvote method', function(){
      vm.descriptions.push({});
      vm.planet = {};
      spyOn($auth, 'isAuthenticated').and.returnValue(true);

      vm.upvote();
      deferredDesc.resolve();
      $rootScope.$apply();

      expect(toastr.success).toHaveBeenCalled();
    });

    it('test upvote method', function(){
      vm.descriptions.push({});
      vm.planet = {};
      spyOn($auth, 'isAuthenticated').and.returnValue(true);

      vm.upvote();
      deferredDesc.reject();
      $rootScope.$apply();

      expect(toastr.error).toHaveBeenCalled();
    });

    it('test upvote method', function(){
      vm.descriptions.push({});
      vm.planet = {};
      spyOn($auth, 'isAuthenticated').and.returnValue(false);

      vm.upvote();
      deferredDesc.resolve();
      $rootScope.$apply();

      expect(toastr.error).toHaveBeenCalled();
    });

    it('test downvote method', function(){
      vm.descriptions.push({});
      vm.planet = {};
      spyOn($auth, 'isAuthenticated').and.returnValue(true);

      vm.downvote();
      deferredDesc.resolve();
      $rootScope.$apply();

      expect(toastr.success).toHaveBeenCalled();
    });

    it('test downvote method', function(){
      vm.descriptions.push({});
      vm.planet = {};
      spyOn($auth, 'isAuthenticated').and.returnValue(true);

      vm.downvote();
      deferredDesc.reject();
      $rootScope.$apply();

      expect(toastr.error).toHaveBeenCalled();
    });

    it('test downvote method', function(){
      vm.descriptions.push({});
      vm.planet = {};
      spyOn($auth, 'isAuthenticated').and.returnValue(false);

      vm.downvote();
      deferredDesc.resolve();
      $rootScope.$apply();

      expect(toastr.error).toHaveBeenCalled();
    });

    it('test clearDescEditorContent method', function(){
      vm.descEditor = 'TEST';
      vm.clearDescEditorContent();
      expect(vm.descEditor).toEqual('');
    });

    it('test sendEditorContent', function(){
      vm.planet = {};
      spyOn($auth, 'isAuthenticated').and.returnValue(true);

      vm.sendDescEditorContent();
      deferredDesc2.resolve();
      $rootScope.$apply();

      expect(toastr.success).toHaveBeenCalled();
    });

    it('test sendEditorContent', function(){
      vm.planet = {};
      spyOn($auth, 'isAuthenticated').and.returnValue(true);

      vm.sendDescEditorContent();
      deferredDesc2.reject();
      $rootScope.$apply();

      expect(toastr.error).toHaveBeenCalled();
    });

    it('test sendEditorContent', function(){
      vm.planet = {};
      spyOn($auth, 'isAuthenticated').and.returnValue(false);

      vm.sendDescEditorContent();
      expect(toastr.error).toHaveBeenCalled();
    });
  });
})();
