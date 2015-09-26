(function() {
  'use strict';

  angular
    .module('yo')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log) {

    $log.debug('runBlock end');
  }

})();
