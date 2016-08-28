(function() {
    'use strict';

    angular
        .module('rtsApp')
        .controller('StuffDeleteController',StuffDeleteController);

    StuffDeleteController.$inject = ['$uibModalInstance', 'entity', 'Stuff'];

    function StuffDeleteController($uibModalInstance, entity, Stuff) {
        var vm = this;

        vm.stuff = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Stuff.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
