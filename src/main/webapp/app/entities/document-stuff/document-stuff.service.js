(function () {
    'use strict';
    angular
        .module('rtsApp')
        .factory('DocumentStuff', DocumentStuff);

    DocumentStuff.$inject = ['$resource', 'DateUtils'];

    function DocumentStuff($resource, DateUtils) {
        var resourceUrl = 'api/document-stuffs/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.publicationDate = DateUtils.convertLocalDateFromServer(data.publicationDate);
                    }
                    return data;
                }
            }
        });
    }
})();
