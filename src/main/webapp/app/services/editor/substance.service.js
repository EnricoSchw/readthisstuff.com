(function () {
    'use strict';
    angular
        .module('rtsApp')
        .factory('SubstanceService', SubstanceService);


    SubstanceService.$inject = [];


    /**
     * Implement this service to interact with the editor
     *
     * @returns {{saveDocument: saveDocument, loadDocument: loadDocument}}
     * @constructor
     */
    function SubstanceService() {

        var document,
            saveHandler;

        var service = {
            setDocument: setDocument,
            getDocument: getDocument,
            setSaveHandler: setSaveHandler,
            saveDocument: saveDocument,
            loadDocument: loadDocument
        };

        return service;

        function setDocument(doc) {
            document = doc;
        }

        function getDocument() {
            return document;
        }

        function setSaveHandler(handler) {
            saveHandler = handler;
        }

        // handler to save Documents in Editor
        function saveDocument(doc, changes, cb) {
            var json = [];
            angular.forEach(doc.getNodes(), function (node) {
                if (node.type != "container") {
                    this.push({
                        id: node.id,
                        type: node.type,
                        content: node.content
                    });
                }
            }, json);
            saveHandler(json);
            cb(null);
        }

        // handler to load Documents in Editor
        function loadDocument(tx) {
            var body = tx.get('body');
            angular.forEach(document, function (content, key) {
                tx.create(content);
                body.show(content.id);
            });
        }
    }
})();
