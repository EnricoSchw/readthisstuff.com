(function () {
    'use strict';

    angular
        .module('rtsApp')
        .controller('DocumentRTSStuffDialogController', DocumentRTSStuffDialogController);

    DocumentRTSStuffDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'DocumentRTS', 'DocumentRTSDepositary'];

    function DocumentRTSStuffDialogController($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, DocumentRTS, documentRTSDepositary) {
        var vm = this;
        var originalTitle = entity.title;

        vm.documentRTS = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            vm.documentRTS.title = originalTitle;
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.documentRTS.id !== null) {
                DocumentRTS.update(vm.documentRTS, onSaveSuccess, onSaveError);
            } else {
                DocumentRTS.save(vm.documentRTS, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('rtsApp:documentRTSUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }


        vm.setThump = function ($file, documentRTS) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function (base64Data) {
                    $scope.$apply(function () {
                        documentRTS.thump = base64Data;
                        documentRTS.thumpContentType = $file.type;
                    });
                });
            }
        };

    }
})();
