(function() {
    'use strict';
    angular
        .module('rtsApp')
        .factory('DocumentRTS', DocumentRTS);

    DocumentRTS.$inject = ['$resource'];

    function DocumentRTS ($resource) {
        var resourceUrl =  'api/document-rts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
