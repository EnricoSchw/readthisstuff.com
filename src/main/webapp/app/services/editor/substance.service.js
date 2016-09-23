(function () {
    'use strict';
    angular
        .module('rtsApp')
        .factory('SubstanceService', SubstanceService);

    /**
     * Implement this service to interact with the editor
     *
     * @returns {{saveDocument: saveDocument, loadDocument: loadDocument}}
     * @constructor
     */
    function SubstanceService() {
        return {

            // handler to save Documents in Editor
            saveDocument: function (doc, changes, cb) {
                console.warn('Save from Angular!');
                cb(null);
            },

            // handler to load Documents in Editor
            loadDocument: function (tx) {
                var body = tx.get('body');

                tx.create({
                    id: 'p1',
                    type: 'paragraph',
                    content: "Enrico Insert a new image using the image tool."
                });
                body.show('p1');

                tx.create({
                    id: 'p2',
                    type: 'paragraph',
                    content: "Please note that images are not actually uploaded in this example. You would need to provide a custom file client that talks to an image store. See FileClientStub which reveals the API you have to implement."
                });
                body.show('p2');
            }
        };
    }
})();
