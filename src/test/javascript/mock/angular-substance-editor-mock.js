angular.module('angular-substance-editor', [])
    .controller('Controller', ['$scope', function ($scope) {
    }])
    .directive('substance', ['SubstanceService', function (substanceService) {
        return {
            restrict: 'A',
            scope: {
                optionsxx: '='
            },
            link: function (scope, element, attributes) {
                if (attributes.type == "editor") {
                    
                }

                if (attributes.type == "viewer") {

                }

            },
            template: '<substanceEditor></substanceEditor>'
        }
    }]);
