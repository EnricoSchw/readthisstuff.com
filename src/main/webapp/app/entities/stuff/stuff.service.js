(function() {
    'use strict';
    angular
        .module('rtsApp')
        .factory('Stuff', Stuff);

    Stuff.$inject = ['$resource', 'DateUtils'];

    function Stuff ($resource, DateUtils) {
        var resourceUrl =  'api/stuffs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.publicationDate = DateUtils.convertLocalDateFromServer(data.publicationDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.publicationDate = DateUtils.convertLocalDateToServer(data.publicationDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.publicationDate = DateUtils.convertLocalDateToServer(data.publicationDate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
