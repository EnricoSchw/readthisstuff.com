(function () {
    'use strict';

    angular
        .module('rtsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('stuff', {
                parent: 'entity',
                url: '/stuff',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'rtsApp.stuff.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/stuff/stuffs.html',
                        controller: 'StuffController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stuff');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('stuff-detail', {
                parent: 'entity',
                url: '/stuff/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'rtsApp.stuff.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/stuff/stuff-detail.html',
                        controller: 'StuffDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stuff');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Stuff', function ($stateParams, Stuff) {
                        return Stuff.get({id: $stateParams.id}).$promise;
                    }]
                }
            })
            .state('stuff.new', {
                parent: 'stuff',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/stuff/stuff-dialog.html',
                        controller: 'StuffDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    description: null,
                                    publicationDate: null,
                                    clicks: null,
                                    author: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function () {
                        $state.go('stuff', null, {reload: true});
                    }, function () {
                        $state.go('stuff');
                    });
                }]
            })
            .state('stuff.edit', {
                parent: 'stuff',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/stuff/stuff-dialog.html',
                        controller: 'StuffDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Stuff', function (Stuff) {
                                return Stuff.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('stuff', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('stuff.delete', {
                parent: 'stuff',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/stuff/stuff-delete-dialog.html',
                        controller: 'StuffDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Stuff', function (Stuff) {
                                return Stuff.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('stuff', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
