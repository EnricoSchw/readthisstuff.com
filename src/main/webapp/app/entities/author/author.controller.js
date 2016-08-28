(function() {
    'use strict';

    angular
        .module('rtsApp')
        .controller('AuthorController', AuthorController);

    AuthorController.$inject = ['$scope', '$state', 'DataUtils', 'Author'];

    function AuthorController ($scope, $state, DataUtils, Author) {
        var vm = this;
        
        vm.authors = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Author.query(function(result) {
                vm.authors = result;
            });
        }
    }
})();
