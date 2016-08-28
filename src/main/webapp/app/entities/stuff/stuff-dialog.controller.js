(function() {
    'use strict';

    angular
        .module('rtsApp')
        .controller('StuffDialogController', StuffDialogController);

    StuffDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Stuff'];

    function StuffDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Stuff) {
        var vm = this;

        vm.stuff = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.stuff.id !== null) {
                Stuff.update(vm.stuff, onSaveSuccess, onSaveError);
            } else {
                Stuff.save(vm.stuff, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rtsApp:stuffUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.publicationDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
