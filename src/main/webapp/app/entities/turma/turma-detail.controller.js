(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .controller('TurmaDetailController', TurmaDetailController);

    TurmaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Turma', 'Professor', 'Aluno', 'Curso'];

    function TurmaDetailController($scope, $rootScope, $stateParams, previousState, entity, Turma, Professor, Aluno, Curso) {
        var vm = this;

        vm.turma = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('unpsipApp:turmaUpdate', function(event, result) {
            vm.turma = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
