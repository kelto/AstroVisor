angular.module('yo').controller('PlanetController', function ($scope,$http, $stateParams) {
  /* jshint validthis: true */
  var vm = this;
  vm.currentDesc = 1;

  //TODO: This must be moved to a service
  $http.get('/api/descriptions?planet='+$stateParams.id).then(function(data) {
    vm.descriptions = data.data;
    vm.nbDesc = vm.descriptions.length;
  });

  $http.get('/api/planets/'+$stateParams.id).then(function(data) {
    vm.planet = data.data;
  });

  $http.get('/api/trades?planet='+$stateParams.id).then(function(data) {
    vm.trades = data.data;
  });

  vm.currentDescription = function() {
    return vm.descriptions ? vm.descriptions[vm.currentDesc - 1] : '';
  };

});

