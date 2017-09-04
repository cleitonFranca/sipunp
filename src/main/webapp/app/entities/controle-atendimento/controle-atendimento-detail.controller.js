(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .controller('ControleAtendimentoDetailController', ControleAtendimentoDetailController);

    ControleAtendimentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ControleAtendimento', 'User', 'Cliente'];

    function ControleAtendimentoDetailController($scope, $rootScope, $stateParams, previousState, entity, ControleAtendimento, User, Cliente) {
        var vm = this;

        vm.controleAtendimento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('unpsipApp:controleAtendimentoUpdate', function(event, result) {
            vm.controleAtendimento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
