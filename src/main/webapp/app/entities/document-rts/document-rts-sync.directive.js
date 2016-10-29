(function () {
    'use strict';
    angular
        .module('rtsApp')
        .directive("syncDocumentModel", SyncDocumentModel);

    SyncDocumentModel.$inject = ['$rootScope'];

    function SyncDocumentModel($rootScope) {

        return {
            restrict: "A",
            link: function (scope, elem, attrs) {
                elem.on('keyup keydown', function (event) {
                    scope.$apply(function () {
                        scope[attrs.syncDocumentModel] = elem.html();
                        $rootScope.$broadcast('rts-change-documentRTS-title', {
                            title: scope[attrs.syncDocumentModel]
                        });
                    });

                });
            }
        }
    }
})();
