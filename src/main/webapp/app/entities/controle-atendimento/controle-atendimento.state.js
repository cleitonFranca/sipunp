(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('controle-atendimento', {
            parent: 'entity',
            url: '/controle-atendimento?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'unpsipApp.controleAtendimento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/controle-atendimento/controle-atendimentos.html',
                    controller: 'ControleAtendimentoController',
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
                    $translatePartialLoader.addPart('controleAtendimento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('controle-atendimento-detail', {
            parent: 'controle-atendimento',
            url: '/controle-atendimento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'unpsipApp.controleAtendimento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/controle-atendimento/controle-atendimento-detail.html',
                    controller: 'ControleAtendimentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('controleAtendimento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ControleAtendimento', function($stateParams, ControleAtendimento) {
                    return ControleAtendimento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'controle-atendimento',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('controle-atendimento-detail.edit', {
            parent: 'controle-atendimento-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/controle-atendimento/controle-atendimento-dialog.html',
                    controller: 'ControleAtendimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ControleAtendimento', function(ControleAtendimento) {
                            return ControleAtendimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('controle-atendimento.new', {
            parent: 'controle-atendimento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/controle-atendimento/controle-atendimento-dialog.html',
                    controller: 'ControleAtendimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numero: null,
                                idade: null,
                                naturalidade: null,
                                queixa: null,
                                encaminhamento: null,
                                vinculo: null,
                                dataCadastro: null,
                                dataAlteracao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('controle-atendimento', null, { reload: 'controle-atendimento' });
                }, function() {
                    $state.go('controle-atendimento');
                });
            }]
        })
        .state('controle-atendimento.edit', {
            parent: 'controle-atendimento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/controle-atendimento/controle-atendimento-dialog.html',
                    controller: 'ControleAtendimentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ControleAtendimento', function(ControleAtendimento) {
                            return ControleAtendimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('controle-atendimento', null, { reload: 'controle-atendimento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('controle-atendimento.delete', {
            parent: 'controle-atendimento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/controle-atendimento/controle-atendimento-delete-dialog.html',
                    controller: 'ControleAtendimentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ControleAtendimento', function(ControleAtendimento) {
                            return ControleAtendimento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('controle-atendimento', null, { reload: 'controle-atendimento' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
