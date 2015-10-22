(function(){
  'use strict';

  if (!document.URL.match(/\?nobackend$/)) {
    nostub();
  }
  else {
    stub();
  }

  function nostub(){
    angular.module('AstroVisor', ['yo']);
  }

  function stub(){
    angular.module('AstroVisor', ['yo', 'ngMockE2E']).config(function ($httpProvider) {
      $httpProvider.interceptors.push('HttpStubInterceptor');
    }).run(function ($log, $httpBackend, GetJsonFile) {
      $log.debug('WARNING : AstroVisor stubbed !');

      $httpBackend.whenGET('/api/planets').respond(GetJsonFile.synchronously('stub/GET-planets.json'));

      $httpBackend.whenGET(/.*/).passThrough();
      $httpBackend.whenPOST(/.*/).passThrough();
      $httpBackend.whenDELETE(/.*/).passThrough();
      $httpBackend.whenPUT(/.*/).passThrough();
    });

    angular.module('AstroVisor').service('HttpStubInterceptor', function($q, $timeout){
      var getMockedAsyncRespondTime = function (url) {
        switch (url.split(/\./).pop()) {
          case 'json':
            return 300;
          case 'html':
            // In production all views are into cachedUrl as JS Templates
            return 0;
          default:
            // Web Services
            return 1000;
        }
      };
      return {
        response: function (response) {
          var defer = $q.defer();
          $timeout(function () {
            defer.resolve(response);
          }, getMockedAsyncRespondTime(response.config.url.toString()));
          return defer.promise;
        }
      };
    });

    angular.module('AstroVisor').service('GetJsonFile', function(){
      this.synchronously = function(url){
        var request = new XMLHttpRequest();
        request.open('GET', url, false);
        request.send(null);
        return request.response;
      };
    });

  }
})();
