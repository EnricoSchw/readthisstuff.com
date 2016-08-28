(function () {
    'use strict';

    var EditorController = require('./components/editor/editor.controller');

    angular
        .module('rtsApp')
        .controller('ContentWriteController', ContentWriteController);

    ContentWriteController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EditorController', 'Content'];

    function ContentWriteController($timeout, $scope, $stateParams, $uibModalInstance, entity, EditorController, Content) {
        var vm = this;

        vm.content = entity;
        vm.clear = clear;
        vm.save = save;

        vm.testVar = EditorController.testVar

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.content.id !== null) {
                Content.update(vm.content, onSaveSuccess, onSaveError);
            } else {
                Content.save(vm.content, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('rtsApp:contentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }


    }
})();
