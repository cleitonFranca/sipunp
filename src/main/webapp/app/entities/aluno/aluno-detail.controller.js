(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .controller('AlunoDetailController', AlunoDetailController);

    AlunoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Aluno', 'Endereco', 'Turma', 'Curso', 'ControleAtendimento'];

    function AlunoDetailController($scope, $rootScope, $stateParams, previousState, entity, Aluno, Endereco, Turma, Curso, ControleAtendimento) {
        var vm = this;

        vm.aluno = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('unpsipApp:alunoUpdate', function(event, result) {
            vm.aluno = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
