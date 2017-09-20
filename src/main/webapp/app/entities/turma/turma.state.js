(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('turma', {
            parent: 'entity',
            url: '/turma',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'unpsipApp.turma.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/turma/turmas.html',
                    controller: 'TurmaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('turma');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('turma-detail', {
            parent: 'turma',
            url: '/turma/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'unpsipApp.turma.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/turma/turma-detail.html',
                    controller: 'TurmaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('turma');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Turma', function($stateParams, Turma) {
                    return Turma.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'turma',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('turma-detail.edit', {
            parent: 'turma-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/turma/turma-dialog.html',
                    controller: 'TurmaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Turma', function(Turma) {
                            return Turma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('turma.new', {
            parent: 'turma',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/turma/turma-dialog.html',
                    controller: 'TurmaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('turma', null, { reload: 'turma' });
                }, function() {
                    $state.go('turma');
                });
            }]
        })
        .state('turma.edit', {
            parent: 'turma',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/turma/turma-dialog.html',
                    controller: 'TurmaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Turma', function(Turma) {
                            return Turma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('turma', null, { reload: 'turma' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('turma.delete', {
            parent: 'turma',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/turma/turma-delete-dialog.html',
                    controller: 'TurmaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Turma', function(Turma) {
                            return Turma.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('turma', null, { reload: 'turma' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
