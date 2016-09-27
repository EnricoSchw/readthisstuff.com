(function() {
    'use strict';

    angular
        .module('rtsApp')
        .controller('DocumentRTSDeleteController',DocumentRTSDeleteController);

    DocumentRTSDeleteController.$inject = ['$uibModalInstance', 'entity', 'DocumentRTS'];

    function DocumentRTSDeleteController($uibModalInstance, entity, DocumentRTS) {
        var vm = this;

        vm.documentRTS = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DocumentRTS.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
