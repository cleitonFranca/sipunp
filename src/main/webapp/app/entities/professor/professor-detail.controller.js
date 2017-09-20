(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .controller('ProfessorDetailController', ProfessorDetailController);

    ProfessorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Professor', 'Turma', 'Curso'];

    function ProfessorDetailController($scope, $rootScope, $stateParams, previousState, entity, Professor, Turma, Curso) {
        var vm = this;

        vm.professor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('unpsipApp:professorUpdate', function(event, result) {
            vm.professor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
