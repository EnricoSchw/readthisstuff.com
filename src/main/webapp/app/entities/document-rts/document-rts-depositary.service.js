(function () {
    'use strict';
    angular
        .module('rtsApp')
        .factory('DocumentRTSDepositary', DocumentRTSDepositary);

    DocumentRTSDepositary.$inject = ['$rootScope'];

    function DocumentRTSDepositary($rootScope) {

        var documentRTS;

        var service = {
            setTitle: setTitle,
            setContent: setContent,
            setDocument: setDocument,
            getContent: getContent,
            getDocument: getDocument
        };

        return service;

        function setTitle(title) {
            documentRTS.title = title;
        }

        function setContent(content) {
            documentRTS.content = content;
        }

        function setDocument(document) {
            documentRTS = document;
        }

        function getContent() {
            return documentRTS.content;
        }

        function getDocument() {
            return documentRTS;
        }
    }
})();
