(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('curso', {
            parent: 'entity',
            url: '/curso?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'unpsipApp.curso.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/curso/cursos.html',
                    controller: 'CursoController',
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
                    $translatePartialLoader.addPart('curso');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('curso-detail', {
            parent: 'curso',
            url: '/curso/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'unpsipApp.curso.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/curso/curso-detail.html',
                    controller: 'CursoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('curso');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Curso', function($stateParams, Curso) {
                    return Curso.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'curso',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('curso-detail.edit', {
            parent: 'curso-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/curso/curso-dialog.html',
                    controller: 'CursoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Curso', function(Curso) {
                            return Curso.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('curso.new', {
            parent: 'curso',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/curso/curso-dialog.html',
                    controller: 'CursoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                coordenado: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('curso', null, { reload: 'curso' });
                }, function() {
                    $state.go('curso');
                });
            }]
        })
        .state('curso.edit', {
            parent: 'curso',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/curso/curso-dialog.html',
                    controller: 'CursoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Curso', function(Curso) {
                            return Curso.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('curso', null, { reload: 'curso' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('curso.delete', {
            parent: 'curso',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/curso/curso-delete-dialog.html',
                    controller: 'CursoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Curso', function(Curso) {
                            return Curso.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('curso', null, { reload: 'curso' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
