(function() {
    'use strict';

    angular
        .module('rtsApp')
        .controller('DocumentRTSController', DocumentRTSController);

    DocumentRTSController.$inject = ['$scope', '$state', 'DataUtils', 'DocumentRTS'];

    function DocumentRTSController ($scope, $state, DataUtils, DocumentRTS) {
        var vm = this;
        
        vm.documentRTS = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            DocumentRTS.query(function(result) {
                vm.documentRTS = result;
            });
        }
    }
})();
