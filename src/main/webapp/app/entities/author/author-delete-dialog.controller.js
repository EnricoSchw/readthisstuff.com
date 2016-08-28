(function() {
    'use strict';

    angular
        .module('rtsApp')
        .controller('AuthorDeleteController',AuthorDeleteController);

    AuthorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Author'];

    function AuthorDeleteController($uibModalInstance, entity, Author) {
        var vm = this;

        vm.author = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Author.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
