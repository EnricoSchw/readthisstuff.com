(function () {
    'use strict';

    angular
        .module('rtsApp')
        .controller('DocumentRTSWriteController', DocumentRTSWriteController);

    DocumentRTSWriteController.$inject = ['$scope', '$rootScope', 'entity', 'DocumentRTS', 'SubstanceService', 'DocumentRTSDepositary'];

    function DocumentRTSWriteController($scope, $rootScope, entity, DocumentRTS, substanceService, documentRTSDepositary) {
        var vm = this;
        documentRTSDepositary.setDocument(entity);

        startEditor();

        function startEditor() {
            substanceService.setDocument(documentRTSDepositary.getContent());
            substanceService.setSaveHandler(handleSaveEvent);
        }

        function handleSaveEvent(content) {
            vm.isSaving = true;
            documentRTSDepositary.setContent(content);
            var document = documentRTSDepositary.getDocument();

            if (document.id !== null) {
                DocumentRTS.update(document, onSaveSuccess, onSaveError);
            } else {
                DocumentRTS.save(document, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('rtsApp:documentRTSUpdate', result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        $scope.$on('rts-change-documentRTS-title', function (event, data) {
            documentRTSDepositary.setTitle(data.title);
        });

        $rootScope.$on('rtsApp:documentRTSUpdate', function (event, result) {
            documentRTSDepositary.setDocument(result);
        });

    }
})();
