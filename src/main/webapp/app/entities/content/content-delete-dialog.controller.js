(function() {
    'use strict';

    angular
        .module('rtsApp')
        .controller('ContentDeleteController',ContentDeleteController);

    ContentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Content'];

    function ContentDeleteController($uibModalInstance, entity, Content) {
        var vm = this;

        vm.content = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Content.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
