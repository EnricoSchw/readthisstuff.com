(function () {
    'use strict';

    angular
        .module('rtsApp')
        .controller('DocumentRTSWriteController', DocumentRTSWriteController);

    DocumentRTSWriteController.$inject = ['$scope', '$state', 'DocumentRTS'];

    function DocumentRTSWriteController($scope, $state, DocumentRTS) {
        var vm = this;

        vm.document = [];

        loadAll();

        function loadAll() {
            DocumentRTS.query(function (result) {
                vm.document = result;
            });
        }
    }
})();
