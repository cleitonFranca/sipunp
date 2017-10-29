(function() {
    'use strict';

    angular
        .module('unpsipApp')
        .controller('AlunoDialogController', AlunoDialogController);

    AlunoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Aluno', 'Endereco', 'Turma', 'Curso', 'ControleAtendimento'];

    function AlunoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Aluno, Endereco, Turma, Curso, ControleAtendimento) {
        var vm = this;

        vm.aluno = entity;
        vm.clear = clear;
        vm.save = save;
        vm.enderecos = Endereco.query({filter: 'aluno-is-null'});
        $q.all([vm.aluno.$promise, vm.enderecos.$promise]).then(function() {
            if (!vm.aluno.endereco || !vm.aluno.endereco.id) {
                return $q.reject();
            }
            return Endereco.get({id : vm.aluno.endereco.id}).$promise;
        }).then(function(endereco) {
            vm.enderecos.push(endereco);
        });
        vm.turmas = Turma.query();
        vm.cursos = Curso.query();
        vm.controleatendimentos = ControleAtendimento.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.aluno.id !== null) {
                Aluno.update(vm.aluno, onSaveSuccess, onSaveError);
            } else {
                Aluno.save(vm.aluno, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('unpsipApp:alunoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
