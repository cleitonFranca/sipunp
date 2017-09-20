(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .controller('CursoDialogController', CursoDialogController);

    CursoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Curso', 'Professor', 'Turma', 'Aluno'];

    function CursoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Curso, Professor, Turma, Aluno) {
        var vm = this;

        vm.curso = entity;
        vm.clear = clear;
        vm.save = save;
        vm.professors = Professor.query();
        vm.turmas = Turma.query();
        vm.alunos = Aluno.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.curso.id !== null) {
                Curso.update(vm.curso, onSaveSuccess, onSaveError);
            } else {
                Curso.save(vm.curso, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('unpsipApp:cursoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
