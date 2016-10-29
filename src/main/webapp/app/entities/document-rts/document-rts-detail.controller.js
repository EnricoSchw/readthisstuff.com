(function() {
    'use strict';

    angular
        .module('rtsApp')
        .controller('DocumentRTSDetailController', DocumentRTSDetailController);

    DocumentRTSDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'DocumentRTS'];

    function DocumentRTSDetailController($scope, $rootScope, $stateParams, DataUtils, entity, DocumentRTS) {
        var vm = this;

        vm.documentRTS = entity;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('rtsApp:documentRTSUpdate', function(event, result) {
            vm.documentRTS = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
