(function () {
    'use strict';

    angular
        .module('rtsApp')
        .controller('DocumentRTSStuffController', DocumentRTSStuffController);

    DocumentRTSStuffController.$inject = ['entity'];

    function DocumentRTSStuffController(entity) {
        var vm = this;
        vm.title = entity.title;
    }
})();
