(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .controller('TurmaDialogController', TurmaDialogController);

    TurmaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Turma', 'Professor', 'Aluno', 'Curso'];

    function TurmaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Turma, Professor, Aluno, Curso) {
        var vm = this;

        vm.turma = entity;
        vm.clear = clear;
        vm.save = save;
        vm.professors = Professor.query();
        vm.alunos = Aluno.query();
        vm.cursos = Curso.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.turma.id !== null) {
                Turma.update(vm.turma, onSaveSuccess, onSaveError);
            } else {
                Turma.save(vm.turma, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('unpsipApp:turmaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
