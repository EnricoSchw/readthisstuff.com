(function () {
    'use strict';

    angular
        .module('rtsApp')
        .controller('DocumentRTSWriteController', DocumentRTSWriteController);

    DocumentRTSWriteController.$inject = ['$scope', '$state', 'entity', 'DocumentRTS', 'SubstanceService'];

    function DocumentRTSWriteController($scope, $state, entity, DocumentRTS, substanceService) {
        var vm = this;
        vm.documentRTS = entity;

        loadAll();

        function loadAll() {
            substanceService.setDocument(vm.documentRTS.content);
            substanceService.setSaveHandler(handleSaveEvent);
        }

        function handleSaveEvent(content) {
            vm.isSaving = true;
            vm.documentRTS.content = content;
            if (vm.documentRTS.id !== null) {
                DocumentRTS.update(vm.documentRTS, onSaveSuccess, onSaveError);
            } else {
                DocumentRTS.save(vm.documentRTS, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('rtsApp:documentRTSUpdate', result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }
    }
})();
