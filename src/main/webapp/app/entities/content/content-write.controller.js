(function () {
    'use strict';

    angular
        .module('rtsApp')
        .controller('ContentWriteController', ContentWriteController);

    ContentWriteController.$inject = ['$scope', '$state', 'Content'];

    function ContentWriteController($scope, $state, Content) {
        var vm = this;

        vm.contents = [];

        loadAll();

        function loadAll() {
            Content.query(function (result) {
                vm.contents = result;
            });
        }
    }
})();
