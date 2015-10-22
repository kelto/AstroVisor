(function () {
  'use strict';

  angular.module('yo').provider('modalState', modalState);

  /** @ngInject */
  function modalState($stateProvider) {
    var provider = this;
    var modalResult;
    this.$get = function () {
      return provider;
    };
    this.state = function (stateName, options) {
      var modalInstance;
      $stateProvider.state(stateName, {
        url: options.url,
        onEnter: ['$modal', '$state', function ($modal, $state) {
          modalInstance = $modal.open(options);
          // When the modal uses $close({..}), the data (=result) will be assigned to the parent state as 'modalResult'.
          modalInstance.result.then(function (result) {
            modalResult = result;
          }).finally(function () { // modal closes
            if (modalResult) {
              $state.get('^').modalResult = modalResult;
            }
            modalInstance = modalResult = null;
            if ($state.$current.name === stateName) {
              $state.go('^'); // go to parent state
            }
          });
        }],
        onExit: function () {
          if (modalInstance) {
            modalInstance.close();
          }
        }
      });
      return provider;
    };
  }
})();
