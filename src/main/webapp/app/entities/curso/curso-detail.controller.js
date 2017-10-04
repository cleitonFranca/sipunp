(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .controller('CursoDetailController', CursoDetailController);

    CursoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Curso', 'Professor', 'Turma', 'Aluno'];

    function CursoDetailController($scope, $rootScope, $stateParams, previousState, entity, Curso, Professor, Turma, Aluno) {
        var vm = this;

        vm.curso = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('unpsipApp:cursoUpdate', function(event, result) {
            vm.curso = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
