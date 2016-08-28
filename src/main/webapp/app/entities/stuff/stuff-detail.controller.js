(function() {
    'use strict';

    angular
        .module('rtsApp')
        .controller('StuffDetailController', StuffDetailController);

    StuffDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Stuff'];

    function StuffDetailController($scope, $rootScope, $stateParams, entity, Stuff) {
        var vm = this;

        vm.stuff = entity;

        var unsubscribe = $rootScope.$on('rtsApp:stuffUpdate', function(event, result) {
            vm.stuff = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
