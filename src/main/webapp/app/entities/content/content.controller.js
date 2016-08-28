(function() {
    'use strict';

    angular
        .module('rtsApp')
        .controller('ContentController', ContentController);

    ContentController.$inject = ['$scope', '$state', 'Content'];

    function ContentController ($scope, $state, Content) {
        var vm = this;
        
        vm.contents = [];

        loadAll();

        function loadAll() {
            Content.query(function(result) {
                vm.contents = result;
            });
        }
    }
})();
