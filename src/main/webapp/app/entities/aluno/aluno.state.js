(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('aluno', {
            parent: 'entity',
            url: '/aluno?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'unpsipApp.aluno.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aluno/alunos.html',
                    controller: 'AlunoController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('aluno');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('aluno-detail', {
            parent: 'aluno',
            url: '/aluno/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'unpsipApp.aluno.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/aluno/aluno-detail.html',
                    controller: 'AlunoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('aluno');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Aluno', function($stateParams, Aluno) {
                    return Aluno.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'aluno',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('aluno-detail.edit', {
            parent: 'aluno-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aluno/aluno-dialog.html',
                    controller: 'AlunoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Aluno', function(Aluno) {
                            return Aluno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('aluno.new', {
            parent: 'aluno',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aluno/aluno-dialog.html',
                    controller: 'AlunoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                matricula: null,
                                nome: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('aluno', null, { reload: 'aluno' });
                }, function() {
                    $state.go('aluno');
                });
            }]
        })
        .state('aluno.edit', {
            parent: 'aluno',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aluno/aluno-dialog.html',
                    controller: 'AlunoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Aluno', function(Aluno) {
                            return Aluno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('aluno', null, { reload: 'aluno' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('aluno.delete', {
            parent: 'aluno',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/aluno/aluno-delete-dialog.html',
                    controller: 'AlunoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Aluno', function(Aluno) {
                            return Aluno.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('aluno', null, { reload: 'aluno' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
