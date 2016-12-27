(function () {
    'use strict';

    angular
        .module('rtsApp')
        .controller('DocumentRTSController', DocumentRTSController);

    DocumentRTSController.$inject = ['DataUtils', 'DocumentRTS'];

    function DocumentRTSController(DataUtils, DocumentRTS) {
        var vm = this;

        vm.documentRTS = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.onPublicChange = onPublicChange;

        loadAll();

        function loadAll() {
            DocumentRTS.query(function (result) {
                vm.documentRTS = result;
            });
        }

        function onPublicChange(newValue, oldValue, document) {
            document.published=newValue;
            console.log('ID:', vm.documentRTS[0].id,'Published:', vm.documentRTS[0].published);
            DocumentRTS.publish(document);
        }

    }
})();
