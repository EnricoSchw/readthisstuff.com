(function () {
    'use strict';

    angular
        .module('rtsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('document', {
                parent: 'entity',
                url: '/document',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'rtsApp.documentRTS.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/document-rts/document-rts.html',
                        controller: 'DocumentRTSController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('documentRTS');
                        $translatePartialLoader.addPart('contentType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('document-rts-detail', {
                parent: 'entity',
                url: '/document-rts/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'rtsApp.documentRTS.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/document-rts/document-rts-detail.html',
                        controller: 'DocumentRTSDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('documentRTS');
                        $translatePartialLoader.addPart('contentType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'DocumentRTS', function ($stateParams, DocumentRTS) {
                        return DocumentRTS.get({id: $stateParams.id}).$promise;
                    }]
                }
            })
            .state('document.write', {
                parent: 'entity',
                url: '/document/write',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'rtsApp.content.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/document-rts/document-rts-write.html',
                        controller: 'DocumentRTSWriteController',
                        controllerAs: 'vm'
                    },
                    'documentrts@app': {
                        templateUrl: 'app/entities/document-rts/document-rts-stuff.html',
                        controller: 'DocumentRTSStuffController',
                        controllerAs: 'vm'
                    }

                },
                resolve: {
                    entity: function () {
                        return {
                            title: "Unknown Title",
                            author: null,
                            content: [
                                {
                                    id: "p1",
                                    type: "paragraph",
                                    content: "Insert a new image using the image tool."
                                }, {
                                    id: "p2",
                                    type: "paragraph",
                                    content: "Please note that images are not actually uploaded in this example. You would need to provide a custom file client that talks to an image store. See FileClientStub which reveals the API you have to implement."
                                }],
                            type: null,
                            thump: null,
                            thumpContentType: null,
                            id: null
                        };
                    },
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('content');
                        $translatePartialLoader.addPart('contentType');
                        return $translate.refresh();
                    }]
                }
            })
            .state('document.stuff', {
                parent: 'document.write',
                url: '/stuff',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/document-rts/document-rts-stuff-dialog.html',
                        controller: 'DocumentRTSStuffDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    author: null,
                                    content: null,
                                    type: null,
                                    thump: null,
                                    thumpContentType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('document.write', null, {reload: true});
                    }, function () {
                        $state.go('document.write');
                    });
                }]

            })
            .state('document-rts.new', {
                parent: 'document-rts',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/document-rts/document-rts-dialog.html',
                        controller: 'DocumentRTSDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    author: null,
                                    content: null,
                                    type: null,
                                    thump: null,
                                    thumpContentType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('document-rts', null, {reload: true});
                    }, function () {
                        $state.go('document-rts');
                    });
                }]
            })
            .state('document-rts.edit', {
                parent: 'document-rts',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/document-rts/document-rts-dialog.html',
                        controller: 'DocumentRTSDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['DocumentRTS', function (DocumentRTS) {
                                return DocumentRTS.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('document-rts', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('document-rts.delete', {
                parent: 'document-rts',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/document-rts/document-rts-delete-dialog.html',
                        controller: 'DocumentRTSDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['DocumentRTS', function (DocumentRTS) {
                                return DocumentRTS.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('document-rts', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
